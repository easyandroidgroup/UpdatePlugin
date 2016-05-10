package org.lzh.framework.updatepluginlib.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.view.ContextThemeWrapper;

/**
 * @author Administrator
 */
public class SafeDialogOper {

    public static void safeShowDialog(DialogFragment dialog, Activity activity, String tag) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (dialog == null || dialog.isDetached()) {
            return;
        }

        dialog.show(activity.getFragmentManager(), tag);
    }

    public static void safeShowDialog(Dialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dialog.show();
    }

    public static void safeDismissDialog(DialogFragment dialog) {
        if (dialog == null || dialog.isDetached()) {
            return;
        }
        dialog.dismiss();
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
