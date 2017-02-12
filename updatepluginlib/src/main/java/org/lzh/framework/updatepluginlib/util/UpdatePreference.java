package org.lzh.framework.updatepluginlib.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.lzh.framework.updatepluginlib.UpdateConfig;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by lzh on 2016/8/11.
 */
public class UpdatePreference {

    private static final String PREF_NAME = "update_preference";

    public static long getLastDownloadSize(String url) {
        SharedPreferences sp = getUpdatePref();
        return sp.getLong(url,0);
    }

    public static long getLastDownloadTotalSize (String url) {
        SharedPreferences sp = getUpdatePref();
        return sp.getLong(url + "_total_size",0);
    }

    public static void saveDownloadSize (String url,long size) {
        SharedPreferences.Editor editor = getUpdatePref().edit();
        editor.putLong(url,size);
        editor.commit();
    }

    public static void saveDownloadTotalSize(String url, long totalSize) {
        SharedPreferences.Editor editor = getUpdatePref().edit();
        editor.putLong(url + "_total_size",totalSize);
        editor.commit();
    }

    public static Set<String> getIgnoreVersions () {
        Set<String> ignoreVersions = getUpdatePref().getStringSet("ignoreVersions", new HashSet<String>());
        return ignoreVersions;
    }

    public static void saveIgnoreVersion(int versionCode) {
        Set<String> ignoreVersions = getIgnoreVersions();
        if (!ignoreVersions.contains(String.valueOf(versionCode))) {
            ignoreVersions.add(String.valueOf(versionCode));
            getUpdatePref().edit().putStringSet("ignoreVersions",ignoreVersions).commit();
        }
    }

    private static SharedPreferences getUpdatePref () {
        return UpdateConfig.getConfig().getContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }
}
