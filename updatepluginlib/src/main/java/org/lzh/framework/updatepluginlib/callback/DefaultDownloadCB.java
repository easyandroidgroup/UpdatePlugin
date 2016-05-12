package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.Dialog;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.InstallUtil;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author Administrator
 */
public class DefaultDownloadCB implements UpdateDownloadCB {

    private WeakReference<Activity> actRef = null;
    private UpdateBuilder builder;
    private UpdateDownloadCB downloadCB;
    private Update update;
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

    @Override
    public void onUpdateStart() {
        if (downloadCB != null) {
            downloadCB.onUpdateStart();
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateStart();
        }
    }

    public UpdateDownloadCB getInnerCB() {
        if (innerCB == null && builder.getStrategy().isShowDownloadDialog()) {
            innerCB = builder.getDownloadDialogCreator().create(update,actRef.get());
        }
        return innerCB;
    }

    @Override
    public void onUpdateComplete(File file) {
        if (downloadCB != null) {
            downloadCB.onUpdateComplete(file);
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateComplete(file);
        }

        if (builder.getStrategy().isShowInstallDialog()) {
            InstallCreator creator = builder.getInstallDialogCreator();
            creator.setCheckCB(builder.getCheckCB());
            Dialog dialog = creator.create(update, file.getAbsolutePath(),actRef.get());
            SafeDialogOper.safeShowDialog(dialog);
        }else if (builder.getStrategy().isAutoInstall()) {
            InstallUtil.installApk(UpdateConfig.getConfig().getContext(),file.getAbsolutePath());
        }
    }

    @Override
    public void onUpdateProgress(long current,long total) {
        if (downloadCB != null) {
            downloadCB.onUpdateProgress(current,total);
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateProgress(current,total);
        }
    }

    @Override
    public void onUpdateError(int code,String errorMsg) {
        if (downloadCB != null) {
            downloadCB.onUpdateError(code,errorMsg);
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateError(code,errorMsg);
        }
    }
}
