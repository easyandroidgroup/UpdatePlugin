package org.lzh.framework.updatepluginlib.creator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import org.lzh.framework.updatepluginlib.R;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

/**
 * @author Administrator
 */
public class DefaultNeedInstallCreator implements DialogCreator {
    @Override
    public Dialog create(Update update, final Context context) {
        String updateContent = context.getText(R.string.update_version_name)
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        return new AlertDialog.Builder(context)
                .setTitle(R.string.update_title)
                .setMessage(updateContent)
                .setNegativeButton(R.string.update_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SafeDialogOper.safeDismissDialog((Dialog) dialog);
                    }
                }).setNeutralButton(R.string.update_immediate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SafeDialogOper.safeDismissDialog((Dialog) dialog);
                    }
                }).show();
    }
}
