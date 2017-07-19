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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The executor to check update and download new version on network
 * @author lzh
 */
public class UpdateExecutor implements IUpdateExecutor{

    private static ExecutorService pool;
    private static UpdateExecutor executor;
    private UpdateExecutor () {
        pool = Executors.newSingleThreadExecutor();
    }

    public synchronized static UpdateExecutor getInstance () {
        if (executor == null) {
            executor = new UpdateExecutor();
        }
        return executor;
    }

    @Override
    public synchronized void check(UpdateWorker worker) {
        worker.setRunning(true);
        pool.execute(worker);
    }

    @Override
    public synchronized void download(DownloadWorker worker) {
        worker.setRunning(true);
        pool.execute(worker);
    }

}
