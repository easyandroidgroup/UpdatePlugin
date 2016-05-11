package org.lzh.framework.updatepluginlib.business;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
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
    public void check(final UpdateWorker worker) {
        pool.execute(worker);
    }

    @Override
    public void download(DownloadWorker worker) {
        pool.execute(worker);
    }
}
