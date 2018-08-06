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
 *     当下载完成后。强制显示下载完成后的界面通知，其他的通知策略默认不变。
 *
 * @author haoge on 2017/9/25.
 */
public class ForcedUpdateStrategy extends UpdateStrategy {

    private UpdateStrategy delegate;

    public ForcedUpdateStrategy(UpdateStrategy delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean isShowUpdateDialog(Update update) {
        return delegate.isShowUpdateDialog(update);
    }

    @Override
    public boolean isAutoInstall() {
        return false;
    }

    @Override
    public boolean isShowDownloadDialog() {
        return delegate.isShowDownloadDialog();
    }
}
