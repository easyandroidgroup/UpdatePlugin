package org.lzh.framework.updateplugin.update;

import android.app.Activity;
import android.app.ProgressDialog;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.creator.DownloadCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;

/**
 *
 */
public class CustomNeedDownloadCreator implements DownloadCreator {
    /**
     * 此方法返回一个UpdateDownloadCB接口实现类。
     * @param update   jsonParser配置方法返回的update实体类
     * @param activity 此Dialog创建器使用的activity实例。默认为使用UpdateBuilder.check(activity)方法传入。
     *                 需注意：此activity传入后使用弱引用进行保存。故有可能使用时activity为null。或者已经finished.
     * @return UpdateDownloadCB实体类。下载任务会将下载进度通过此实体类传递过来。
     */
    @Override
    public UpdateDownloadCB create(Update update, Activity activity) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        SafeDialogOper.safeShowDialog(dialog);
        // 此处返回的UpdateDownloadCB实体类为用于更新进度条Dialog的，如果需要使用Notification进行更新进度显示。则
        // 在此自定义进行刷新Notification操作即可
        return new UpdateDownloadCB() {
            @Override
            public void  onUpdateStart() {
            }

            @Override
            public void onUpdateComplete(File file) {
                SafeDialogOper.safeDismissDialog(dialog);
            }

            @Override
            public void onUpdateProgress(long current, long total) {
                int percent = (int) (current * 1.0f / total * 100);
                dialog.setProgress(percent);
            }

            @Override
            public void onUpdateError(int code, String errorMsg) {
                SafeDialogOper.safeDismissDialog(dialog);
            }
        };
    }
}
