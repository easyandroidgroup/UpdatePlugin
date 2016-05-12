package org.lzh.framework.updatepluginlib.callback;

import java.io.File;

/**
 * @author Administrator
 */
public interface UpdateDownloadCB {

    void onUpdateStart();

    void onUpdateComplete(File file);

    void onUpdateProgress(long current, long total);

    void onUpdateError(int code, String errorMsg);


}
