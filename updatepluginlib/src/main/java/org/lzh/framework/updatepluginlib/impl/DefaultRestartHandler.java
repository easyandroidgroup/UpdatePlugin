package org.lzh.framework.updatepluginlib.impl;

import org.lzh.framework.updatepluginlib.base.RestartHandler;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * 默认使用的更新任务重启操作类。
 * @author haoge on 2018/3/22.
 */
public class DefaultRestartHandler extends RestartHandler {

    // ====复写对应的回调并进行任务重启======
    @Override
    public void onDownloadComplete(File file) {
        retry();
    }

    @Override
    public void onDownloadError(Throwable t) {
        retry();
    }

    @Override
    public void noUpdate() {
        retry();
    }

    @Override
    public void onCheckError(Throwable t) {
        retry();
    }

    @Override
    public void onUserCancel() {
        retry();
    }

    @Override
    public void onCheckIgnore(Update update) {
        retry();
    }
}
