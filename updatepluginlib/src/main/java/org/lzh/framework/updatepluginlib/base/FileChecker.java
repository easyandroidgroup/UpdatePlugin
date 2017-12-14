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
import org.lzh.framework.updatepluginlib.impl.DefaultFileChecker;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * 用于提供在更新中对apk进行有效性、安全性检查的接口
 *
 * <p>配置方式：通过{@link UpdateConfig#setFileChecker(FileChecker)}或者{@link UpdateBuilder#setFileChecker(FileChecker)}
 *
 * <p>默认实现：{@link DefaultFileChecker}
 *
 * @author haoge
 */
public abstract class FileChecker {

    protected Update update;
    protected File file;

    final void attach(Update update, File file) {
        this.update = update;
        this.file = file;
    }

    final boolean checkBeforeDownload(){
        if (file == null || !file.exists()) {
            return false;
        }

        try {
            return onCheckBeforeDownload();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在启动下载任务前。对{@link FileCreator}创建的缓存文件进行验证。判断是否此文件已被成功下载完成。
     *
     * <p>当验证成功时，则代表此文件在之前已经被下载好了。则将跳过下载任务。
     *
     * <p>若下载失败
     */
    protected abstract boolean onCheckBeforeDownload() throws Exception;

    /**
     * 当下载完成后。触发到此。进行文件安全校验检查。当检查成功。即可启动安装任务。安装更新apk
     *
     * <p>若检查失败。则可主动抛出一个异常。用于提供给框架捕获并通知给用户。
     */
    protected abstract void onCheckBeforeInstall() throws Exception;
}
