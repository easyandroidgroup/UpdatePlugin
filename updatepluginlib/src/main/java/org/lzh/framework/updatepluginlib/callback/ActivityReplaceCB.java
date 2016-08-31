package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;

/**
 * A interface to replace your activity when is finished or should shown dialog with another activity
 * Created by lzh on 16/8/23.
 */
public interface ActivityReplaceCB {

    /**
     * replace activity callback
     * @param oldActivity the last activity,maybe null
     * @return the activity returned to shown dialog;
     */
    Activity replace(Activity oldActivity);
}
