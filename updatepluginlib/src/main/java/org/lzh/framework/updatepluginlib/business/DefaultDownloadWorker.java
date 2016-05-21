package org.lzh.framework.updatepluginlib.business;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.UpdateConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 */
public class DefaultDownloadWorker extends DownloadWorker {
    public static final String KEY_DOWN_SIZE = "update_download_size";
    HttpURLConnection urlConn;
    @Override
    protected void download(String url, File target) throws Exception{
        URL httpUrl = new URL(url);
        urlConn = (HttpURLConnection) httpUrl.openConnection();
        setDefaultProperties();
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }

        long contentLength = urlConn.getContentLength();
        if (checkIsDownAll(target,url,contentLength)) {
            urlConn.disconnect();
            urlConn = null;
            return;
        }
        RandomAccessFile raf = supportBreakpointDownload(target, httpUrl, url);

        long offset = target.exists() ? (int) target.length() : 0;
        InputStream inputStream = urlConn.getInputStream();
        byte[] buffer = new byte[8 * 1024];
        int length;
        long start = System.currentTimeMillis();

        while ((length = inputStream.read(buffer)) != -1) {
            raf.write(buffer, 0, length);
            offset += length;
            saveDownloadSize(url,offset);
            long end = System.currentTimeMillis();
            if (end - start > 1000) {
                sendUpdateProgress(offset,contentLength);
            }
        }

        urlConn.disconnect();
        raf.close();
        urlConn = null;
    }

    private boolean checkIsDownAll(File target,String url,long contentLength) {
        long lastDownSize = getLastDownloadSize(url);
        long length = target.length();
        long lastTotalSize = getLastDownloadTotalSize(url);
        if (lastDownSize == length
                && lastTotalSize == lastTotalSize
                && lastDownSize != 0
                && lastDownSize == contentLength)
            return true;
        return false;
    }

    private RandomAccessFile supportBreakpointDownload(File target, URL httpUrl, String url) throws IOException {

        String range = urlConn.getHeaderField("Accept-Ranges");
        if (TextUtils.isEmpty(range) || !range.startsWith("bytes")) {
            target.delete();
            return new RandomAccessFile(target,"rw");
        }

        long lastDownSize = getLastDownloadSize(url);
        long length = target.length();
        long lastTotalSize = getLastDownloadTotalSize(url);
        long contentLength = Long.parseLong(urlConn.getHeaderField("Content-Length"));
        saveDownloadTotalSize(url,contentLength);
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
     //   urlConn.setDoOutput(true);  这会把request method 强制变成post请求 造成下载失败
     //   urlConn.setDoInput(true);
    }

    private long getLastDownloadSize(String url) {
        SharedPreferences sp = UpdateConfig.getConfig().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getLong(url,0);
    }

    private long getLastDownloadTotalSize (String url) {
        SharedPreferences sp = UpdateConfig.getConfig().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getLong(url + "_total_size",0);
    }

    private void saveDownloadSize (String url,long size) {
        SharedPreferences sp = UpdateConfig.getConfig().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(url,size);
        editor.commit(); // editor.apply() 是异步提交修改 同时修改造成死锁 ANR
    }

    private void saveDownloadTotalSize(String url,long totalSize) {
        SharedPreferences sp = UpdateConfig.getConfig().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(url + "_total_size",totalSize);
        editor.commit();
    }
}
