package org.lzh.framework.updatepluginlib.business;

/**
 */
public interface IUpdateExecutor {

    /**
     * check if is should update
     */
    void check(UpdateWorker worker);

    /**
     * request check apk file
     */
    void download (DownloadWorker worker);
}
