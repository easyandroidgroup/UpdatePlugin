/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lzh.framework.updatepluginlib.business;

import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.util.UpdatePreference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 默认的apk下载任务。若需定制，则可通过{@link UpdateBuilder#downloadWorker(DownloadWorker)}或者{@link UpdateConfig#downloadWorker(DownloadWorker)}进行定制使用
 *
 * <p>此默认下载任务。支持断点下载功能。
 *
 * @author haoge
 */
public class DefaultDownloadWorker extends DownloadWorker {
    private HttpURLConnection urlConn;
    @Override
    protected void download(String url, File target) throws Exception{
        URL httpUrl = new URL(url);
        urlConn = (HttpURLConnection) httpUrl.openConnection();
        setDefaultProperties();
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            urlConn.disconnect();
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }

        long contentLength = urlConn.getContentLength();
        if (checkIsDownAll(target,url,contentLength)) {
            urlConn.disconnect();
            urlConn = null;
            // notify download completed
            sendDownloadComplete(target);
            return;
        }
        RandomAccessFile raf = supportBreakpointDownload(target, httpUrl, url);
        if (contentLength > 0) {
            UpdatePreference.saveDownloadTotalSize(url,contentLength);
        }

        long offset = target.exists() ? (int) target.length() : 0;
        InputStream inputStream = urlConn.getInputStream();
        byte[] buffer = new byte[8 * 1024];
        int length;
        long start = System.currentTimeMillis();
        while ((length = inputStream.read(buffer)) != -1) {
            raf.write(buffer, 0, length);
            offset += length;
            long end = System.currentTimeMillis();
            if (end - start > 1000) {
                sendDownloadProgress(offset,contentLength);
                start = System.currentTimeMillis();
            }
            UpdatePreference.saveDownloadSize(url,offset);
        }

        urlConn.disconnect();
        raf.close();
        urlConn = null;

        // notify download completed
        sendDownloadComplete(target);
    }

    private boolean checkIsDownAll(File target,String url,long contentLength) {
        long lastDownSize = UpdatePreference.getLastDownloadSize(url);
        long length = target.length();
        long lastTotalSize = UpdatePreference.getLastDownloadTotalSize(url);
        return lastDownSize == length
                && lastTotalSize == lastDownSize
                && lastDownSize != 0
                && lastDownSize == contentLength;
    }

    private RandomAccessFile supportBreakpointDownload(File target, URL httpUrl, String url) throws IOException {

        String range = urlConn.getHeaderField("Accept-Ranges");
        if (TextUtils.isEmpty(range) || !range.startsWith("bytes")) {
            target.delete();
            return new RandomAccessFile(target,"rw");
        }

        long lastDownSize = UpdatePreference.getLastDownloadSize(url);
        long length = target.length();
        long lastTotalSize = UpdatePreference.getLastDownloadTotalSize(url);
        long contentLength = Long.parseLong(urlConn.getHeaderField("Content-Length"));
        UpdatePreference.saveDownloadTotalSize(url,contentLength);
        if (lastTotalSize != contentLength
                || lastDownSize != length
                || lastDownSize > contentLength) {
            target.delete();
            return new RandomAccessFile(target,"rw");
        }
        urlConn.disconnect();
        urlConn = (HttpURLConnection) httpUrl.openConnection();

        urlConn.setRequestProperty("RANGE", "bytes=" + length + "-" + contentLength);
        setDefaultProperties();
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }
        RandomAccessFile raf = new RandomAccessFile(target,"rw");
        raf.seek(length);

        return raf;
    }

    private void setDefaultProperties() throws IOException {
        urlConn.setRequestProperty("Content-Type","text/html; charset=UTF-8");
        urlConn.setRequestMethod("GET");
        urlConn.setConnectTimeout(10000);
    }

}
