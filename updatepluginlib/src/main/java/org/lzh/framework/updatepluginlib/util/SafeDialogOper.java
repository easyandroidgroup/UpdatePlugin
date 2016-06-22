package org.lzh.framework.updatepluginlib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;

/**
 * To safely operation dialog show and hide
 * @author lzh
 */
public class SafeDialogOper {

    public static void safeShowDialog(Dialog dialog) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        Activity bindAct = null;
        Context context = dialog.getContext();
        if (context instanceof ContextThemeWrapper) {
            ContextThemeWrapper contextWrapper = (ContextThemeWrapper) context;
            bindAct = (Activity) contextWrapper.getBaseContext();
        } else if (context instanceof Activity) {
            bindAct = (Activity) context;
        }

        if (bindAct == null || bindAct.isFinishing()) {
            Log.d("Dialog shown failed:","The Dialog bind's Activity was recycled or finished!");
            return;
        }

        dialog.show();
    }

    public static void safeDismissDialog(Dialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }

        Context context = dialog.getContext();
        Activity activity = null;
        if (context instanceof ContextThemeWrapper) {
            ContextThemeWrapper contextWrapper = (ContextThemeWrapper) context;
            activity = (Activity) contextWrapper.getBaseContext();
        } else if (context instanceof Activity) {
            activity = (Activity) context;
        }
        if (activity != null && !activity.isFinishing()) {
            dialog.dismiss();
        }
    }
}
