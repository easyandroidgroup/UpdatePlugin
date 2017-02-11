package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class DefaultUpdateWorker extends UpdateWorker {
    @Override
    protected String check(CheckEntity entity) throws Exception {
        HttpURLConnection urlConn = createHttpRequest(entity);

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            urlConn.disconnect();
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }

        BufferedReader bis = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));

        StringBuilder sb = new StringBuilder();
        String lines;
        while ((lines = bis.readLine()) != null) {
            sb.append(lines);
        }

        urlConn.disconnect();

        return sb.toString();
    }

    private HttpURLConnection createHttpRequest(CheckEntity entity) throws IOException {
        if (entity.getMethod().equals(HttpMethod.GET)) {
            return createGetRequest(entity);
        } else {
            return createPostRequest(entity);
        }

    }

    private HttpURLConnection createPostRequest(CheckEntity entity) throws IOException {
        URL getUrl = new URL(entity.getUrl());
        HttpURLConnection urlConn = (HttpURLConnection) getUrl.openConnection();
        urlConn.setDoOutput(true);
        urlConn.setConnectTimeout(10000);
        urlConn.setRequestMethod("POST");
        String params = createParams(entity.getParams());
        urlConn.getOutputStream().write(params.getBytes("utf-8"));
        return urlConn;
    }

    private HttpURLConnection createGetRequest(CheckEntity entity) throws IOException {
        StringBuilder builder = new StringBuilder(entity.getUrl());
        Map<String,String> params = entity.getParams();
        if (params.size() > 0) {
            builder.append("?").append(createParams(params));
        }
        String url = builder.toString();

        URL getUrl = new URL(url);
        HttpURLConnection urlConn = (HttpURLConnection) getUrl.openConnection();
        urlConn.setDoInput(true);
        urlConn.setUseCaches(false);
        urlConn.setConnectTimeout(10000);
        urlConn.setRequestMethod("GET");
        urlConn.connect();
        return urlConn;
    }

    private String createParams(Map<String,String> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder paramsBuilder = new StringBuilder();
        for (String key : params.keySet()) {
            paramsBuilder.append(key).append("=").append(params.get(key)).append("&");
        }
        paramsBuilder.deleteCharAt(paramsBuilder.length() - 1);
        return paramsBuilder.toString();
    }

}
