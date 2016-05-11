package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import org.lzh.framework.updatepluginlib.R;
import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.Updater;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

/**
 * @author Administrator
 */
public class DefaultNeedUpdateCreator implements DialogCreator {
    @Override
    public Dialog create(final Update update, final Activity activity, final UpdateBuilder builder) {
        String updateContent = activity.getText(R.string.update_version_name)
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        return new AlertDialog.Builder(activity)
                .setMessage(updateContent)
                .setTitle(R.string.update_title)
                .setNegativeButton(R.string.update_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SafeDialogOper.safeDismissDialog((Dialog) dialog);
                    }
                }).setNeutralButton(R.string.update_immediate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Updater.getInstance().downUpdate(activity,update,builder);
                        SafeDialogOper.safeDismissDialog((Dialog) dialog);
                    }
                }).show();
    }
}
