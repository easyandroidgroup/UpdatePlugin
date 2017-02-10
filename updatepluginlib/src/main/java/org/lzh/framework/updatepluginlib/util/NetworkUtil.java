package org.lzh.framework.updatepluginlib.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.lzh.framework.updatepluginlib.UpdateConfig;

/**
 * @author Administrator
 */
public class NetworkUtil {

    /**
     * Determine whether to use wifi
     * @return true if is connected by wifi
     */
    public static boolean isConnectedByWifi() {
        NetworkInfo info = getNetworkInfos();
        return info != null
                && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static NetworkInfo getNetworkInfos() {
        Context context = UpdateConfig.getConfig().getContext();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getActiveNetworkInfo();
    }
}
