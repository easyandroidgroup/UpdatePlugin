package org.lzh.framework.updatepluginlib.callback;

import java.io.File;

/**
 * The download callback
 * @author lzh
 */
public interface UpdateDownloadCB {

    void onUpdateStart();

    void onUpdateComplete(File file);

    void onUpdateProgress(long current, long total);

    void onUpdateError(int code, String errorMsg);


}
