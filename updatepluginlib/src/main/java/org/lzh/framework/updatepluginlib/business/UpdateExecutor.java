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
import java.util.concurrent.ThreadFactory;

/**
 * <b>核心操作类</b>
 *
 * <p>用于提供一个单线程的线程池使用，用于避免多任务并发的情况。
 *
 * @author haoge
 */
public class UpdateExecutor{

    private static ExecutorService pool;
    private static UpdateExecutor executor;
    private UpdateExecutor () {
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

    public synchronized static UpdateExecutor getInstance () {
        if (executor == null) {
            executor = new UpdateExecutor();
        }
        return executor;
    }

    public synchronized void check(UpdateWorker worker) {
        worker.setRunning(true);
        pool.execute(worker);
    }

    public synchronized void download(DownloadWorker worker) {
        worker.setRunning(true);
        pool.execute(worker);
    }

}
