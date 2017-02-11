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

    /**
     * To show a dialog be safety
     * @param dialog The dialog instance to be shown
     */
    public static void safeShowDialog(Dialog dialog) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        Activity bindAct = getActivity(dialog);

        if (bindAct == null || bindAct.isFinishing()) {
            Log.d("Dialog shown failed:","The Dialog bind's Activity was recycled or finished!");
            return;
        }

        dialog.show();
    }

    private static Activity getActivity(Dialog dialog) {
        Activity bindAct = null;
        Context context = dialog.getContext();
        do {
            if (context instanceof Activity) {
                bindAct = (Activity) context;
                break;
            } else if (context instanceof ContextThemeWrapper) {
                context = ((ContextThemeWrapper) context).getBaseContext();
            } else {
                break;
            }
        } while (true);
        return bindAct;
    }

    /**
     * to dismiss a dialog safety
     * @param dialog The dialog to be hide
     */
    public static void safeDismissDialog(Dialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }

        Activity bindAct = getActivity(dialog);
        if (bindAct != null && !bindAct.isFinishing()) {
            dialog.dismiss();
        }
    }
}
