package org.lzh.framework.updateplugin.update;

import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.model.CheckEntity;

/**
 * 自定义一个类.做Update更新检查时的post请求
 */
public class PostUpdateWorker extends UpdateWorker {
    @Override
    protected String check(CheckEntity entity) throws Exception {
        // 在此对url进行访问,获取返回结果并返回
        return null;
    }
}
