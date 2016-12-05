package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.InstallUtil;
import org.lzh.framework.updatepluginlib.util.Recycler;
import org.lzh.framework.updatepluginlib.util.Recycler.Recycleable;
import org.lzh.framework.updatepluginlib.util.UpdatePreference;

/**
 *
 * @author lzh
 */
public abstract class InstallCreator implements Recycleable{

    private UpdateCheckCB checkCB;
    protected InstallChecker installChecker;
    protected Update update;

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public void setInstallChecker (InstallChecker checker) {
        this.installChecker = checker;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public abstract Dialog create(Update update, String path, Activity activity);

    /**
     * request to install this apk file
     * @param filename the absolutely file name that downloaded
     */
    public void sendToInstall(String filename) {
        if (installChecker == null || installChecker.check(update,filename)) {
            InstallUtil.installApk(UpdateConfig.getConfig().getContext(),filename);
        } else {
            checkCB.onCheckError(-1,String.format("apk %s checked failed",filename));
        }
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

    public void sendCheckIgnore(Update update) {
        if (this.checkCB != null) {
            this.checkCB.onCheckIgnore(update);
        }
        UpdatePreference.saveIgnoreVersion(update.getVersionCode());
        Recycler.release(this);
    }

    @Override
    public void release() {
        this.checkCB = null;
        this.installChecker = null;
        this.update = null;
    }
}
