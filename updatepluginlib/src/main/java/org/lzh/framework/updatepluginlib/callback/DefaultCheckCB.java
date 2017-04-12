package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.Updater;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

/**
 * default check callback to receive update event send by {@link org.lzh.framework.updatepluginlib.business.UpdateWorker}
 */
public final class DefaultCheckCB implements UpdateCheckCB,Recyclable {

    private UpdateBuilder builder;
    private UpdateCheckCB checkCB;

    public void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
        this.checkCB = builder.getCheckCB();
    }

    @Override
    public void onCheckStart() {
        try {
            if (checkCB != null) {
                checkCB.onCheckStart();
            }
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

    /**
     * Receive and pass has_update event send by {@link org.lzh.framework.updatepluginlib.business.UpdateWorker#sendHasUpdate(Update)}<br>
     * Create update dialog if should be shown according to {@link org.lzh.framework.updatepluginlib.strategy.UpdateStrategy#isShowUpdateDialog(Update)}<br>
     */
    @Override
    public void hasUpdate(Update update) {
        try {
            if (checkCB != null) {
                checkCB.hasUpdate(update);
            }

            if (!builder.getStrategy().isShowUpdateDialog(update)) {
                Updater.getInstance().downUpdate(update,builder);
                return;
            }

            Activity current = ActivityManager.get().topActivity();

            DialogCreator creator = builder.getUpdateDialogCreator();
            creator.setBuilder(builder);
            creator.setCheckCB(builder.getCheckCB());
            Dialog dialog = creator.create(update,current);
            SafeDialogOper.safeShowDialog(dialog);

            release();
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

    /**
     * Receive and pass no_update event send by {@link UpdateWorker#sendNoUpdate()}
     */
    @Override
    public void noUpdate() {
        try {
            if (checkCB != null) {
                checkCB.noUpdate();
            }
            release();
        } catch (Throwable t) {
            onCheckError(t);
        }

    }

    /**
     * Receive and pass check_error event send by {@link UpdateWorker#sendOnErrorMsg(Throwable)}
     */
    @Override
    public void onCheckError(Throwable t) {
        try {
            if (checkCB != null) {
                checkCB.onCheckError(t);
            }
        } catch (Throwable ignore) {
            ignore.printStackTrace();
        } finally {
            release();
        }
    }

    /**
     * will be never invoke
     */
    @Override
    public void onUserCancel() {
        try {
            if (checkCB != null) {
                checkCB.onUserCancel();
            }
            release();
        } catch (Throwable t) {
            onCheckError(t);
        }

    }

    @Override
    public void onCheckIgnore(Update update) {
        try {
            if (checkCB != null) {
                checkCB.onCheckIgnore(update);
            }
            release();
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

    @Override
    public void release() {
        this.builder = null;
        this.checkCB = null;
    }
}
