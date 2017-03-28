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
import org.lzh.framework.updatepluginlib.creator.InstallChecker;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.DefaultChecker;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.strategy.WifiFirstStrategy;
import org.lzh.framework.updatepluginlib.util.ActivityManager;

/**
 * Global configs
 */
public class UpdateConfig {

    private Context context;

    /**
     * update task
     */
    private UpdateWorker checkWorker;

    /**
     * download task
     */
    private DownloadWorker downloadWorker;
    /**
     * The callback to receive update task info
     */
    private UpdateCheckCB checkCB;
    /**
     * The callback to receive download task info
     */
    private UpdateDownloadCB downloadCB;
    /**
     * The entity will be used by {@link UpdateWorker} to access network
     */
    private CheckEntity entity;
    /**
     * The strategy on update
     */
    private UpdateStrategy strategy;
    /**
     * To create update dialog when should be shown,according to {@link UpdateStrategy#isShowUpdateDialog(Update)}
     */
    private DialogCreator updateDialogCreator;
    /**
     * To create install dialog when should be shown,according to {@link UpdateStrategy#isAutoInstall()}
     */
    private InstallCreator installDialogCreator;
    /**
     * To create download dialog when should be shown,according to {@link UpdateStrategy#isShowDownloadDialog()}
     */
    private DownloadCreator downloadDialogCreator;
    /**
     * The parser to parse response data form url to {@link Update} instance
     */
    private UpdateParser jsonParser;
    /**
     * To create cache apk file name in download task:{@link DownloadWorker}
     */
    private ApkFileCreator fileCreator;
    /**
     * To check out whether or not there are a new version of apk should be updated
     */
    private UpdateChecker updateChecker;

    /**
     * To check out if the apk file is validly.
     */
    private InstallChecker installChecker;

    private static UpdateConfig config;
    public static UpdateConfig getConfig() {
        if (config == null) {
            config = new UpdateConfig();
        }
        return config;
    }

    /**
     * Cache a application context.
     * Should not be called by yourself,
     * cause it had been invoked automatically when app was launched
     * @param context Activity context
     */
    public UpdateConfig init(Context context) {
        if (this.context == null) {
            this.context = context.getApplicationContext();
            ActivityManager.get().registerSelf(this.context);
        }
        return this;
    }

    public UpdateConfig url(String url) {
        this.entity = new CheckEntity().setUrl(url);
        return this;
    }

    public UpdateConfig checkEntity (CheckEntity entity) {
        this.entity = entity;
        return this;
    }

    public UpdateConfig updateChecker(UpdateChecker checker) {
        this.updateChecker = checker;
        return this;
    }

    public UpdateConfig installChecker (InstallChecker checker) {
        this.installChecker = checker;
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
            throw new RuntimeException("should call UpdateConfig.init(context) first");
        }
        return context;
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = new WifiFirstStrategy();
        }
        return strategy;
    }

    public CheckEntity getCheckEntity () {
        if (this.entity == null || TextUtils.isEmpty(this.entity.getUrl())) {
            throw new IllegalArgumentException("Do not set url in CheckEntity");
        }
        return this.entity;
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

    public UpdateChecker getUpdateChecker() {
        if (updateChecker == null) {
            updateChecker = new DefaultChecker();
        }
        return updateChecker;
    }

    public InstallChecker getInstallChecker () {
        return installChecker;
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

