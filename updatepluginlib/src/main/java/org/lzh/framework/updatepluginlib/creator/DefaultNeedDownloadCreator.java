package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;

/**
 * @author Administrator
 */
public class DefaultNeedDownloadCreator implements DownloadCreator {
    @Override
    public UpdateDownloadCB create(Update update,Activity activity) {
        if (activity == null || activity.isFinishing()) {
            Log.e("DownDialogCreator--->","show download dialog failed:activity was recycled or finished");
            throw new IllegalStateException("Activity was recycled or finished,dialog shown failed!");
        }
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        SafeDialogOper.safeShowDialog(dialog);
        return new UpdateDownloadCB() {
            @Override
            public void onUpdateStart() {
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
