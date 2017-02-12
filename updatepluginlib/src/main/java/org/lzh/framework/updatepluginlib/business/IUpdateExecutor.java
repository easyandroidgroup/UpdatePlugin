package org.lzh.framework.updatepluginlib.business;

public interface IUpdateExecutor {

    /**
     * Launch a <b>Check</b> task which checks if is new version exist;
     */
    void check(UpdateWorker worker);

    /**
     * Launch a <b>Download</b> task that requests download new version apk
     * @param worker The DownloadWorker task
     */
    void download(DownloadWorker worker);
}
