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
import org.lzh.framework.updatepluginlib.impl.DefaultUpdateChecker;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * 此类用于对通过{@link UpdateParser}所解析返回的更新数据进行检查。判断是否此新版本数据需要被更新
 *
 * <p>配置方式：通过{@link UpdateConfig#setUpdateChecker(UpdateChecker)}或者{@link UpdateBuilder#setUpdateChecker(UpdateChecker)}
 *
 * <p>默认实现：{@link DefaultUpdateChecker}
 *
 * @author haoge
 */
public interface UpdateChecker {

    /**
     * 对提供的更新实体类进行检查。判断是否需要进行更新。
     * @param update 更新数据实体类
     * @return True代表检查通过。此版本需要被更新
     * @throws Exception error occurs.
     */
    boolean check(Update update) throws Exception;
}
