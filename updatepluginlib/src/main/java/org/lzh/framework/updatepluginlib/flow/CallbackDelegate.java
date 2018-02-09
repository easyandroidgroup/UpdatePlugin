package org.lzh.framework.updatepluginlib.flow;

import org.lzh.framework.updatepluginlib.base.CheckCallback;
import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.L;

import java.io.File;

/**
 * 更新回调的代理类，用于统一进行回调分发。并打印日志辅助调试
 *
 * @author haoge on 2017/9/26.
 */
public final class CallbackDelegate implements CheckCallback, DownloadCallback {

    private CheckCallback checkProxy;
    private DownloadCallback downloadProxy;
    private RetryCallback retryCallback;

    public void setCheckDelegate(CheckCallback checkProxy) {
        this.checkProxy = checkProxy;
    }

    public void setDownloadDelegate(DownloadCallback downloadProxy) {
        this.downloadProxy = downloadProxy;
    }

    @Override
    public void onDownloadStart() {
        L.d("start downloading。。。");
        if (downloadProxy != null) {
            downloadProxy.onDownloadStart();
        }

        if (retryCallback != null) {
            retryCallback.onDownloadStart();
        }
    }

    @Override
    public void onDownloadComplete(File file) {
        L.d("Download completed to file [%s]", file.getAbsoluteFile());
        if (downloadProxy != null) {
            downloadProxy.onDownloadComplete(file);
        }

        if (retryCallback != null) {
            retryCallback.onDownloadComplete(file);
        }
    }

    @Override
    public void onDownloadProgress(long current, long total) {
        L.d("Downloading... current is %s and total is %s", current, total);
        if (downloadProxy != null) {
            downloadProxy.onDownloadProgress(current, total);
        }

        if (retryCallback != null) {
            retryCallback.onDownloadProgress(current, total);
        }
    }

    @Override
    public void onDownloadError(Throwable t) {
        L.e(t, "Download task has occurs error: %s", t.getMessage());
        if (downloadProxy != null) {
            downloadProxy.onDownloadError(t);
        }

        if (retryCallback != null) {
            retryCallback.onDownloadError(t);
        }
    }
    @Override
    public void onCheckStart() {
        L.d("starting check update task.");
        if (checkProxy != null) {
            checkProxy.onCheckStart();
        }

        if (retryCallback != null) {
            retryCallback.onCheckStart();
        }
    }

    @Override
    public void hasUpdate(Update update) {
        L.d("Checkout that new version apk is exist: update is %s", update);
        if (checkProxy != null) {
            checkProxy.hasUpdate(update);
        }

        if (retryCallback != null) {
            retryCallback.hasUpdate(update);
        }
    }

    @Override
    public void noUpdate() {
        L.d("There are no new version exist");
        if (checkProxy != null) {
            checkProxy.noUpdate();
        }

        if (retryCallback != null) {
            retryCallback.noUpdate();
        }
    }

    @Override
    public void onCheckError(Throwable t) {
        L.e(t, "check update failed: cause by : %s", t.getMessage());
        if (checkProxy != null) {
            checkProxy.onCheckError(t);
        }

        if (retryCallback != null) {
            retryCallback.onCheckError(t);
        }
    }

    @Override
    public void onUserCancel() {
        L.d("update task has canceled by user");
        if (checkProxy != null) {
            checkProxy.onUserCancel();
        }

        if (retryCallback != null) {
            retryCallback.onUserCancel();
        }
    }

    @Override
    public void onCheckIgnore(Update update) {
        L.d("ignored for this update: " + update);
        if (checkProxy != null) {
            checkProxy.onCheckIgnore(update);
        }

        if (retryCallback != null) {
            retryCallback.onCheckIgnore(update);
        }
    }

    public void setRetryCallback(RetryCallback retryCallback) {
        this.retryCallback = retryCallback;
    }
}
