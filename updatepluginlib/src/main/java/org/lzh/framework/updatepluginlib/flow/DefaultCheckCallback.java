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
package org.lzh.framework.updatepluginlib.flow;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.base.CheckCallback;
import org.lzh.framework.updatepluginlib.base.CheckNotifier;
import org.lzh.framework.updatepluginlib.base.CheckWorker;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.SafeDialogHandle;

/**
 * <p><b>核心操作类</b>
 *
 * <p>此类为默认的检查更新api网络任务的通知回调。用于接收从{@link CheckWorker}中所传递过来的各种状态。并进行后续流程触发</p>
 *
 * @author haoge
 */
public final class DefaultCheckCallback implements CheckCallback {
    private UpdateBuilder builder;
    private CheckCallback callback;

    public void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
        this.callback = builder.getCheckCallback();
    }

    @Override
    public void onCheckStart() {
        try {
            if (callback != null) {
                callback.onCheckStart();
            }
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

    @Override
    public void hasUpdate(Update update) {
        try {
            if (callback != null) {
                callback.hasUpdate(update);
            }

            CheckNotifier notifier = builder.getCheckNotifier();
            notifier.setBuilder(builder);
            notifier.setUpdate(update);

            if (!builder.getUpdateStrategy().isShowUpdateDialog(update)) {
                notifier.sendDownloadRequest();
                return;
            }

            Activity current = ActivityManager.get().topActivity();
            Dialog dialog = notifier.create(current);
            SafeDialogHandle.safeShowDialog(dialog);
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

    @Override
    public void noUpdate() {
        try {
            if (callback != null) {
                callback.noUpdate();
            }
        } catch (Throwable t) {
            onCheckError(t);
        }

    }

    @Override
    public void onCheckError(Throwable t) {
        try {
            if (callback != null) {
                callback.onCheckError(t);
            }
        } catch (Throwable ignore) {
            ignore.printStackTrace();
        }
    }

    @Override
    public void onUserCancel() {
        try {
            if (callback != null) {
                callback.onUserCancel();
            }
        } catch (Throwable t) {
            onCheckError(t);
        }

    }

    @Override
    public void onCheckIgnore(Update update) {
        try {
            if (callback != null) {
                callback.onCheckIgnore(update);
            }
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

}
