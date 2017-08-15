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
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;

/**
 * The default download callback to receive update event send by {@link org.lzh.framework.updatepluginlib.business.DownloadWorker}
 * @author lzh
 */
public final class DefaultDownloadCB implements UpdateDownloadCB ,Recyclable {

    private UpdateBuilder builder;
    /**
     * set by {@link UpdateBuilder#downloadCB(UpdateDownloadCB)} or
     * {@link org.lzh.framework.updatepluginlib.UpdateConfig#downloadCB(UpdateDownloadCB)}<br>
     */
    private UpdateDownloadCB downloadCB;
    private Update update;
    /**
     * This callback is created by {@link org.lzh.framework.updatepluginlib.creator.DownloadCreator#create(Update, Activity)}<br>
     *     to update UI within this callback
     */
    private UpdateDownloadCB innerCB;

    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
        downloadCB = builder.getDownloadCB();
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    /**
     * Receive and pass download_start event send by {@link DownloadWorker#sendUpdateStart()}
     */
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

    /**
     * Receive and pass download_complete event send by {@link DownloadWorker#sendUpdateComplete(File)}
     *
     * When download complete,The install dialog will be create when {@link UpdateStrategy#isAutoInstall()} is return with false
     */
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

    public void showInstallDialogIfNeed(File file) {
        Activity current = ActivityManager.get().topActivity();

        InstallCreator creator = builder.getInstallDialogCreator();
        creator.setCheckCB(builder.getCheckCB());
        creator.setFileChecker(builder.getFileChecker());
        creator.setUpdate(update);
        if (builder.getStrategy().isAutoInstall()) {
            creator.sendToInstall(file.getAbsolutePath());
        } else {
            Dialog dialog = creator.create(update, file.getAbsolutePath(),current);
            SafeDialogOper.safeShowDialog(dialog);
        }
    }

    /**
     * Receive and pass download_progress event send by {@link DownloadWorker#sendUpdateProgress(long, long)}
     */
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

    /**
     * Receive and pass download_error event send by {@link DownloadWorker#sendUpdateError(Throwable)}
     */
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
