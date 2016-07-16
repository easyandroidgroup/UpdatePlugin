package org.lzh.framework.updatepluginlib.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This entity contains all of data used by {@link org.lzh.framework.updatepluginlib.business.UpdateWorker}
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
