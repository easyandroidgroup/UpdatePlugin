package org.lzh.framework.updatepluginlib;

import android.content.Context;

import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultFileCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedInstallCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedUpdateCreator;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;

/**
 */
public class UpdateConfig {
    /**
     * update url
     */
    private String url;
    private Context context;
    /**
     * The creator to create dialog when need shown update
     */
    private DialogCreator needUpdateDialog;
    /**
     * The creator to create dialog that apk file has been downloaded;
     */
    private DialogCreator needInstallDialog;

    /**
     * The creator to generate file name by version name
     */
    private ApkFileCreator fileCreator;
    private UpdateConfig () {}
    private static UpdateConfig config;
    public static UpdateConfig install (Context context) {
        UpdateConfig config = getInstance();
        Updater.create(config);
        return config;
    }

    public static UpdateConfig getInstance() {
        if (config == null) {
            config = new UpdateConfig();
        }
        return config;
    }

    /**
     * Set a new file creator,if not set,apk will be cached in system cache dir;
     * @param fileCreator file name creator
     */
    public UpdateConfig fileCreator(ApkFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    /**
     * The creator to create dialog when need shown update
     */
    public UpdateConfig needUpdateDialog(DialogCreator creator) {
        this.needUpdateDialog = creator;
        return this;
    }

    /**
     * The creator to create dialog that apk file has been downloaded;
     */
    public UpdateConfig needInstallDialog(DialogCreator creator) {
        this.needInstallDialog = creator;
        return this;
    }

    /**
     * to indicate update check url
     */
    public UpdateConfig url(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return context;
    }

    public DialogCreator getNeedUpdateDialog() {
        if (needUpdateDialog == null) {
            needUpdateDialog = new DefaultNeedInstallCreator();
        }
        return needUpdateDialog;
    }

    public DialogCreator getNeedInstallDialog() {
        if (needUpdateDialog == null) {
            needUpdateDialog = new DefaultNeedUpdateCreator();
        }
        return needInstallDialog;
    }

    public ApkFileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = new DefaultFileCreator();
        }
        return fileCreator;
    }
}
