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
package org.lzh.framework.updatepluginlib.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.lzh.framework.updatepluginlib.base.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.UpdatePreference;

/**
 * 默认的更新数据检查器。
 *
 * <p>实现逻辑说明：<br>
 *     1. 当为强制更新时：直接判断当前的{@link Update#versionCode}是否大于当前应用的versionCode。若大于。则代表需要更新<br>
 *     2. 当为普通更新，判断是否当前更新的版本在忽略版本列表中。如果不是。再进行versionCode比对。
 *
 * @author haoge
 */
public class DefaultUpdateChecker implements UpdateChecker {
    @Override
    public boolean check(Update update) throws Exception{
        int curVersion = getApkVersion(ActivityManager.get().getApplicationContext());
        return update.getVersionCode() > curVersion &&
                (update.isForced() ||
                        !UpdatePreference.getIgnoreVersions().contains(String.valueOf(update.getVersionCode())));
    }

    public int getApkVersion (Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    }
}
