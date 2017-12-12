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
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.impl.WifiFirstStrategy;

/**
 * 此接口用于定制更新时各节点通知的显示逻辑。
 *
 * <p>配置方式：通过{@link UpdateConfig#setUpdateStrategy(UpdateStrategy)}或者{@link UpdateBuilder#setUpdateStrategy(UpdateStrategy)}
 *
 * <p>默认实现: {@link WifiFirstStrategy}
 *
 * <p>请注意：在整套更新流程中。共有以下三处可进行用户界面通知的节点：
 *
 * <ol>
 *     <li>当通过更新api检查到有新版本需要被更新，且当{@link #isShowUpdateDialog(Update)}返回true时：触发使用创建器{@link CheckNotifier}创建界面通知</li>
 *     <li>当启动apk下载任务时，且当{@link #isShowDownloadDialog()}返回true时：触发使用创建器{@link DownloadNotifier}创建通知</li>
 *     <li>当apk下载任务下载完成后。在进行启动安装任务前。且当{@link #isAutoInstall()}返回false时：触发使用创建器{@link InstallNotifier}创建通知</li>
 * </ol>
 *
 * @author haoge
 */
public interface UpdateStrategy {

    /**
     * 当通过{@link UpdateChecker}检测到需要更新时。是否显示界面更新通知
     *
     * @param update 更新数据实体类
     * @return True代表需要显示更新。False代表不进行界面更新通知。直接调起后续流程(启动apk文件下载任务)
     * @see CheckNotifier
     */
    boolean isShowUpdateDialog(Update update);

    /**
     * 在使用{@link DownloadWorker}执行文件下载任务时。是否显示界面进度条通知
     *
     * @return True代表需要显示下载进度通知，
     * @see DownloadNotifier
     */
    boolean isShowDownloadDialog();

    /**
     * 是否在下载完成后。跳过下载完成的界面通知。直接自动启动安装任务
     *
     * @return True代表将跳过展示apk下载完成的通知。自动进行安装。
     * @see InstallNotifier
     */
    boolean isAutoInstall();
}
