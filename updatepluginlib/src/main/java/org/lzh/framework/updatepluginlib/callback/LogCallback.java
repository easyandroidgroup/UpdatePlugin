package org.lzh.framework.updatepluginlib.callback;

import android.util.Log;

import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * 默认提供的更新回调器。用于对更新状态进行打印
 *
 * @author haoge on 2017/9/26.
 */
public final class LogCallback implements UpdateCheckCB, UpdateDownloadCB{

    private static LogCallback callback = new LogCallback();
    private LogCallback() {}
    public static LogCallback get() {
        return callback;
    }

    private final String TAG = "UpdatePluginLog";
    public static boolean LOG = true;

    @Override
    public void onDownloadStart() {
        log("start downloading。。。");
    }

    @Override
    public void onDownloadComplete(File file) {
        log(String.format("Download completed with file [%s]", file.getAbsoluteFile()));
    }

    @Override
    public void onDownloadProgress(long current, long total) {
        log(String.format("Downloading... current is %s and total is %s", current, total));
    }

    @Override
    public void onDownloadError(Throwable t) {
    }

    @Override
    public void onCheckStart() {

    }

    @Override
    public void hasUpdate(Update update) {

    }

    @Override
    public void noUpdate() {

    }

    @Override
    public void onCheckError(Throwable t) {

    }

    @Override
    public void onUserCancel() {

    }

    @Override
    public void onCheckIgnore(Update update) {

    }

    private void log(String message) {
        if (LOG) {
            Log.d(TAG, message);
        }
    }
}
