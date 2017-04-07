package org.lzh.framework.updatepluginlib.creator;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author haoge
 */
public class DefaultFileChecker implements FileChecker {

    @Override
    public boolean checkPreFile(Update update, String file) {
        try {
            Context context = UpdateConfig.getConfig().getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(file, PackageManager.GET_ACTIVITIES);
            return  (update.getVersionCode() == packageInfo.versionCode);
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public boolean checkAfterDownload(Update update, String file) {
        // For default: skip checked.
        return true;
    }
}
