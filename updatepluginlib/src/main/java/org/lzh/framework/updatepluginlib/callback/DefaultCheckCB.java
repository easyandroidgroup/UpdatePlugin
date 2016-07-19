package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.Updater;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.Recycler;
import org.lzh.framework.updatepluginlib.util.Recycler.Recycleable;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.lang.ref.WeakReference;

/**
 * default check callback to receive update event send by {@link org.lzh.framework.updatepluginlib.business.UpdateWorker}
 */
public class DefaultCheckCB implements UpdateCheckCB,Recycleable {

    private WeakReference<Activity> actRef = null;
    private UpdateBuilder builder;
    private UpdateCheckCB checkCB;

    public DefaultCheckCB(Activity context) {
        this.actRef = new WeakReference<>(context);
    }

    public void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
        this.checkCB = builder.getCheckCB();
    }

    /**
     * Receive and pass has_update event send by {@link org.lzh.framework.updatepluginlib.business.UpdateWorker#sendHasUpdate(Update)}<br>
     * Create update dialog if should be shown according to {@link org.lzh.framework.updatepluginlib.strategy.UpdateStrategy#isShowUpdateDialog(Update)}<br>
     */
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
        creator.setCheckCB(builder.getCheckCB());
        Dialog dialog = creator.create(update,actRef.get());

        if (update.isForced() && dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        SafeDialogOper.safeShowDialog(dialog);

        Recycler.release(this);
    }

    /**
     * Receive and pass no_update event send by {@link UpdateWorker#sendNoUpdate()}
     */
    @Override
    public void noUpdate() {
        if (checkCB != null) {
            checkCB.noUpdate();
        }

        Recycler.release(this);
    }

    /**
     * Receive and pass check_error event send by {@link UpdateWorker#sendOnErrorMsg(int, String)}
     */
    @Override
    public void onCheckError(int code, String errorMsg) {
        if (checkCB != null) {
            checkCB.onCheckError(code,errorMsg);
        }

        Recycler.release(this);
    }

    /**
     * will be never invoke
     */
    @Override
    public void onUserCancel() {
        if (checkCB != null) {
            checkCB.onUserCancel();
        }

        Recycler.release(this);
    }

    @Override
    public void release() {
        this.actRef = null;
        this.builder = null;
        this.checkCB = null;
    }
}
