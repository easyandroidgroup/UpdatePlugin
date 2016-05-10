package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;

/**
 */
public interface IUpdateExecutor {

    /**
     * check if is should update
     * @param url the url to check if has new version on network
     * @param cb  callback
     */
    void check(String url,UpdateCheckCB cb);

    /**
     * request download apk file
     *
     * @param url the url to check if has new version on network
     * @param cb  callback
     */
    void download (String url,UpdateDownloadCB cb);
}
