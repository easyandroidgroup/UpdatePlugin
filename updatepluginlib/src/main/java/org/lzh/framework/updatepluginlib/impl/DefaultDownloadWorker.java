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
package org.lzh.framework.updatepluginlib.impl;

import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.DownloadWorker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 默认的apk下载任务。若需定制，则可通过{@link UpdateBuilder#setDownloadWorker(Class)}或者{@link UpdateConfig#setDownloadWorker(Class)}进行定制使用
 *
 * <p>此默认下载任务。支持断点下载功能。
 *
 * @author haoge
 */
public class DefaultDownloadWorker extends DownloadWorker {

    private HttpURLConnection urlConn;
    private File original;
    private File bak;
    private long contentLength;

    @Override
    protected void download(String url, File target) throws Exception{
        original = target;
        URL httpUrl = new URL(url);
        urlConn = (HttpURLConnection) httpUrl.openConnection();
        setDefaultProperties();
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            urlConn.disconnect();
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }

        contentLength = urlConn.getContentLength();
        // 使用此bak文件进行下载。辅助进行断点下载。
        if (checkIsDownAll()) {
            urlConn.disconnect();
            urlConn = null;
            // notify download completed
            sendDownloadComplete(original);
            return;
        }

        createBakFile();
        FileOutputStream writer = supportBreakpointDownload(httpUrl);

        long offset = bak.length();
        InputStream inputStream = urlConn.getInputStream();
        byte[] buffer = new byte[8 * 1024];
        int length;
        long start = System.currentTimeMillis();
        while ((length = inputStream.read(buffer)) != -1) {
            writer.write(buffer, 0, length);
            offset += length;
            long end = System.currentTimeMillis();
            if (end - start > 1000) {
                sendDownloadProgress(offset,contentLength);
                start = System.currentTimeMillis();
            }
        }

        urlConn.disconnect();
        writer.close();
        urlConn = null;

        // notify download completed
        renameAndNotifyCompleted();
    }

    private boolean checkIsDownAll() {
        return original.length() == contentLength
                && contentLength > 0;
    }

    private FileOutputStream supportBreakpointDownload(URL httpUrl) throws IOException {

        String range = urlConn.getHeaderField("Accept-Ranges");
        if (TextUtils.isEmpty(range) || !range.startsWith("bytes")) {
            bak.delete();
            return new FileOutputStream(bak, false);
        }

        long length = bak.length();

        urlConn.disconnect();
        urlConn = (HttpURLConnection) httpUrl.openConnection();

        urlConn.setRequestProperty("RANGE", "bytes=" + length + "-" + contentLength);
        setDefaultProperties();
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }

        return new FileOutputStream(bak, true);
    }

    private void setDefaultProperties() throws IOException {
        urlConn.setRequestProperty("Content-Type","text/html; charset=UTF-8");
        urlConn.setRequestMethod("GET");
        urlConn.setConnectTimeout(10000);
    }

    // 创建bak文件。
    private void createBakFile() {
        bak = new File(String.format("%s_%s", original.getAbsolutePath(), contentLength));
    }

    private void renameAndNotifyCompleted() {
        original.delete();
        bak.renameTo(original);
        sendDownloadComplete(original);
    }
}
