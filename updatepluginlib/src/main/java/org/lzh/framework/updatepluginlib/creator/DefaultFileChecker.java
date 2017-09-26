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
package org.lzh.framework.updatepluginlib.creator;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;

/**
 * 默认的apk文件检查器。
 * @author haoge
 */
public class DefaultFileChecker implements FileChecker {

    @Override
    public boolean checkPreFile(Update update, String file) {
        try {
            Context context = ActivityManager.get().getApplicationContext();
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
