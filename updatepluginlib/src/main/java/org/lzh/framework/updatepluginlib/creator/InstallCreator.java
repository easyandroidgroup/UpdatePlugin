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

import android.app.Activity;
import android.app.Dialog;
import android.os.Process;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.UpdatePreference;

/**
 * <p>此类为当检查到更新时的通知创建器基类。
 *
 * <p>配置方式：通过{@link UpdateConfig#installDialogCreator(InstallCreator)}或者{@link UpdateBuilder#installDialogCreator(InstallCreator)}
 *
 * <p>默认实现：{@link DefaultNeedInstallCreator}
 *
 * <p>触发逻辑：当检查到有新版本更新时且配置的更新策略{@link UpdateStrategy#isAutoInstall()}设定为false时。此通知创建器将被触发
 *
 * <p>定制说明：<br>
 *     1. 当需要进行后续更新操作时(请求调起安装任务)：调用{@link #sendToInstall(String)}}<br>
 *     2. 当需要取消此次更新操作时：调用{@link #sendUserCancel()}<br>
 *     3. 当需要忽略此版本更新时：调用{@link #sendCheckIgnore(Update)}<br>
 *
 * @author haoge
 */
public abstract class InstallCreator implements Recyclable {

    private UpdateBuilder builder;
    private Update update;

    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public abstract Dialog create(Update update, String path, Activity activity);

    public void sendToInstall(String filename) {
        if (builder.getFileChecker().checkAfterDownload(update,filename)) {
            builder.getInstallStrategy().install(ActivityManager.get().getApplicationContext(), filename);
            if (update.isForced()) {
                // 对于是强制更新的。当调起安装任务后。直接kill掉自身进程。
                // 单独使用一种有时候会kill失效。两种一起用。。。双管齐下。。。
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
        } else {
            builder.getCheckCB().onCheckError(new RuntimeException(String.format("apk %s checked failed",filename)));
        }
        release();
    }

    public void sendUserCancel() {
        if (builder.getCheckCB() != null) {
            builder.getCheckCB().onUserCancel();
        }

        release();
    }

    public void sendCheckIgnore(Update update) {
        if (builder.getCheckCB() != null) {
            builder.getCheckCB().onCheckIgnore(update);
        }
        UpdatePreference.saveIgnoreVersion(update.getVersionCode());
        release();
    }

    @Override
    public void release() {
        this.builder = null;
        this.update = null;
    }
}
