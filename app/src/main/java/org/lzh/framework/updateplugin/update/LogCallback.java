package org.lzh.framework.updateplugin.update;

import android.util.Log;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * 用于对更新任务添加日志打印的回调
 */
public class LogCallback implements UpdateCheckCB, UpdateDownloadCB{

    @Override
    public void onDownloadStart() {
        log("启动下载任务");
    }

    @Override
    public void onDownloadComplete(File file) {
        log("下载apk完成");
    }

    @Override
    public void onDownloadProgress(long current, long total) {
        log("下载中: current:" + current + " & total:" + total);
    }

    @Override
    public void onDownloadError(Throwable t) {
        log("哦豁，下载失败了：" + t.getMessage());
    }

    @Override
    public void onCheckStart() {
        log("开始启动更新检查");
    }

    @Override
    public void hasUpdate(Update update) {
        log("检测到有新版本需要更新");
    }

    @Override
    public void noUpdate() {
        log("哦豁。当前没得新版本更新");
    }

    @Override
    public void onCheckError(Throwable t) {
        log("检查更新失败了：" + t.getMessage());
    }

    @Override
    public void onUserCancel() {
        log("用户取消了更新");
    }

    @Override
    public void onCheckIgnore(Update update) {
        log("用于忽略了更新");
    }

    private void log(String message) {
        Log.d("update_log", message);
    }
}
