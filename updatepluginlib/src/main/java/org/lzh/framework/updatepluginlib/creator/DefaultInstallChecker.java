package org.lzh.framework.updatepluginlib.creator;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * Checkout if
 * @author haoge
 */
public class DefaultInstallChecker implements InstallChecker {
    @Override
    public boolean check(Update update, String file) {
        try {
            Context context = UpdateConfig.getConfig().getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(file, PackageManager.GET_ACTIVITIES);
            return  (update.getVersionCode() == packageInfo.versionCode);
        } catch (Throwable t) {
            return false;
        }
    }
}
