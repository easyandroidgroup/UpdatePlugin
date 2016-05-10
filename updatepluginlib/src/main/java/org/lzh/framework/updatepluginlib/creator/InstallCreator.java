package org.lzh.framework.updatepluginlib.creator;

import android.app.Dialog;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public interface InstallCreator {

    Dialog create(Update update,String path);
}
