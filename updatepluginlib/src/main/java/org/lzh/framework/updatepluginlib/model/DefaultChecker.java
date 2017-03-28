package org.lzh.framework.updatepluginlib.model;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.util.UpdatePreference;
import org.lzh.framework.updatepluginlib.util.Utils;

public class DefaultChecker implements UpdateChecker {
    @Override
    public boolean check(Update update) {
        try {
            int curVersion = Utils.getApkVersion(UpdateConfig.getConfig().getContext());
            if (update.getVersionCode() > curVersion &&
                    (update.isForced()||
                    !UpdatePreference.getIgnoreVersions().contains(String.valueOf(update.getVersionCode())))) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
