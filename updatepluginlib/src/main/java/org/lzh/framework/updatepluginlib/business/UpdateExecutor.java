package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;

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
    public void check(String url, UpdateCheckCB cb) {
        pool.execute(new CheckRunnable(url,cb));
    }

    @Override
    public void download(String url, UpdateDownloadCB cb) {
        pool.execute(new DownloadRunnable(url,cb));
    }

}
