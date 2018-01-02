/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lzh.framework.updatepluginlib.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * 框架内部所提供使用的一些缓存数据存取：如下载进度、忽略版本。
 * @author haoge
 */
public class UpdatePreference {

    private static final String PREF_NAME = "update_preference";

    public static Set<String> getIgnoreVersions () {
        return getUpdatePref().getStringSet("ignoreVersions", new HashSet<String>());
    }

    public static void saveIgnoreVersion(int versionCode) {
        Set<String> ignoreVersions = getIgnoreVersions();
        if (!ignoreVersions.contains(String.valueOf(versionCode))) {
            ignoreVersions.add(String.valueOf(versionCode));
            getUpdatePref().edit().putStringSet("ignoreVersions",ignoreVersions).apply();
        }
    }

    private static SharedPreferences getUpdatePref () {
        return ActivityManager.get().getApplicationContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }
}
