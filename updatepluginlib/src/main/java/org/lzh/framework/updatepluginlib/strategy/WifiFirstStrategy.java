package org.lzh.framework.updatepluginlib.strategy;

import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.NetworkUtil;

/**
 * @author Administrator
 */
public class WifiFirstStrategy implements UpdateStrategy {

    boolean isWifi;

    @Override
    public boolean isShowUpdateDialog(Update update) {
        isWifi = NetworkUtil.isConnectedByWifi();
        if (isWifi) {
            return true;
        }
        return true;
    }

    @Override
    public boolean isAutoInstall() {
        return false;
    }

    @Override
    public boolean isShowInstallDialog() {
        if (isWifi) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isShowDownloadDialog() {
        return false;
    }
}
