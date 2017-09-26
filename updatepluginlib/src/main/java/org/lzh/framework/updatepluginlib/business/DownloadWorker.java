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
package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.DefaultDownloadCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.Utils;

import java.io.File;

/**
 * <b>核心操作类</b>
 *
 * 此为下载任务的封装基类。主要用于对下载中的进度、状态进行派发。以起到连接更新流程作用
 *
 * @author lzh
 */
public abstract class DownloadWorker extends UnifiedWorker implements Runnable,Recyclable {

    /**
     * 通过{@link Update#setUpdateUrl(String)}所设置的远程apk下载地址
     */
    protected String url;
    /**
     * {@link DefaultDownloadCB}的实例。用于接收下载状态并进行后续流程通知
     */
    private UpdateDownloadCB downloadCB;
    /**
     * 下载的缓存文件全路径。此路径名通过{@link ApkFileCreator#create(String)}进行获取
     */
    private File cacheFileName;
    // 将update实例提供给子类使用。
    protected Update update;

    public void setUpdate(Update update) {
        this.update = update;
        this.url = update.getUpdateUrl();
    }

    public void setCacheFileName(File cacheFileName) {
        this.cacheFileName = cacheFileName;
    }

    public void setDownloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
    }

    @Override
    public void run() {
        try {
            cacheFileName.getParentFile().mkdirs();
            sendUpdateStart();
            download(url,cacheFileName);
            sendUpdateComplete(cacheFileName);
        } catch (Throwable e) {
            sendUpdateError(e);
        } finally {
            setRunning(false);
        }
    }

    protected abstract void download(String url, File target) throws Exception;

    private void sendUpdateStart() {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onDownloadStart();
            }
        });
    }

    protected void sendUpdateProgress(final long current, final long total) {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onDownloadProgress(current, total);
            }
        });
    }

    private void sendUpdateComplete(final File file) {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onDownloadComplete(file);
                release();
            }
        });
    }

    private void sendUpdateError (final Throwable t) {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onDownloadError(t);
                release();
            }
        });
    }

    @Override
    public void release() {
        this.downloadCB = null;
        this.update = null;
    }
}
