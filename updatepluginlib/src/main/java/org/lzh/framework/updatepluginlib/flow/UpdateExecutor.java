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
package org.lzh.framework.updatepluginlib.flow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.CheckWorker;
import org.lzh.framework.updatepluginlib.base.DownloadWorker;

/**
 * <b>核心操作类</b>
 *
 * <p>用于提供一个单线程的线程池使用，用于避免多任务并发的情况。每个{@link UpdateConfig}对应一个此类实例。
 *
 * @author haoge
 */
public final class UpdateExecutor{

    private static ExecutorService pool;
    public UpdateExecutor () {
        pool = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Update Dispatcher");
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    public synchronized final void check(CheckWorker worker) {
        worker.setRunning(true);
        pool.execute(worker);
    }

    public synchronized final void download(DownloadWorker worker) {
        worker.setRunning(true);
        pool.execute(worker);
    }

}
