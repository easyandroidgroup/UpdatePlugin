package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.Recyclable;
import org.lzh.framework.updatepluginlib.util.Utils;

import java.io.File;

/**
 * The task to download new version apk
 * @author lzh
 */
public abstract class DownloadWorker extends UnifiedWorker implements Runnable,Recyclable {

    /**
     * The url set by {@link Update#getUpdateUrl()}
     */
    protected String url;
    /**
     * The instance of {@link org.lzh.framework.updatepluginlib.callback.DefaultDownloadCB}
     */
    protected UpdateDownloadCB downloadCB;
    /**
     * The file was created by {@link org.lzh.framework.updatepluginlib.creator.ApkFileCreator#create(String)}
     */
    protected File cacheFileName;
    protected Update update;

    public void setUpdate(Update update) {
        this.update = update;
        this.url = update.getUpdateUrl();
    }

    public void setCacheFileName(File cacheFileName) {
        this.cacheFileName = cacheFileName;
    }

    public void setDownloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
    }

    @Override
    public void run() {
        try {
            cacheFileName.getParentFile().mkdirs();
            sendUpdateStart();
            download(url,cacheFileName);
            sendUpdateComplete(cacheFileName);
        } catch (Throwable e) {
            sendUpdateError(e);
        } finally {
            setRunning(false);
        }
    }

    protected abstract void download(String url, File target) throws Exception;

    private void sendUpdateStart() {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onUpdateStart();
            }
        });
    }

    protected void sendUpdateProgress(final long current, final long total) {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onUpdateProgress(current, total);
            }
        });
    }

    private void sendUpdateComplete(final File file) {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onUpdateComplete(file);
                release();
            }
        });
    }

    private void sendUpdateError (final Throwable t) {
        if (downloadCB == null) return;

        Utils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadCB == null) return;
                downloadCB.onUpdateError(t);
                release();
            }
        });
    }

    @Override
    public void release() {
        this.downloadCB = null;
        this.update = null;
    }
}
