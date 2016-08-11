package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;

import org.lzh.framework.updatepluginlib.R;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.lang.ref.WeakReference;

/**
 * @author Administrator
 */
public class DefaultNeedInstallCreator extends InstallCreator {

    private WeakReference<Activity> activityRef;

    @Override
    public Dialog create(final Update update, final String path, final Activity activity) {
        if (activity == null || activity.isFinishing()) {
            Log.e("DownDialogCreator--->","show install dialog failed:activity was recycled or finished");
            return null;
        }
        activityRef = new WeakReference<>(activity);
        String updateContent = activity.getText(R.string.update_version_name)
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(R.string.install_title)
                .setMessage(updateContent)
                .setPositiveButton(R.string.install_immediate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!update.isForced()) {
                            SafeDialogOper.safeDismissDialog((Dialog) dialog);
                        }
                        sendToInstall(path);
                    }
                });

        if (!update.isForced() && update.isIgnore()) {
            builder.setNeutralButton(R.string.update_ignore, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendCheckIgnore(update);
                    SafeDialogOper.safeDismissDialog((Dialog) dialog);
                }
            });
        }

        if (!update.isForced()) {
            builder.setNegativeButton(R.string.update_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendUserCancel();
                    SafeDialogOper.safeDismissDialog((Dialog) dialog);
                }
            });
        }
        AlertDialog installDialog = builder.create();
        installDialog.setCancelable(false);
        installDialog.setCanceledOnTouchOutside(false);
        return installDialog;
    }

}
