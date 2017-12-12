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

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.base.DownloadWorker;
import org.lzh.framework.updatepluginlib.base.CheckWorker;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * 此类用于调起更新流程中的两处网络任务进行执行。
 *
 * <p>1. 检查更新api网络任务:{@link #launchCheck(UpdateBuilder)}
 *
 * <p>2. 发起apk文件下载任务:{@link #launchDownload(Update, UpdateBuilder)}
 *
 * @author haoge
 */
public final class Launcher {
    private static Launcher launcher;

    public static Launcher getInstance() {
        if (launcher == null) {
            launcher = new Launcher();
        }
        return launcher;
    }

    /**
     * 调起检查api更新任务。
     *
     * @param builder 更新任务实例
     */
    public void launchCheck(UpdateBuilder builder) {
        // 定义一个默认的检查更新回调监听。用于接收api检查更新任务所发出的通知。并链接后续流程。
        DefaultCheckCallback checkCB = new DefaultCheckCallback();
        checkCB.setBuilder(builder);
        checkCB.onCheckStart();

        CheckWorker checkWorker = builder.getCheckWorker();
        if (checkWorker.isRunning()) {
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
    public void launchDownload(Update update, UpdateBuilder builder) {
        // 定义一个默认的下载状态回调监听。用于接收文件下载任务所发出的通知。并链接下载后续流程
        DefaultDownloadCallback downloadCB = new DefaultDownloadCallback();
        downloadCB.setBuilder(builder);
        downloadCB.setUpdate(update);

        DownloadWorker downloadWorker = builder.getDownloadWorker();
        if (downloadWorker.isRunning()) {
            downloadCB.onDownloadError(new RuntimeException("Already have a download task running"));
            return;
        }

        downloadWorker.setUpdate(update);
        downloadWorker.setUpdateBuilder(builder);
        downloadWorker.setDownloadCB(downloadCB);

        builder.getExecutor().download(downloadWorker);
    }

}
