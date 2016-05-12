package org.lzh.framework.updatepluginlib.callback;

import java.io.File;

/**
 * @author Administrator
 */
public class EmptyDownloadCB implements UpdateDownloadCB {
    @Override
    public void onUpdateStart() {

    }

    @Override
    public void onUpdateComplete(File file) {

    }

    @Override
    public void onUpdateProgress(long current, long total) {

    }

    @Override
    public void onUpdateError(int code, String errorMsg) {

    }

}
