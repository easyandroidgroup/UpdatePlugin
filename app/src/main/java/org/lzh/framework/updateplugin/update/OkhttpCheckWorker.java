package org.lzh.framework.updateplugin.update;

import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.model.CheckEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * <p>
 * 定制一个简单的使用okHttp做更新接口检查的网络任务。
 * 框架默认使用参考：{@link org.lzh.framework.updatepluginlib.business.DefaultUpdateWorker}
 * </p>
 */
public class OkhttpCheckWorker extends UpdateWorker {

    private static OkHttpClient sOkClient;
    @Override
    protected String check(CheckEntity entity) throws Exception {
        // 自定网络任务。在此通过同步请求的方式调用即可
        Request.Builder builder = new Request.Builder().url(entity.getUrl());
        if ("GET".equalsIgnoreCase(entity.getMethod())) {
            builder.method("GET",null);
        } else {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            Map<String, String> params = entity.getParams();
            if (params == null) {
                params = new HashMap<>();
            }
            Set<String> keys = params.keySet();
            for (String key : keys) {
                bodyBuilder.add(key,params.get(key));
            }
            builder.method("POST",bodyBuilder.build());
        }
        Request request = builder.build();
        Call call = sOkClient.newCall(request);
        return call.execute().body().string();
    }

    @Override
    protected boolean useAsync() {
        return false;
    }

    static {
        sOkClient = new OkHttpClient();
    }
}
