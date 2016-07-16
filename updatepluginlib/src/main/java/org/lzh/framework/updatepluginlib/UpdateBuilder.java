package org.lzh.framework.updatepluginlib;

import android.app.Activity;
import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.creator.DownloadCreator;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.UpdateParser;

/**
 *
 * @author lzh
 */
public class UpdateBuilder {

    /**
     * @see UpdateWorker
     */
    private UpdateWorker checkWorker;
    private DownloadWorker downloadWorker;
    private UpdateCheckCB checkCB;
    private UpdateDownloadCB downloadCB;
    private String url;
    private CheckEntity entity;
    private UpdateStrategy strategy;
    private DialogCreator updateDialogCreator;
    private InstallCreator installDialogCreator;
    private DownloadCreator downloadDialogCreator;
    private UpdateParser jsonParser;
    private ApkFileCreator fileCreator;
    private UpdateChecker updateChecker;

    public static UpdateBuilder create() {
        return new UpdateBuilder();
    }

    public UpdateBuilder url(String url) {
        this.entity = new CheckEntity().setUrl(url);
        return this;
    }

    public UpdateBuilder checkEntity (CheckEntity entity) {
        this.entity = entity;
        return this;
    }

    public UpdateBuilder updateChecker (UpdateChecker checker) {
        this.updateChecker = checker;
        return this;
    }

    public UpdateBuilder checkWorker(UpdateWorker checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    public UpdateBuilder downloadWorker(DownloadWorker downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    public UpdateBuilder downloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
        return this;
    }

    public UpdateBuilder checkCB (UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
        return this;
    }

    public UpdateBuilder jsonParser (UpdateParser jsonParser) {
        this.jsonParser = jsonParser;
        return this;
    }

    public UpdateBuilder fileCreator (ApkFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    public UpdateBuilder downloadDialogCreator (DownloadCreator downloadDialogCreator) {
        this.downloadDialogCreator = downloadDialogCreator;
        return this;
    }

    public UpdateBuilder installDialogCreator (InstallCreator installDialogCreator) {
        this.installDialogCreator = installDialogCreator;
        return this;
    }

    public UpdateBuilder updateDialogCreator(DialogCreator updateDialogCreator) {
        this.updateDialogCreator = updateDialogCreator;
        return this;
    }

    public UpdateBuilder strategy(UpdateStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void check(Activity activity) {
        Updater.getInstance().checkUpdate(activity, this);
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = UpdateConfig.getConfig().getStrategy();
        }
        return strategy;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(url)) {
            url = UpdateConfig.getConfig().getUrl();
        }
        return url;
    }

    public CheckEntity getCheckEntity () {
        if (this.entity == null) {
            this.entity = UpdateConfig.getConfig().getCheckEntity();
        }
        return this.entity;
    }

    public UpdateChecker getUpdateChecker() {
        if (updateChecker == null) {
            updateChecker = UpdateConfig.getConfig().getUpdateChecker();
        }
        return updateChecker;
    }

    public DialogCreator getUpdateDialogCreator() {
        if (updateDialogCreator == null) {
            updateDialogCreator = UpdateConfig.getConfig().getUpdateDialogCreator();
        }
        return updateDialogCreator;
    }

    public InstallCreator getInstallDialogCreator() {
        if (installDialogCreator == null) {
            installDialogCreator = UpdateConfig.getConfig().getInstallDialogCreator();
        }
        return installDialogCreator;
    }

    public DownloadCreator getDownloadDialogCreator() {
        if (downloadDialogCreator == null) {
            downloadDialogCreator = UpdateConfig.getConfig().getDownloadDialogCreator();
        }
        return downloadDialogCreator;
    }

    public UpdateParser getJsonParser() {
        if (jsonParser == null) {
            jsonParser = UpdateConfig.getConfig().getJsonParser();
        }
        return jsonParser;
    }

    public UpdateWorker getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = UpdateConfig.getConfig().getCheckWorker();
        }
        return checkWorker;
    }

    public DownloadWorker getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = UpdateConfig.getConfig().getDownloadWorker();
        }
        return downloadWorker;
    }

    public ApkFileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = UpdateConfig.getConfig().getFileCreator();
        }
        return fileCreator;
    }

    public UpdateCheckCB getCheckCB() {
        if (checkCB == null) {
            checkCB = UpdateConfig.getConfig().getCheckCB();
        }
        return checkCB;
    }

    public UpdateDownloadCB getDownloadCB() {
        if (downloadCB == null) {
            downloadCB = UpdateConfig.getConfig().getDownloadCB();
        }
        return downloadCB;
    }
}
