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
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

/**
 * <p><b>核心操作类</b>
 *
 * <p>此类为默认的检查更新api网络任务的通知回调。用于接收从{@link CheckWorker}中所传递过来的各种状态。并进行后续流程触发</p>
 *
 * @author haoge
 */
public final class DefaultCheckCallback implements CheckCallback {
    private UpdateBuilder builder;
    private CheckCallback checkCB;

    public void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
        this.checkCB = builder.getCheckCB();
    }

    @Override
    public void onCheckStart() {
        try {
            if (checkCB != null) {
                checkCB.onCheckStart();
            }
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

    @Override
    public void hasUpdate(Update update) {
        try {
            if (checkCB != null) {
                checkCB.hasUpdate(update);
            }

            CheckNotifier creator = builder.getUpdateDialogCreator();
            creator.setBuilder(builder);
            creator.setUpdate(update);

            if (!builder.getUpdateStrategy().isShowUpdateDialog(update)) {
                creator.sendDownloadRequest();
                return;
            }

            Activity current = ActivityManager.get().topActivity();
            Dialog dialog = creator.create(current);
            SafeDialogOper.safeShowDialog(dialog);
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

    @Override
    public void noUpdate() {
        try {
            if (checkCB != null) {
                checkCB.noUpdate();
            }
        } catch (Throwable t) {
            onCheckError(t);
        }

    }

    @Override
    public void onCheckError(Throwable t) {
        try {
            if (checkCB != null) {
                checkCB.onCheckError(t);
            }
        } catch (Throwable ignore) {
            ignore.printStackTrace();
        }
    }

    @Override
    public void onUserCancel() {
        try {
            if (checkCB != null) {
                checkCB.onUserCancel();
            }
        } catch (Throwable t) {
            onCheckError(t);
        }

    }

    @Override
    public void onCheckIgnore(Update update) {
        try {
            if (checkCB != null) {
                checkCB.onCheckIgnore(update);
            }
        } catch (Throwable t) {
            onCheckError(t);
        }
    }

}
