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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.lzh.framework.updatepluginlib.base.UpdateStrategy;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;

/**
 * 默认提供的更新策略：
 * 1. 当处于wifi环境时，只展示下载完成后的通知
 * 2. 当处于非wifi环境是：只展示有新版本更新及下载进度的通知。
 */
public class WifiFirstStrategy implements UpdateStrategy {

    private boolean isWifi;

    @Override
    public boolean isShowUpdateDialog(Update update) {
        isWifi = isConnectedByWifi();
        return !isWifi;
    }

    @Override
    public boolean isAutoInstall() {
        return !isWifi;
    }

    @Override
    public boolean isShowDownloadDialog() {
        return !isWifi;
    }

    private boolean isConnectedByWifi() {
        Context context = ActivityManager.get().getApplicationContext();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        return info != null
                && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

}
