package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public abstract class InstallCreator {

    private UpdateCheckCB checkCB;

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public abstract Dialog create(Update update,String path,Activity activity);

    public void sendUserCancel() {
        if (this.checkCB != null) {
            this.checkCB.onUserCancel();
        }
    }
}
