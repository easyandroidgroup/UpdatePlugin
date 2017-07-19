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

public interface IUpdateExecutor {

    /**
     * Launch a <b>Check</b> task which checks if is new version exist;
     */
    void check(UpdateWorker worker);

    /**
     * Launch a <b>Download</b> task that requests download new version apk
     * @param worker The DownloadWorker task
     */
    void download(DownloadWorker worker);
}
