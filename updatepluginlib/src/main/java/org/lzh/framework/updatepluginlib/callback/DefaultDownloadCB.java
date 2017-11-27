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
package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;

/**
 * 默认的下载任务的回调监听。主要用于接收从{@link DownloadWorker}中传递过来的下载状态。通知用户并触发后续流程
 *
 * @author haoge
 */
public final class DefaultDownloadCB implements UpdateDownloadCB ,Recyclable {

    private UpdateBuilder builder;
    // 通过UpdateConfig或者UpdateBuilder所设置的下载回调监听。通过此监听器进行通知用户下载状态
    private UpdateDownloadCB downloadCB;
    private Update update;
    // 通过DownloadCreator所创建的回调监听，通过此监听器进行下载通知的UI更新
    private UpdateDownloadCB innerCB;

    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
        downloadCB = builder.getDownloadCB();
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    @Override
    public void onDownloadStart() {
        try {
            if (downloadCB != null) {
                downloadCB.onDownloadStart();
            }
            innerCB = getInnerCB();
            if (innerCB != null) {
                innerCB.onDownloadStart();
            }
        } catch (Throwable t) {
            onDownloadError(t);
        }
    }

    private UpdateDownloadCB getInnerCB() {
        if (innerCB != null || !builder.getStrategy().isShowDownloadDialog()) {
            return innerCB;
        }

        Activity current = ActivityManager.get().topActivity();
        innerCB = builder.getDownloadDialogCreator().create(update,current);
        return innerCB;
    }

    @Override
    public void onDownloadComplete(File file) {
        try {
            if (downloadCB != null) {
                downloadCB.onDownloadComplete(file);
            }

            if (innerCB != null) {
                innerCB.onDownloadComplete(file);
            }

            showInstallDialogIfNeed(file);

            release();
        } catch (Throwable t) {
            onDownloadError(t);
        }
    }

    public void showInstallDialogIfNeed(final File file) {
        final Activity current = ActivityManager.get().topActivity();
        current.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InstallCreator creator = builder.getInstallDialogCreator();
                creator.setBuilder(builder);
                creator.setUpdate(update);
                if (builder.getStrategy().isAutoInstall()) {
                    creator.sendToInstall(file.getAbsolutePath());
                } else {
                    Dialog dialog = creator.create(update, file.getAbsolutePath(),current);
                    SafeDialogOper.safeShowDialog(dialog);
                }
            }
        });
    }

    @Override
    public void onDownloadProgress(long current, long total) {
        try {
            if (downloadCB != null) {
                downloadCB.onDownloadProgress(current,total);
            }

            if (innerCB != null) {
                innerCB.onDownloadProgress(current,total);
            }
        } catch (Throwable t) {
            onDownloadError(t);
        }

    }

    @Override
    public void onDownloadError(Throwable t) {
        try {
            if (downloadCB != null) {
                downloadCB.onDownloadError(t);
            }
            if (innerCB != null) {
                innerCB.onDownloadError(t);
            }
        } catch (Throwable ignore) {
            ignore.printStackTrace();
        } finally {
            release();
        }
    }

    @Override
    public void release() {
        this.builder = null;
        this.innerCB = null;
        this.downloadCB = null;
        this.update = null;
    }
}
