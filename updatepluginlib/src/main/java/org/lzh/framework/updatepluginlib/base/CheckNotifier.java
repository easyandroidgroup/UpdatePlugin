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
package org.lzh.framework.updatepluginlib.base;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.flow.Launcher;
import org.lzh.framework.updatepluginlib.impl.DefaultUpdateNotifier;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.UpdatePreference;

/**
 * <p>此为当检查到更新时的通知创建器基类。
 *
 * <p>配置方式：通过{@link UpdateConfig#setCheckNotifier(CheckNotifier)}或者{@link UpdateBuilder#setCheckNotifier(CheckNotifier)}
 *
 * <p>默认实现：{@link DefaultUpdateNotifier}
 *
 * <p>触发逻辑：当检查到有新版本更新时且配置的更新策略{@link UpdateStrategy#isShowUpdateDialog(Update)}设定为true时。此通知创建器将被触发
 *
 * <p>定制说明：<br>
 *     1. 当需要进行后续更新操作时(请求进行apk下载任务)：调用{@link #sendDownloadRequest()}<br>
 *     2. 当需要取消此次更新操作时：调用{@link #sendUserCancel()}<br>
 *     3. 当需要忽略此版本更新时：调用{@link #sendUserIgnore()}<br>
 *
 * @author haoge
 */
public abstract class CheckNotifier {

    protected UpdateBuilder builder;
    protected Update update;
    private CheckCallback callback;
    public final void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
        this.callback = builder.getCheckCallback();
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    /**
     * 创建一个Dialog用于通知用户当前有新版本需要更新。
     *
     * <p>若需要展示的通知为非弹窗通知。如在通知栏进行通知。则在此创建通知栏通知，返回为null即可。
     *
     * <p>定制说明：<br>
     *     1. 当需要进行后续更新操作时(请求进行apk下载任务)：调用{@link #sendDownloadRequest()}<br>
     *     2. 当需要取消此次更新操作时：调用{@link #sendUserCancel()}<br>
     *     3. 当需要忽略此版本更新时：调用{@link #sendUserIgnore()}<br>
     *
     * @param context 顶部的Activity实例。通过{@link ActivityManager#topActivity()}进行获取
     * @return 创建的Dialog实例。若当需要展示的不是弹窗时：返回null
     */
    public abstract Dialog create(Activity context);

    /**
     * 当需要进行后续更新操作时：需要更新，启动下载任务时，调用此方法进行流程连接
     */
    public final void sendDownloadRequest() {
        Launcher.getInstance().launchDownload(update,builder);
    }

    /**
     * 当用户手动取消此次更新任务时，通过此方法进行取消并通知用户
     */
    protected final void sendUserCancel() {
        if (this.callback != null) {
            this.callback.onUserCancel();
        }
    }

    /**
     * 当用户指定需要忽略此版本的更新请求时：通过此方法进行取消并忽略此版本的后续更新请求。
     */
    protected final void sendUserIgnore() {
        if (this.callback != null) {
            this.callback.onCheckIgnore(update);
        }
        UpdatePreference.saveIgnoreVersion(update.getVersionCode());
    }

}