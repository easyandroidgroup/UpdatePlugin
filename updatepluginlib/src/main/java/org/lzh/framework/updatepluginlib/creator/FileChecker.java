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
package org.lzh.framework.updatepluginlib.creator;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * 用于提供在更新中对apk进行有效性、安全性检查的接口
 *
 * <p>配置方式：通过{@link UpdateConfig#fileChecker(FileChecker)}或者{@link UpdateBuilder#fileChecker(FileChecker)}
 *
 * <p>默认实现：{@link DefaultFileChecker}
 *
 * @author haoge
 */
public interface FileChecker {

    /**
     * 在启动下载任务前。通过对设置的文件下载缓存路径进行验证。
     *
     * <p>当验证成功时，则代表此文件在之前已经被下载好了。则将跳过下载任务。
     *
     * @param update 更新数据实体类
     * @param file 被{@link ApkFileCreator}所创建的缓存文件地址
     * @return True代表验证成功
     */
    boolean checkPreFile(Update update, String file);

    /**
     * 当下载完成后。触发到此。进行文件安全校验检查。当检查成功。即可启动安装任务。安装更新apk
     *
     * @param update 更新数据实体类
     * @param file 被{@link ApkFileCreator}所创建的缓存文件地址
     * @return True代表验证成功
     */
    boolean checkAfterDownload (Update update, String file);
}
