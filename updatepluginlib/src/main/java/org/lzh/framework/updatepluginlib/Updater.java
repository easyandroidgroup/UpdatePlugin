package org.lzh.framework.updatepluginlib;

import android.util.Log;

import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.business.IUpdateExecutor;
import org.lzh.framework.updatepluginlib.business.UpdateExecutor;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.callback.DefaultCheckCB;
import org.lzh.framework.updatepluginlib.callback.DefaultDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

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
     * Check out whether or not there is a new version on internet
     * @param builder update builder that contained all config.
     */
    public void checkUpdate(UpdateBuilder builder) {
        // define a default callback to receive update event send by update task
        DefaultCheckCB checkCB = new DefaultCheckCB();
        checkCB.setBuilder(builder);
        checkCB.onCheckStart();

        UpdateWorker checkWorker = builder.getCheckWorker();
        if (checkWorker.isRunning()) {
            Log.e("Updater","Already have a update task running");
            checkCB.onCheckError(-1,"Already have a update task running");
            return;
        }
        checkWorker.setEntity(builder.getCheckEntity());
        checkWorker.setParser(builder.getJsonParser());
        checkWorker.setChecker(builder.getUpdateChecker());
        checkWorker.setCheckCB(checkCB);

        executor.check(checkWorker);
    }

    /**
     * Request to download the app.
     * @param update update instance,should not be null;
     * @param builder update builder that contained all config;
     */
    public void downUpdate(Update update,UpdateBuilder builder) {
        // define a default download callback to receive callback from download task
        DefaultDownloadCB downloadCB = new DefaultDownloadCB();
        downloadCB.setBuilder(builder);
        downloadCB.setUpdate(update);
        downloadCB.setDownloadCB(builder.getDownloadCB());

        DownloadWorker downloadWorker = builder.getDownloadWorker();
        if (downloadWorker.isRunning()) {
            Log.e("Updater","Already have a download task running");
            downloadCB.onUpdateError(-1,"Already have a download task running");
            return;
        }

        downloadWorker.setUrl(update.getUpdateUrl());
        downloadWorker.setDownloadCB(downloadCB);
        downloadWorker.setCacheFileName(builder.getFileCreator().create(update.getVersionName()));

        executor.download(downloadWorker);
    }

}
