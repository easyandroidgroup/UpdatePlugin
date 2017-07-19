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

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * The update check callback
 */
public interface UpdateCheckCB {

    /**
     * When you start a upgrade task.this method should be invoked.
     * This method runs on a thread which you launch the update task.
     */
    void onCheckStart ();

    /**
     * There are a new version of APK on network
     * @param update Update entity
     */
    void hasUpdate(Update update);

    /**
     * There are no new version for update
     */
    void noUpdate();

    void onCheckError(Throwable t);

    /**
     * to be invoked by user press cancel button.
     */
    void onUserCancel();

    /**
     * to be invoked by user press ignore button.
     * @param update Update entity
     */
    void onCheckIgnore(Update update);
}
