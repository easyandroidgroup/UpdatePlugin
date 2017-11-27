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

import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;

import java.io.File;

/**
 * 默认的apk文件检查器。
 * @author haoge
 */
public class DefaultFileChecker implements FileChecker {

    private void check(Update update, String file) throws Exception {
        // TODO: 2017/11/27 添加MD5支持
        Context context = ActivityManager.get().getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(file, PackageManager.GET_ACTIVITIES);
        if (packageInfo.versionCode != update.getVersionCode()) {
            throw new IllegalStateException(
                    String.format("The version code not matched between apk and update entity. apk is %s but update is %s",
                            packageInfo.versionCode, update.getVersionCode())
            );
        }
    }

    @Override
    public boolean checkForDownload(Update update, String file) {
        try {
            check(update, file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void checkForInstall(Update update, String file) throws Exception {
        check(update, file);
    }


}
