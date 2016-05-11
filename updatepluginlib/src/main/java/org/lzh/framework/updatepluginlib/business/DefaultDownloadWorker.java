package org.lzh.framework.updatepluginlib.business;

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
    HttpURLConnection urlConn;
    private URL httpUrl;
    @Override
    protected void download(String url, File target) throws Exception{
        httpUrl = new URL(url);
        urlConn = (HttpURLConnection) httpUrl.openConnection();
        setDefaultProperties();
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }
        target.delete();
        RandomAccessFile raf = new RandomAccessFile(target,"rw");

        long contentLength = urlConn.getContentLength();
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
                sendUpdateProgress(offset,contentLength);
            }
        }

        urlConn.disconnect();
        raf.close();
    }

    private void setDefaultProperties() throws IOException {
        urlConn.setRequestMethod("GET");
        urlConn.setConnectTimeout(10000);
        urlConn.setDoOutput(true);
        urlConn.setDoInput(true);
    }
}
