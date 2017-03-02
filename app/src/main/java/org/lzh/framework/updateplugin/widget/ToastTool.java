package org.lzh.framework.updateplugin.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by haoge on 2017/3/2.
 */

public class ToastTool {
    static Handler mainHandler = new Handler(Looper.getMainLooper());
    static Toast toast;
    @SuppressLint("ShowToast")
    public static void init(Context context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static void show (final String msg) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                toast.setText(msg);
                toast.show();
            }
        });
    }
}
