package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.util.ActivityManager;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;

/**
 * The default download callback to receive update event send by {@link org.lzh.framework.updatepluginlib.business.DownloadWorker}
 * @author lzh
 */
public final class DefaultDownloadCB implements UpdateDownloadCB ,Recyclable {

    private UpdateBuilder builder;
    /**
     * set by {@link UpdateBuilder#downloadCB(UpdateDownloadCB)} or
     * {@link org.lzh.framework.updatepluginlib.UpdateConfig#downloadCB(UpdateDownloadCB)}<br>
     */
    private UpdateDownloadCB downloadCB;
    private Update update;
    /**
     * This callback is created by {@link org.lzh.framework.updatepluginlib.creator.DownloadCreator#create(Update, Activity)}<br>
     *     to update UI within this callback
     */
    private UpdateDownloadCB innerCB;

    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
        downloadCB = builder.getDownloadCB();
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    /**
     * Receive and pass download_start event send by {@link DownloadWorker#sendUpdateStart()}
     */
    @Override
    public void onUpdateStart() {
        try {
            if (downloadCB != null) {
                downloadCB.onUpdateStart();
            }
            innerCB = getInnerCB();
            if (innerCB != null) {
                innerCB.onUpdateStart();
            }
        } catch (Throwable t) {
            onUpdateError(t);
        }
    }

    private UpdateDownloadCB getInnerCB() {
        if (innerCB != null || !builder.getStrategy().isShowDownloadDialog()) {
            return innerCB;
        }

        Activity current = ActivityManager.get().topActivity();
        innerCB = builder.getDownloadDialogCreator().create(update,current);
        return innerCB;
    }

    /**
     * Receive and pass download_complete event send by {@link DownloadWorker#sendUpdateComplete(File)}
     *
     * When download complete,The install dialog will be create when {@link UpdateStrategy#isAutoInstall()} is return with false
     */
    @Override
    public void onUpdateComplete(File file) {
        try {
            if (downloadCB != null) {
                downloadCB.onUpdateComplete(file);
            }

            if (innerCB != null) {
                innerCB.onUpdateComplete(file);
            }

            showInstallDialogIfNeed(file);

            release();
        } catch (Throwable t) {
            onUpdateError(t);
        }
    }

    public void showInstallDialogIfNeed(File file) {
        Activity current = ActivityManager.get().topActivity();

        InstallCreator creator = builder.getInstallDialogCreator();
        creator.setCheckCB(builder.getCheckCB());
        creator.setFileChecker(builder.getFileChecker());
        creator.setUpdate(update);
        if (builder.getStrategy().isAutoInstall()) {
            creator.sendToInstall(file.getAbsolutePath());
        } else {
            Dialog dialog = creator.create(update, file.getAbsolutePath(),current);
            SafeDialogOper.safeShowDialog(dialog);
        }
    }

    /**
     * Receive and pass download_progress event send by {@link DownloadWorker#sendUpdateProgress(long, long)}
     */
    @Override
    public void onUpdateProgress(long current,long total) {
        try {
            if (downloadCB != null) {
                downloadCB.onUpdateProgress(current,total);
            }

            if (innerCB != null) {
                innerCB.onUpdateProgress(current,total);
            }
        } catch (Throwable t) {
            onUpdateError(t);
        }

    }

    /**
     * Receive and pass download_error event send by {@link DownloadWorker#sendUpdateError(Throwable)}
     */
    @Override
    public void onUpdateError(Throwable t) {
        try {
            if (downloadCB != null) {
                downloadCB.onUpdateError(t);
            }
            if (innerCB != null) {
                innerCB.onUpdateError(t);
            }
        } catch (Throwable ignore) {
            ignore.printStackTrace();
        } finally {
            release();
        }
    }

    @Override
    public void release() {
        this.builder = null;
        this.innerCB = null;
        this.downloadCB = null;
        this.update = null;
    }
}
