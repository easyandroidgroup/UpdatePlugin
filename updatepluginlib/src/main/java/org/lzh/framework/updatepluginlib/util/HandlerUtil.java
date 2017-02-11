package org.lzh.framework.updatepluginlib.util;

import android.os.Handler;
import android.os.Looper;

public class HandlerUtil {
    private static Handler handler;
    public static Handler getMainHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }
}
