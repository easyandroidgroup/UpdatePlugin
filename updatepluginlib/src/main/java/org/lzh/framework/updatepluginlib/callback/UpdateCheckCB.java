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
package org.lzh.framework.updatepluginlib.callback;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * 检查更新的回调监听。
 *
 * <p>设置方式：通过{@link UpdateConfig#setCheckCallback(UpdateCheckCB)}或者{@link UpdateBuilder#setCheckCallback(UpdateCheckCB)}进行设置。
 *
 * @author haoge
 */
public interface UpdateCheckCB {

    /**
     * 当使用{@link UpdateBuilder#check()}进行启动更新任务时。通知到此回调中。
     *
     * <p>回调线程：与更新任务启动线程一致
     */
    void onCheckStart ();

    /**
     * 当通过{@link UpdateWorker}进行更新api检查成功时。即检查到有新版本需要更新时，通知到此回调中：
     *
     * <p>回调线程：UI
     * @param update 更新数据实体类
     */
    void hasUpdate(Update update);

    /**
     * 当检查到无更新时。通知到此：
     *
     * <p>回调线程：UI
     */
    void noUpdate();

    /**
     * 当进行更新检查失败，内部发生异常时。通知到此
     *
     * <p>回调线程：UI
     * @param t 发生异常实例
     */
    void onCheckError(Throwable t);

    /**
     * 当用户主动取消时触发到此回调中。主动取消的触发入口在{@link DialogCreator#sendUserCancel()}和{@link InstallCreator#sendUserCancel()}
     *
     * <p>回调线程：UI
     */
    void onUserCancel();

    /**
     * 当用户点击忽略此版本更新时触发到此回调中。触发入口在{@link DialogCreator#sendUserIgnore(Update)}和{@link InstallCreator#sendCheckIgnore(Update)}
     *
     * <p>回调线程：UI
     * @param update Update entity
     */
    void onCheckIgnore(Update update);
}
