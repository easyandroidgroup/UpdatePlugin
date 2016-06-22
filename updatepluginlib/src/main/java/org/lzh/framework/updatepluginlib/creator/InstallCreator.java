package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.InstallUtil;
import org.lzh.framework.updatepluginlib.util.Recycler;
import org.lzh.framework.updatepluginlib.util.Recycler.Recycleable;

/**
 *
 * @author lzh
 */
public abstract class InstallCreator implements Recycleable{

    private UpdateCheckCB checkCB;

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public abstract Dialog create(Update update,String path,Activity activity);

    /**
     * request to install this apk file
     * @param filename the absolutely file name that downloaded
     */
    public void sendToInstall(String filename) {
        InstallUtil.installApk(UpdateConfig.getConfig().getContext(),filename);
        Recycler.release(this);
    }

    /**
     * request cancel install action
     */
    public void sendUserCancel() {
        if (this.checkCB != null) {
            this.checkCB.onUserCancel();
        }

        Recycler.release(this);
    }

    @Override
    public void release() {
        this.checkCB = null;
    }
}
