package org.lzh.framework.updatepluginlib.callback;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * The update check callback
 */
public interface UpdateCheckCB {

    /**
     * there are a new version of APK on server
     */
    void hasUpdate(Update update);

    /**
     * no new version for update
     */
    void noUpdate();

    /**
     * http check error,
     * @param code http code
     * @param errorMsg http error msg
     */
    void onCheckError(int code, String errorMsg);

    /**
     * to be invoked by user press cancel button.
     */
    void onUserCancel();
}
