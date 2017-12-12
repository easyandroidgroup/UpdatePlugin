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
import org.lzh.framework.updatepluginlib.UpdateConfig;

import java.io.File;

/**
 * 更新下载回调。
 *
 * <p>设置方式：{@link UpdateConfig#setDownloadCallback(DownloadCallback)} 或者 {@link UpdateBuilder#setDownloadCallback(DownloadCallback)}
 *
 * @author haoge
 */
public interface DownloadCallback {

    /**
     * 启动下载任务
     * <p>回调线程：UI
     */
    void onDownloadStart();

    /**
     * 下载完成
     *
     * <p>回调线程：UI
     * @param file 下载的文件
     */
    void onDownloadComplete(File file);

    /**
     * 下载进度通知
     *
     * <p>回调线程：UI
     * @param current 当前已下载长度
     * @param total 整个文件长度
     */
    void onDownloadProgress(long current, long total);

    /**
     * 当下载出现异常时通知到此回调
     * <p>回调线程：UI
     * @param t 下载时出现的异常
     */
    void onDownloadError(Throwable t);
}
