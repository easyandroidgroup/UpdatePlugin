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
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.DefaultCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.Utils;

/**
 * The network task to check out whether or not a new version of apk is exist
 */
public abstract class UpdateWorker extends UnifiedWorker implements Runnable,Recyclable {

    protected CheckEntity entity;
    /**
     * The instance of {@link DefaultCheckCB}
     */
    private UpdateCheckCB checkCB;

    /**
     * set by {@link UpdateConfig#updateChecker(UpdateChecker)} or
     * {@link UpdateBuilder#updateChecker(UpdateChecker)}<br>
     *     <br>
     *     according to instance {@link Update} to check out whether or not should be updated
     */
    private UpdateChecker checker;
    /**
     * set by {@link UpdateConfig#jsonParser(UpdateParser)} or
     * {@link UpdateBuilder#jsonParser(UpdateParser)}<br><br>
     *
     *     according to response data from url to create update instance
     */
    private UpdateParser parser;

    public void setBuilder (UpdateBuilder builder) {
        this.entity = builder.getCheckEntity();
        this.checker = builder.getUpdateChecker();
        this.parser = builder.getJsonParser();
    }

    public void setCheckCB (UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    @Override
    public void run() {
        try {
            String response = check(entity);
            Update parse = parser.parse(response);
            if (parse == null) {
                throw new IllegalArgumentException("parse response to update failed by " + parser.getClass().getCanonicalName());
            }
            if (checker.check(parse)) {
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
     * access the url and get response data back
     * @param url The url to be accessed
     * @return response data from url
     * @throws Exception some error occurs when checked
     */
    protected abstract String check(CheckEntity url) throws Exception;

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
        this.checker = null;
        this.parser = null;
        this.entity = null;
    }
}
