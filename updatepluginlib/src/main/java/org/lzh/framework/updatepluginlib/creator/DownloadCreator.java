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

import android.app.Activity;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * Download dialog creator
 * @author lzh
 */
public interface DownloadCreator {

    /**
     * To create a download dialog when should be shown,this method returns a {@link UpdateDownloadCB},
     * the download callback will be used in {@link org.lzh.framework.updatepluginlib.business.DownloadWorker},
     * and also called with it to update download progress,
     * @param update The update instance created by {@link org.lzh.framework.updatepluginlib.model.UpdateParser#parse(String)}
     * @param activity The activity instance,cause it is be saved with weak ref,so the context
     *                will be null or finished sometimes when you finish you activity before,
     * @return A {@link UpdateDownloadCB} instance to update ui
     */
    UpdateDownloadCB create(Update update, Activity activity);
}
