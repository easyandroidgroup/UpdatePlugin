package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public interface DownloadCreator {
    UpdateDownloadCB create (Update update,Activity activity);
}
