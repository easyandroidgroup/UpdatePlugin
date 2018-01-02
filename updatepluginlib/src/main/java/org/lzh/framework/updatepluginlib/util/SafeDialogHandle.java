/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lzh.framework.updatepluginlib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;

/**
 * 用于安全的进行Dialog显示隐藏的工具类
 * @author haoge
 */
public class SafeDialogHandle {

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
