package org.lzh.framework.updatepluginlib.creator;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 *
 */
public interface DialogCreator {
    Dialog create(Update update,Activity context,UpdateBuilder builder);
}
