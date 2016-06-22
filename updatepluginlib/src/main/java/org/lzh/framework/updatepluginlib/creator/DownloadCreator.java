package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * Download dialog creator
 * @author lzh
 */
public interface DownloadCreator {

    /**
     * To create a download dialog when should be shown,this method returns a {@link UpdateDownloadCB},
     * the download callback will be used in {@link org.lzh.framework.updatepluginlib.business.DownloadWorker},
     * and also called with it to update download progress,
     * @param update The update instance created by {@link org.lzh.framework.updatepluginlib.model.UpdateParser#parse(String)}
     * @param activity The activity instance,cause it is be saved with weak ref,so the context
     *                will be null or finished sometimes when you finish you activity before,
     */
    UpdateDownloadCB create(Update update, Activity activity);
}
