package org.lzh.framework.updatepluginlib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * @author Administrator
 */
public class InstallUtil {

    /**
     * install apk
     * @param context the context is used to send install apk broadcast;
     * @param filename the file name to be installed;
     */
    public static void installApk(Context context, String filename) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        File pluginfile = new File(filename);
        intent.setDataAndType(Uri.fromFile(pluginfile), type);
        context.startActivity(intent);
    }
}
