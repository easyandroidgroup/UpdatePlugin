package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.HandlerUtil;
import org.lzh.framework.updatepluginlib.util.Recycler;
import org.lzh.framework.updatepluginlib.util.Recycler.Recycleable;

import java.io.File;

/**
 * The task to download new version apk
 * @author lzh
 */
public abstract class DownloadWorker extends UnifiedWorker implements Runnable,Recycleable{

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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void setCacheFileName(File cacheFileName) {
        this.cacheFileName = cacheFileName;
    }

    public void setDownloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
    }

    @Override
    public void run() {
        cacheFileName.getParentFile().mkdirs();
        try {
            sendUpdateStart();
            download(url,cacheFileName);
            sendUpdateComplete(cacheFileName);
        } catch (HttpException he) {
            sendUpdateError(he.getCode(),he.getErrorMsg());
        } catch (Exception e) {
            sendUpdateError(-1,e.getMessage());
        } finally {
            setRunning(false);
        }
    }

    protected abstract void download(String url, File target) throws Exception;

    private void sendUpdateStart() {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateStart();
            }
        });
    }

    protected void sendUpdateProgress(final long current, final long total) {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateProgress(current, total);
            }
        });
    }

    private void sendUpdateComplete(final File file) {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateComplete(file);
                Recycler.release(DownloadWorker.this);
            }
        });
    }

    private void sendUpdateError (final int code, final String errorMsg) {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateError(code,errorMsg);
                Recycler.release(DownloadWorker.this);
            }
        });
    }

    @Override
    public void release() {
        this.downloadCB = null;
        this.update = null;
    }
}
