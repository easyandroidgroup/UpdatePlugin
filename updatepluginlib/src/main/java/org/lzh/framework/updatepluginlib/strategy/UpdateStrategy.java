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
package org.lzh.framework.updatepluginlib.strategy;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.creator.DownloadCreator;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * 此类用于提供定制更新时的各处通知显示逻辑。
 *
 * <p>配置方式：通过{@link UpdateConfig#setUpdateStrategy(UpdateStrategy)}或者{@link UpdateBuilder#setUpdateStrategy(UpdateStrategy)}
 *
 * <p>默认实现: {@link WifiFirstStrategy}
 *
 * <p>请注意：在整套更新流程中。共有以下三处可进行用户界面通知的点：
 *
 * <ol>
 *     <li>当通过更新api检查到有新版本需要被更新，且当{@link #isShowUpdateDialog(Update)}返回true时：触发使用创建器{@link DialogCreator}创建界面通知</li>
 *     <li>当启动apk下载任务时，且当{@link #isShowDownloadDialog()}返回true时：触发使用创建器{@link DownloadCreator}创建通知</li>
 *     <li>当apk下载任务下载完成后。在进行启动安装任务前。且当{@link #isAutoInstall()}返回false时：触发使用创建器{@link InstallCreator}创建通知</li>
 * </ol>
 *
 * @author haoge
 */
public interface UpdateStrategy {

    /**
     * 是否显示有新版本更新的通知
     * @param update 更新数据实体类
     * @return True代表需要显示
     * @see DialogCreator
     */
    boolean isShowUpdateDialog(Update update);

    /**
     * 是否在下载完成后。跳过下载完成的通知。直接自动启动安装任务
     *
     * @return True代表将跳过展示apk下载完成的通知。
     * @see InstallCreator
     */
    boolean isAutoInstall();

    /**
     * 是否在下载时。启动下载进度条通知。
     * @return True代表需要显示下载进度通知
     * @see DownloadCreator
     */
    boolean isShowDownloadDialog();
}
