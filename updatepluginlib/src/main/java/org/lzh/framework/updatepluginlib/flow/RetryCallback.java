package org.lzh.framework.updatepluginlib.flow;

import android.util.Log;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.base.CheckCallback;
import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.Utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author haoge on 2017/12/26.
 */

public final class RetryCallback implements CheckCallback, DownloadCallback, Runnable{

    private UpdateBuilder builder;
    private long retryTime = TimeUnit.HOURS.toHours(2);// 当失败或取消后的重启间隔时间。

    public RetryCallback(UpdateBuilder builder) {
        this.builder = builder;
    }

    public void setRetryTime(long retryTime) {
        this.retryTime = retryTime;
    }

    @Override
    public void onDownloadStart() {

    }

    @Override
    public void onDownloadComplete(File file) {
        retry();
    }

    @Override
    public void onDownloadProgress(long current, long total) {

    }

    @Override
    public void onDownloadError(Throwable t) {
        retry();
    }

    @Override
    public void onCheckStart() {

    }

    @Override
    public void hasUpdate(Update update) {

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

    private final synchronized void retry() {
        Utils.getMainHandler().removeCallbacks(this);
        Utils.getMainHandler().postDelayed(this, retryTime);
    }

    @Override
    public void run() {
        Log.d(CallbackDelegate.TAG, "Restart update for daemon");
        builder.checkWithDaemon(retryTime);
    }
}
