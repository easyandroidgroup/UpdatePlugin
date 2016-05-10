package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;

/**
 * @author Administrator
 */
public class DownloadRunnable implements Runnable {

    private String url;
    private UpdateDownloadCB downloadCB;

    public DownloadRunnable(String url, UpdateDownloadCB downloadCB) {
        this.url = url;
        this.downloadCB = downloadCB;
    }

    @Override
    public void run() {

    }
}
