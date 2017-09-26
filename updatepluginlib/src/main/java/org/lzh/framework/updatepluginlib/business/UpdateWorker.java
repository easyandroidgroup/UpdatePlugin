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
package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.callback.DefaultCheckCB;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.ForcedUpdateStrategy;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.Utils;

/**
 * <b>核心操作类</b>
 *
 * 此为检查更新api网络任务封装基类。用于对更新api进行请求返回数据后。触发
 *
 * @author haoge
 */
public abstract class UpdateWorker extends UnifiedWorker implements Runnable,Recyclable {

    /**
     * {@link DefaultCheckCB}的实例，用于接收网络任务状态。并连接后续流程
     */
    private DefaultCheckCB checkCB;

    private UpdateBuilder builder;

    public void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
    }

    public void setCheckCB (DefaultCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    @Override
    public void run() {
        try {
            String response = check(builder.getCheckEntity());
            UpdateParser jsonParser = builder.getJsonParser();
            Update parse = preHandle(jsonParser.parse(response));
            if (parse == null) {
                throw new IllegalArgumentException("parse response to update failed by " + jsonParser.getClass().getCanonicalName());
            }
            if (builder.getUpdateChecker().check(parse)) {
                sendHasUpdate(parse);
            } else {
                sendNoUpdate();
            }
        } catch (Throwable e) {
            sendOnErrorMsg(e);
        } finally {
            setRunning(false);
        }
    }

    /**
     * 访问此更新api实体类，并将其api接口数据返回。
     * @param entity 更新api数据实体类
     * @return 通过此更新api接口返回的数据
     * @throws Exception 当访问失败。请抛出一个异常。底层即可捕获此异常用于通知用户。
     */
    protected abstract String check(CheckEntity entity) throws Exception;

    private void sendHasUpdate(final Update update) {
        if (checkCB == null) return;
        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (checkCB == null) return;
                checkCB.hasUpdate(update);
                release();
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
                release();
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
                release();
            }
        });
    }

    @Override
    public void release() {
        this.checkCB = null;
    }

    private Update preHandle(Update update) {
        if (update == null) {
            return null;
        }

        // 当需要进行强制更新时。覆盖替换更新策略，且关闭忽略功能
        if (update.isForced()) {
            update.setIgnore(false);
            builder.strategy(new ForcedUpdateStrategy());
        }
        return update;
    }
}
