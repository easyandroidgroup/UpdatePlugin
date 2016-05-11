package org.lzh.framework.updatepluginlib;

import android.content.Context;
import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.business.DefaultDownloadWorker;
import org.lzh.framework.updatepluginlib.business.DefaultUpdateWorker;
import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultFileCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedDownloadCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedInstallCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedUpdateCreator;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.creator.DownloadCreator;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.strategy.WifiFirstStrategy;

/**
 */
public class UpdateConfig {

    private Context context;
    private UpdateWorker checkWorker;
    private DownloadWorker downloadWorker;
    private UpdateCheckCB checkCB;
    private UpdateDownloadCB downloadCB;
    private String url;
    private UpdateStrategy strategy;
    private DialogCreator updateDialogCreator;
    private InstallCreator installDialogCreator;
    private DownloadCreator downloadDialogCreator;
    private UpdateParser jsonParser;
    private ApkFileCreator fileCreator;

    private static UpdateConfig config;
    public static UpdateConfig getConfig() {
        if (config == null) {
            config = new UpdateConfig();
        }
        return config;
    }

    UpdateConfig context (Context context) {
        if (this.context == null) {
            this.context = context.getApplicationContext();
        }
        return this;
    }

    public UpdateConfig url(String url) {
        this.url = url;
        return this;
    }

    public UpdateConfig checkWorker(UpdateWorker checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    public UpdateConfig downloadWorker(DownloadWorker downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    public UpdateConfig downloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
        return this;
    }

    public UpdateConfig checkCB (UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
        return this;
    }

    public UpdateConfig jsonParser (UpdateParser jsonParser) {
        this.jsonParser = jsonParser;
        return this;
    }

    public UpdateConfig fileCreator (ApkFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    public UpdateConfig downloadDialogCreator (DownloadCreator downloadDialogCreator) {
        this.downloadDialogCreator = downloadDialogCreator;
        return this;
    }

    public UpdateConfig installDialogCreator (InstallCreator installDialogCreator) {
        this.installDialogCreator = installDialogCreator;
        return this;
    }

    public UpdateConfig updateDialogCreator(DialogCreator updateDialogCreator) {
        this.updateDialogCreator = updateDialogCreator;
        return this;
    }

    public UpdateConfig strategy(UpdateStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public Context getContext() {
        if (context == null) {
            throw new RuntimeException("should call UpdateConfig.install first");
        }
        return context;
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = new WifiFirstStrategy();
        }
        return strategy;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url is null");
        }
        return url;
    }

    public DialogCreator getUpdateDialogCreator() {
        if (updateDialogCreator == null) {
            updateDialogCreator = new DefaultNeedUpdateCreator();
        }
        return updateDialogCreator;
    }

    public InstallCreator getInstallDialogCreator() {
        if (installDialogCreator == null) {
            installDialogCreator = new DefaultNeedInstallCreator();
        }
        return installDialogCreator;
    }

    public DownloadCreator getDownloadDialogCreator() {
        if (downloadDialogCreator == null) {
            downloadDialogCreator = new DefaultNeedDownloadCreator();
        }
        return downloadDialogCreator;
    }

    public UpdateParser getJsonParser() {
        if (jsonParser == null) {
            throw new IllegalStateException("update parser is null");
        }
        return jsonParser;
    }

    public UpdateWorker getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = new DefaultUpdateWorker();
        }
        return checkWorker;
    }

    public DownloadWorker getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = new DefaultDownloadWorker();
        }
        return downloadWorker;
    }

    public ApkFileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = new DefaultFileCreator();
        }
        return fileCreator;
    }

    public UpdateCheckCB getCheckCB() {
        return checkCB;
    }

    public UpdateDownloadCB getDownloadCB() {
        return downloadCB;
    }
}
