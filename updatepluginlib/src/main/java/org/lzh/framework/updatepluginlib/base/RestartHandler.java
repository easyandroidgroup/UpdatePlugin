package org.lzh.framework.updatepluginlib.base;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.impl.DefaultRestartHandler;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.L;
import org.lzh.framework.updatepluginlib.util.Utils;

import java.io.File;

/**
 * 后台重启任务定制接口。此接口用于定制后台任务的重启逻辑，可参考{@link DefaultRestartHandler}
 * @author haoge on 2018/3/22.
 */
public abstract class RestartHandler implements CheckCallback, DownloadCallback{

    protected UpdateBuilder builder;
    protected long retryTime;// 重启间隔
    private RetryTask task;

    public final void attach(UpdateBuilder builder, long retryTime) {
        this.builder = builder;
        this.retryTime = Math.max(1, retryTime);
    }

    public final void detach() {
        builder = null;
    }

    protected final void retry() {
        if (builder == null) {
            return;
        }
        if (task == null) {
            task = new RetryTask();
        }
        Utils.getMainHandler().removeCallbacks(task);
        Utils.getMainHandler().postDelayed(task, retryTime * 1000);
    }

    private class RetryTask implements Runnable {

        @Override
        public void run() {
            if (builder != null) {
                L.d("Restart update for daemon");
                builder.checkWithDaemon(retryTime);
            }
        }
    }

    // =======所有生命周期回调均为空实现，由子类复写相应的生命周期函数进行重启操作。====
    @Override
    public void onDownloadStart() {}

    @Override
    public void onCheckStart() {}

    @Override
    public void onDownloadComplete(File file) {}

    @Override
    public void hasUpdate(Update update) {}

    @Override
    public void onDownloadProgress(long current, long total) {}

    @Override
    public void noUpdate() {}

    @Override
    public void onDownloadError(Throwable t) {}

    @Override
    public void onCheckError(Throwable t) {}

    @Override
    public void onUserCancel() {}

    @Override
    public void onCheckIgnore(Update update) {}
}
