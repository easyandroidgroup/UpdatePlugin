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
package org.lzh.framework.updatepluginlib;

import android.util.Log;

import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.callback.DefaultCheckCB;
import org.lzh.framework.updatepluginlib.callback.DefaultDownloadCB;
import org.lzh.framework.updatepluginlib.creator.FileChecker;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * 此类用于调起更新流程中的两处网络任务进行执行。
 *
 * <p>1. 检查更新api网络任务:{@link #checkUpdate(UpdateBuilder)}
 *
 * <p>2. 发起apk文件下载任务:{@link #downUpdate(Update, UpdateBuilder)}
 *
 * @author haoge
 */
public final class Updater {
    private static Updater updater;

    public static Updater getInstance() {
        if (updater == null) {
            updater = new Updater();
        }
        return updater;
    }

    /**
     * 调起检查api更新任务。
     *
     * @param builder 更新任务实例
     */
    public void checkUpdate(UpdateBuilder builder) {
        // 定义一个默认的检查更新回调监听。用于接收api检查更新任务所发出的通知。并链接后续流程。
        DefaultCheckCB checkCB = new DefaultCheckCB();
        checkCB.setBuilder(builder);
        checkCB.onCheckStart();

        UpdateWorker checkWorker = builder.getCheckWorker();
        if (checkWorker.isRunning()) {
            Log.e("Updater","Already have a update task running");
            checkCB.onCheckError(new RuntimeException("Already have a update task running"));
            return;
        }
        checkWorker.setBuilder(builder);
        checkWorker.setCheckCB(checkCB);
        builder.getExecutor().check(checkWorker);
    }

    /**
     * 调起apk文件下载任务。
     *
     * @param update 更新api实体类。不能为null
     * @param builder 更新任务实例
     */
    public void downUpdate(Update update,UpdateBuilder builder) {
        // 定义一个默认的下载状态回调监听。用于接收文件下载任务所发出的通知。并链接下载后续流程
        DefaultDownloadCB downloadCB = new DefaultDownloadCB();
        downloadCB.setBuilder(builder);
        downloadCB.setUpdate(update);

        DownloadWorker downloadWorker = builder.getDownloadWorker();
        if (downloadWorker.isRunning()) {
            Log.e("Updater","Already have a download task running");
            downloadCB.onDownloadError(new RuntimeException("Already have a download task running"));
            return;
        }

        downloadWorker.setUpdate(update);
        downloadWorker.setUpdateBuilder(builder);
        downloadWorker.setDownloadCB(downloadCB);

        builder.getExecutor().download(downloadWorker);
    }

}
