package org.lzh.framework.updatepluginlib.creator;

import android.app.Dialog;
import android.content.Context;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 *
 */
public interface DialogCreator {
    Dialog create(Update update,Context context);
}
