package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.Updater;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 *
 */
public abstract class DialogCreator {

    private UpdateBuilder builder;
    private UpdateCheckCB checkCB;
    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
    }

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public abstract Dialog create(Update update,Activity context);

    public void sendDownloadRequest(Update update,Activity activity) {
        Updater.getInstance().downUpdate(activity,update,builder);
    }

    public void sendUserCancel() {
        if (this.checkCB != null) {
            this.checkCB.onUserCancel();
        }
    }
}