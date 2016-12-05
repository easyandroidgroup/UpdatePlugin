package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.util.Recycler;
import org.lzh.framework.updatepluginlib.util.Recycler.Recycleable;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * The default download callback to receive update event send by {@link org.lzh.framework.updatepluginlib.business.DownloadWorker}
 * @author lzh
 */
public class DefaultDownloadCB implements UpdateDownloadCB ,Recycleable{

    private WeakReference<Activity> actRef = null;
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

    public DefaultDownloadCB(Activity activity) {
        actRef = new WeakReference<>(activity);
    }

    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
        downloadCB = builder.getDownloadCB();
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void setDownloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
    }

    /**
     * Receive and pass download_start event send by {@link DownloadWorker#sendUpdateStart()}
     */
    @Override
    public void onUpdateStart() {
        // replace activity when necessary
        if (builder.getReplaceCB() != null) {
            actRef = new WeakReference<>(builder.getReplaceCB().replace(actRef.get()));
        }
        if (downloadCB != null) {
            downloadCB.onUpdateStart();
        }
        innerCB = getInnerCB();
        if (innerCB != null) {
            innerCB.onUpdateStart();
        }
    }

    public UpdateDownloadCB getInnerCB() {
        if (innerCB == null && builder.getStrategy().isShowDownloadDialog()) {
            innerCB = builder.getDownloadDialogCreator().create(update,actRef.get());
        }
        return innerCB;
    }

    /**
     * Receive and pass download_complete event send by {@link DownloadWorker#sendUpdateComplete(File)}
     *
     * When download complete,The install dialog will be create when {@link UpdateStrategy#isAutoInstall()} is return with false
     */
    @Override
    public void onUpdateComplete(File file) {
        if (downloadCB != null) {
            downloadCB.onUpdateComplete(file);
        }

        if (innerCB != null) {
            innerCB.onUpdateComplete(file);
        }

        Activity current = actRef.get();
        if (builder.getReplaceCB() != null) {
            current = builder.getReplaceCB().replace(current);
        }

        InstallCreator creator = builder.getInstallDialogCreator();
        creator.setCheckCB(builder.getCheckCB());
        creator.setInstallChecker(builder.getInstallChecker());
        creator.setUpdate(update);
        if (builder.getStrategy().isAutoInstall()) {
            creator.sendToInstall(file.getAbsolutePath());
        } else {
            Dialog dialog = creator.create(update, file.getAbsolutePath(),current);
            SafeDialogOper.safeShowDialog(dialog);
        }

        Recycler.release(this);
    }

    /**
     * Receive and pass download_progress event send by {@link DownloadWorker#sendUpdateProgress(long, long)}
     */
    @Override
    public void onUpdateProgress(long current,long total) {
        if (downloadCB != null) {
            downloadCB.onUpdateProgress(current,total);
        }

        if (innerCB != null) {
            innerCB.onUpdateProgress(current,total);
        }
    }

    /**
     * Receive and pass download_error event send by {@link DownloadWorker#sendUpdateError(int, String)}
     */
    @Override
    public void onUpdateError(int code,String errorMsg) {
        if (downloadCB != null) {
            downloadCB.onUpdateError(code,errorMsg);
        }

        if (innerCB != null) {
            innerCB.onUpdateError(code,errorMsg);
        }

        Recycler.release(this);
    }

    @Override
    public void release() {
        this.actRef = null;
        this.builder = null;
        this.innerCB = null;
        this.downloadCB = null;
        this.update = null;
    }
}
