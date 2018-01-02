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
import org.lzh.framework.updatepluginlib.flow.DefaultCheckCallback;
import org.lzh.framework.updatepluginlib.impl.ForcedUpdateStrategy;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.Utils;

/**
 * <b>核心操作类</b>
 *
 * 此为检查更新api网络任务封装基类。用于对更新api进行请求返回数据后。触发
 *
 * @author haoge
 */
public abstract class CheckWorker implements Runnable {

    /**
     * {@link DefaultCheckCallback}的实例，用于接收网络任务状态。并连接后续流程
     */
    private DefaultCheckCallback checkCB;

    protected UpdateBuilder builder;

    public final void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
    }

    public final void setCheckCB (DefaultCheckCallback checkCB) {
        this.checkCB = checkCB;
    }

    @Override
    public final void run() {
        try {
            if (useAsync()) {
                asyncCheck(builder.getCheckEntity());
            } else {
                onResponse(check(builder.getCheckEntity()));
            }
        } catch (Throwable t) {
            onError(t);
        }
    }

    /**
     *
     * 同步请求更新api。并将其接口数据直接返回。
     *
     * 此处运行于子线程。可直接使用网络库的同步操作
     *
     * @param entity 更新api数据实体类
     * @return 通过此更新api接口返回的数据
     * @throws Exception 当访问失败。请抛出一个异常。底层将捕获此异常用于通知用户。
     */
    protected String check(CheckEntity entity) throws Exception {
        throw new RuntimeException("You must implements this method for sync request");
    }

    /**
     * 异步请求更新api。当{@link #useAsync()}返回true时被触发
     *
     * <p>当请求失败：需要手动调用{@link #onError(Throwable)}并传入失败异常
     *
     * <p>当请求成功：需要手动调用{@link #onResponse(String)}并传入接口返回原始数据。便于后续解析
     *
     * @param entity 更新api数据实体类
     */
    protected void asyncCheck(CheckEntity entity) {
        throw new RuntimeException("You must implements this method for async request");
    }

    /**
     * 用于指定此网络任务使用异步还是同步方式进行请求：
     *
     * <p>True:使用异步操作。则将触发{@link #asyncCheck(CheckEntity)}进行更新请求
     *
     * <p>False:使用同步操作，则将触发{@link #check(CheckEntity)}进行更新请求
     *
     * <p>默认为False.使用同步网络请求操作
     *
     * @return True:使用异步网络操作
     */
    protected boolean useAsync() {
        return false;
    }

    /**
     * 获取到更新接口api数据时的回调。在此进行后续的解析、检查是否需要更新等操作
     *
     * @param response 更新接口api返回的数据实体
     */
    public final void onResponse(String response) {
        try {
            UpdateParser jsonParser = builder.getUpdateParser();
            Update update = jsonParser.parse(response);
            if (update == null) {
                throw new IllegalArgumentException(String.format(
                        "Could not returns null by %s.parse()", jsonParser.getClass().getCanonicalName()
                ));
            }
            update = preHandle(update);
            if (builder.getUpdateChecker().check(update)) {
                sendHasUpdate(update);
            } else {
                sendNoUpdate();
            }
        } catch (Throwable t) {
            onError(t);
        }
    }

    /**
     * 进行检查更新api失败时的回调。
     * @param t 失败时的异常
     */
    public final void onError(Throwable t) {
        sendOnErrorMsg(t);
    }

    private void sendHasUpdate(final Update update) {
        if (checkCB == null) return;
        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (checkCB == null) return;
                checkCB.hasUpdate(update);
            }
        });
    }

    private void sendNoUpdate() {
        if (checkCB == null) return;
        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (checkCB == null) return;
                checkCB.noUpdate();
            }
        });
    }

    private void sendOnErrorMsg(final Throwable t) {
        if (checkCB == null) return;
        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (checkCB == null) return;
                checkCB.onCheckError(t);
            }
        });
    }

    private Update preHandle(Update update) {
        // 当需要进行强制更新时。覆盖替换更新策略，且关闭忽略功能
        if (update.isForced()) {
            update.setIgnore(false);
            builder.setUpdateStrategy(new ForcedUpdateStrategy());
        }
        return update;
    }
}
