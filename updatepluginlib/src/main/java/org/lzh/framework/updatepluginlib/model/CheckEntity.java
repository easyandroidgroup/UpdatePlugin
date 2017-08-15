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
package org.lzh.framework.updatepluginlib.model;

import org.lzh.framework.updatepluginlib.business.UpdateWorker;

import java.util.HashMap;
import java.util.Map;

/**
 * This entity contains all of data used by {@link UpdateWorker}
 * Created by lzh on 2016/7/15.
 */
public class CheckEntity {
    private HttpMethod method = HttpMethod.GET;
    private String url;
    private Map<String,String> params;

    public HttpMethod getMethod() {
        return method;
    }

    public CheckEntity setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public CheckEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, String> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public CheckEntity setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }
}
