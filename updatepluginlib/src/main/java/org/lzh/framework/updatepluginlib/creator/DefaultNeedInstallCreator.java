package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import org.lzh.framework.updatepluginlib.R;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.InstallUtil;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

/**
 * @author Administrator
 */
public class DefaultNeedInstallCreator implements InstallCreator {
    @Override
    public Dialog create(Update update, final String path, final Activity activity) {
        String updateContent = activity.getText(R.string.update_version_name)
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        return new AlertDialog.Builder(activity)
                .setTitle(R.string.install_title)
                .setMessage(updateContent)
                .setNegativeButton(R.string.update_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SafeDialogOper.safeDismissDialog((Dialog) dialog);
                    }
                }).setNeutralButton(R.string.install_immediate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SafeDialogOper.safeDismissDialog((Dialog) dialog);
                        InstallUtil.installApk(activity,path);
                    }
                }).show();
    }

}
