package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.Updater;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.Recycler;
import org.lzh.framework.updatepluginlib.util.Recycler.Recycleable;

/**
 *
 */
public abstract class DialogCreator implements Recycleable{
    private UpdateBuilder builder;
    private UpdateCheckCB checkCB;
    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
    }

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    /**
     * to create update dialog when should notice user there is a new version to be updated,
     * @param update The update instance created by {@link org.lzh.framework.updatepluginlib.model.UpdateParser#parse(String)}
     * @param context The activity instance,cause it is be saved with weak ref,so the context
     *                will be null or finished sometimes when you finish you activity before,
     * @return The update dialog instance.not yet show
     */
    public abstract Dialog create(Update update,Activity context);

    /**
     * invoked this method when you want to start download task
     * @param update should not be null,
     * @param activity The activity instance to show download dialog
     */
    public void sendDownloadRequest(Update update,Activity activity) {
        Updater.getInstance().downUpdate(activity,update,builder);
        Recycler.release(this);
    }

    /**
     * invoked this method when you press cancel button
     */
    public void sendUserCancel() {
        if (this.checkCB != null) {
            this.checkCB.onUserCancel();
        }
        Recycler.release(this);
    }

    public void sendUserIgnore(Update update) {
        if (this.checkCB != null) {
            this.checkCB.onCheckIgnore(update);
        }
        Recycler.release(this);
    }

    @Override
    public void release() {
        this.builder = null;
        this.checkCB = null;
    }
}