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
package org.lzh.framework.updatepluginlib.impl;

import org.lzh.framework.updatepluginlib.base.UpdateStrategy;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * 当为强制更新时，将会强制使用此更新策略。
 *
 * <p>此更新策略的表现为：<br>
 *     1. 当存在更新时：直接展示有新更新时的通知<br>
 *     2. 当点击进行更新时：直接展示下载进度通知<br>
 *     3. 当下载完成后。直接调起安装任务。并调起安装后。杀死进程
 *
 * @author haoge on 2017/9/25.
 */
public class ForcedUpdateStrategy implements UpdateStrategy {
    @Override
    public boolean isShowUpdateDialog(Update update) {
        return true;
    }

    @Override
    public boolean isAutoInstall() {
        return true;
    }

    @Override
    public boolean isShowDownloadDialog() {
        return true;
    }
}
