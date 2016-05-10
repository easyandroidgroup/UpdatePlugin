package org.lzh.framework.updatepluginlib.callback;

/**
 * @author Administrator
 */
public interface UpdateDownloadCB {

    void onUpdateStart();

    void onUpdateComplete();

    void onUpdateProgress();

    void onUpdateError();
}
