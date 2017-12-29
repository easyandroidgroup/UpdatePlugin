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

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.CheckWorker;
import org.lzh.framework.updatepluginlib.model.CheckEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * 默认提供的检查更新api网络任务：
 *
 * 实现：通过原生api访问网络。并将网络返回数据直接返回。提供给框架内进行使用
 *
 * <p>若需定制。则可通过 {@link UpdateBuilder#setCheckWorker(Class)}或者 {@link UpdateConfig#setCheckWorker(Class)}进行定制
 *
 * @author haoge
 */
public class DefaultCheckWorker extends CheckWorker {
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

    @Override
    protected boolean useAsync() {
        return false;
    }

    private HttpURLConnection createHttpRequest(CheckEntity entity) throws IOException {
        if (entity.getMethod().equalsIgnoreCase("GET")) {
            return createGetRequest(entity);
        } else {
            return createPostRequest(entity);
        }
    }

    private void inflateHeaders(Map<String, String> headers, HttpURLConnection connection) {
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            connection.setRequestProperty(key, headers.get(key));
        }
    }

    private HttpURLConnection createPostRequest(CheckEntity entity) throws IOException {
        URL getUrl = new URL(entity.getUrl());
        HttpURLConnection urlConn = (HttpURLConnection) getUrl.openConnection();
        urlConn.setDoOutput(true);
        urlConn.setConnectTimeout(10000);
        urlConn.setRequestMethod("POST");
        inflateHeaders(entity.getHeaders(), urlConn);
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
        inflateHeaders(entity.getHeaders(), urlConn);
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
