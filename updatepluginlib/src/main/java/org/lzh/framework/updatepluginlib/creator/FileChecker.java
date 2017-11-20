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
     * 在此进行文件检查。
     *
     * <p>调用时机：
     * <ol>
     *     <li>
     *         当检查到有新版本需要更新时。且在启动下载任务前。进行检查。当检查成功时，则将跳过文件下载任务。避免重复下载，
     *         检查失败。则启动下载任务。
     *     </li>
     *     <li>
     *         当下载完成后。启动安装包程序之前。进行检查。检查成功后即可调起安装任务。检查失败将失败信息通知给用户
     *     </li>
     * </ol>
     *
     * @param update 更新数据实体类
     * @param file 被{@link ApkFileCreator}所创建的缓存文件地址
     * @throws Exception 若检查失败。抛出异常。外部将捕获后提供给用户具体的异常信心。便于定位问题。
     */
    void check(Update update, String file) throws Exception;
}
