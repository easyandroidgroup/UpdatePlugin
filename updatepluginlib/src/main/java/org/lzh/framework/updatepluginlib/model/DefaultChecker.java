package org.lzh.framework.updatepluginlib.model;

import android.content.pm.PackageManager;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.util.InstallUtil;

/**
 * Created by Administrator on 2016/6/20.
 */
public class DefaultChecker implements UpdateChecker {
    @Override
    public boolean check(Update update) {
        try {
            int curVersion = InstallUtil.getApkVersion(UpdateConfig.getConfig().getContext());
            if (update.getVersionCode() > curVersion) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
