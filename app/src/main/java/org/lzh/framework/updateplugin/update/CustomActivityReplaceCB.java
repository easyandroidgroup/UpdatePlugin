package org.lzh.framework.updateplugin.update;

import android.app.Activity;

import org.lzh.framework.updateplugin.ActivityStack;
import org.lzh.framework.updatepluginlib.callback.ActivityReplaceCB;

/**
 * Created by admin on 16/8/31.
 */
public class CustomActivityReplaceCB implements ActivityReplaceCB {
    @Override
    public Activity replace(Activity oldActivity) {
        return ActivityStack.current();
    }
}
