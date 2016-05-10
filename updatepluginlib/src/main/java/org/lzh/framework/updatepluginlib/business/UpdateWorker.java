package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;

/**
 * The class is to check
 */
public abstract class UpdateWorker {

    protected String url;
    protected UpdateDownloadCB downloadCB;

    public UpdateWorker(String url, UpdateDownloadCB downloadCB) {
        this.url = url;
        this.downloadCB = downloadCB;
    }

    public abstract void execute();
}
