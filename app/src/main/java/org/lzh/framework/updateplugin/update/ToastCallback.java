package org.lzh.framework.updateplugin.update;

import android.content.Context;
import android.widget.Toast;

import org.lzh.framework.updatepluginlib.base.CheckCallback;
import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * @author haoge on 2018/1/9.
 */

public class ToastCallback implements CheckCallback, DownloadCallback {

    Toast mToast;

    public ToastCallback(Context context) {
        this.mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    private void show(String message) {
        mToast.setText(message);
        mToast.show();
    }

    @Override
    public void onCheckStart() {
        show("启动更新任务");
    }

    @Override
    public void hasUpdate(Update update) {
        show("检测到有更新");
    }

    @Override
    public void noUpdate() {
        show("检测到没有更新");
    }

    @Override
    public void onCheckError(Throwable t) {
        show("更新检查失败：" + t.getMessage());
    }

    @Override
    public void onUserCancel() {
        show("用户取消更新");
    }

    @Override
    public void onCheckIgnore(Update update) {
        show("用户忽略此版本更新");
    }

    @Override
    public void onDownloadStart() {
        show("开始下载");
    }

    @Override
    public void onDownloadComplete(File file) {
        show("下载完成");
    }

    @Override
    public void onDownloadProgress(long current, long total) {

    }

    @Override
    public void onDownloadError(Throwable t) {
        show("下载失败");
    }
}
