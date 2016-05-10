package org.lzh.framework.updatepluginlib.creator;

import android.app.Dialog;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public interface DownloadCreator {
    Dialog create (Update update,UpdateDownloadCB downloadCB);
}
