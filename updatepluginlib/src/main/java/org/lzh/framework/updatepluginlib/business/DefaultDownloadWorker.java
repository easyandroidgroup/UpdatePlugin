package org.lzh.framework.updatepluginlib.business;

import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.util.UpdatePreference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

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
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                raf.write(buffer, 0, length);
                offset += length;
                long end = System.currentTimeMillis();
                if (end - start > 1000) {
                    sendUpdateProgress(offset,contentLength);
                    start = System.currentTimeMillis();
                }
            }
        } finally {
            //
            UpdatePreference.saveDownloadSize(url,offset);
        }


        urlConn.disconnect();
        raf.close();
        urlConn = null;
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
