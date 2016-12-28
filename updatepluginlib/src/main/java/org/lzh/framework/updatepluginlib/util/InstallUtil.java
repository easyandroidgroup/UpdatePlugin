package org.lzh.framework.updatepluginlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * @author Administrator
 */
public class InstallUtil {

    public static final int REQUEST_INSTALL = 0x010101;

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
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            // Adaptive with api version 24+
            uri = UpdateInstallProvider.getUriByFile(pluginfile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(pluginfile);
        }
        intent.setDataAndType(uri, type);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent,REQUEST_INSTALL);
        }
        context.startActivity(intent);
    }

    public static int getApkVersion (Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    }
}
