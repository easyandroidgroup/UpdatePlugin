package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.Updater;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.lang.ref.WeakReference;

/**
 */
public class DefaultCheckCB implements UpdateCheckCB {

    private WeakReference<Activity> actRef = null;
    private UpdateBuilder builder;
    private UpdateCheckCB checkCB;

    public DefaultCheckCB(Activity context) {
        this.actRef = new WeakReference<>(context);
    }

    public void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
        checkCB = builder.getCheckCB();
    }

    @Override
    public void hasUpdate(Update update) {
        if (checkCB != null) {
            checkCB.hasUpdate(update);
        }
        if (!builder.getStrategy().isShowUpdateDialog(update)) {
            Updater.getInstance().downUpdate(actRef.get(),update,builder);
            return;
        }

        DialogCreator creator = builder.getUpdateDialogCreator();
        creator.setBuilder(builder);
        creator.setCheckCB(this);
        Dialog dialog = creator.create(update,actRef.get());

        if (update.isForced()) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        SafeDialogOper.safeShowDialog(dialog);
    }

    @Override
    public void noUpdate() {
        if (checkCB != null) {
            checkCB.noUpdate();
        }
    }

    @Override
    public void onCheckError(int code, String errorMsg) {
        if (checkCB != null) {
            checkCB.onCheckError(code,errorMsg);
        }
    }

    @Override
    public void onUserCancel() {
        if (checkCB != null) {
            checkCB.onUserCancel();
        }
    }
}
