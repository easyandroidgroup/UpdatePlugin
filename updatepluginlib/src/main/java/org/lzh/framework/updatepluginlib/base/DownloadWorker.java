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

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.flow.DefaultDownloadCallback;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>核心操作类</b>
 *
 * 此为下载任务的封装基类。主要用于对下载中的进度、状态进行派发。以起到连接更新流程作用
 *
 * @author lzh
 */
public abstract class DownloadWorker implements Runnable {

    /**
     * {@link DefaultDownloadCallback}的实例。用于接收下载状态并进行后续流程通知
     */
    private DefaultDownloadCallback callback;

    // 维护一个所有下载任务的下载文件集合。用于避免出现多个下载任务同时下载同一个文件。避免冲突。
    private static Map<DownloadWorker, File> downloading = new HashMap<>();

    protected Update update;
    protected UpdateBuilder builder;

    public final void setUpdate(Update update) {
        this.update = update;
    }

    public final void setUpdateBuilder(UpdateBuilder builder) {
        this.builder = builder;
    }

    public final void setCallback(DefaultDownloadCallback callback) {
        this.callback = callback;
    }

    @Override
    public final void run() {
        try {
            File file = builder.getFileCreator().createWithBuilder(update, builder);
            FileChecker checker = builder.getFileChecker();
            checker.attach(update, file);
            if (builder.getFileChecker().checkBeforeDownload()) {
                // check success: skip download and show install dialog if needed.
                callback.postForInstall(file);
                return;
            }
            checkDuplicateDownload(file);

            sendDownloadStart();
            String url = update.getUpdateUrl();
            file.getParentFile().mkdirs();
            download(url,file);
        } catch (Throwable e) {
            sendDownloadError(e);
        }
    }

    private void checkDuplicateDownload(File file) {
        if (downloading.containsValue(file)) {
            throw new RuntimeException(String.format(
                    "You can not download the same file using multiple download tasks simultaneously，the file path is %s",
                    file.getAbsolutePath()
            ));
        }
        downloading.put(this, file);
    }

    /**
     * 此为更新插件apk下载任务触发入口。
     *
     * <p>定制自己的网络层下载任务。需要复写此方法。并执行网络下载任务。
     *
     * <p>定制网络下载任务需要自己进行状态通知：
     * <ol>
     *     <li>当使用的网络框架可以支持进度条通知时，调用{@link #sendDownloadProgress(long, long)}触发进度条消息通知</li>
     *     <li>当下载出现异常时。若使用的是同步请求。则无需理会，若使用的是异步请求。则需手动调用{@link #sendDownloadError(Throwable)}</li>
     *     <li>当下载任务执行完毕时：需手动调用{@link #sendDownloadComplete(File)}通知用户并启动下一步任务</li>
     * </ol>
     *
     * @param url apk文件下载地址
     * @param target 指定的下载文件地址
     * @throws Exception 捕获出现的异常
     */
    protected abstract void download(String url, File target) throws Exception;

    protected final void sendDownloadStart() {
        if (callback == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (callback == null) return;
                callback.onDownloadStart();
            }
        });
    }

    /**
     * 通知当前下载进度，若实现类不调用此方法。将不会触发更新进度条的消息
     * @param current 当前下载长度
     * @param total 下载文件总长度
     */
    protected final void sendDownloadProgress(final long current, final long total) {
        if (callback == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (callback == null) return;
                callback.onDownloadProgress(current, total);
            }
        });
    }

    /**
     * 通知当前下载任务执行完毕！当下载完成后。此回调必须被调用
     * @param file 被下载的文件
     */
    protected final void sendDownloadComplete(final File file) {
        try {
            builder.getFileChecker().onCheckBeforeInstall();
        } catch (Exception e) {
            sendDownloadError(e);
            return;
        }

        if (callback == null) return;
        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (callback == null) return;
                callback.onDownloadComplete(file);
                callback.postForInstall(file);
                downloading.remove(DownloadWorker.this);
            }
        });
    }

    /**
     * 通知当前下载任务出错。当下载出错时，此回调必须被调用
     * @param t 错误异常信息
     */
    protected final void sendDownloadError(final Throwable t) {
        if (callback == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (callback == null) return;
                callback.onDownloadError(t);
                downloading.remove(DownloadWorker.this);
            }
        });
    }

}
