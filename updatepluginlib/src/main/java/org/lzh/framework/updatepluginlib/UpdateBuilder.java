package org.lzh.framework.updatepluginlib;

import android.app.Activity;

import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.creator.DownloadCreator;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.UpdateParser;

/**
 * @author Administrator
 */
public class UpdateBuilder {

    private UpdateStrategy strategy;
    private DialogCreator updateDialogCreator;
    private InstallCreator installDialogCreator;
    private DownloadCreator downloadDialogCreator;
    private UpdateParser jsonParser;
    private ApkFileCreator fileCreator;

    public static UpdateBuilder create() {
        return new UpdateBuilder();
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
        return strategy;
    }

    public DialogCreator getUpdateDialogCreator() {
        return updateDialogCreator;
    }

    public InstallCreator getInstallDialogCreator() {
        return installDialogCreator;
    }

    public DownloadCreator getDownloadDialogCreator() {
        return downloadDialogCreator;
    }

    public UpdateParser getJsonParser() {
        return jsonParser;
    }

    public ApkFileCreator getFileCreator() {
        return fileCreator;
    }
}
