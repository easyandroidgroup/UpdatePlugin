package org.lzh.framework.updatepluginlib.business;

public interface IUpdateExecutor {

    /**
     * check if is new version exist;
     */
    void check(UpdateWorker worker);

    /**
     * request download new version apk
     * @param worker The DownloadWorker task
     */
    void download(DownloadWorker worker);
}
