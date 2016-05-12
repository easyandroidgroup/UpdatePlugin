package org.lzh.framework.updatepluginlib;

import android.app.Activity;

import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.business.IUpdateExecutor;
import org.lzh.framework.updatepluginlib.business.UpdateExecutor;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.callback.DefaultCheckCB;
import org.lzh.framework.updatepluginlib.callback.DefaultDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 *
 * @author lzh
 */
public class Updater {
    private static Updater updater;
    private IUpdateExecutor executor;

    private Updater() {
        executor = UpdateExecutor.getInstance();
    }
    public static Updater getInstance() {
        if (updater == null) {
            updater = new Updater();
        }
        return updater;
    }

    /**
     * check update entry.
     * @param activity
     * @param builder
     */
    public void checkUpdate(Activity activity,UpdateBuilder builder) {
        UpdateConfig.getConfig().context(activity);

        DefaultCheckCB checkCB = new DefaultCheckCB(activity);
        checkCB.setBuilder(builder);

        UpdateWorker checkWorker = builder.getCheckWorker();
        checkWorker.setUrl(builder.getUrl());
        checkWorker.setParser(builder.getJsonParser());
        checkWorker.setCheckCB(checkCB);

        executor.check(builder.getCheckWorker());
    }

    public void downUpdate(Activity activity,Update update,UpdateBuilder builder) {
        UpdateConfig.getConfig().context(activity);
        DefaultDownloadCB downloadCB = new DefaultDownloadCB(activity);
        downloadCB.setBuilder(builder);
        downloadCB.setUpdate(update);
        downloadCB.setDownloadCB(builder.getDownloadCB());

        DownloadWorker downloadWorker = builder.getDownloadWorker();
        downloadWorker.setUrl(update.getUpdateUrl());
        downloadWorker.setDownloadCB(downloadCB);
        downloadWorker.setCacheFileName(builder.getFileCreator().create(update.getVersionName()));

        executor.download(downloadWorker);
    }

}
