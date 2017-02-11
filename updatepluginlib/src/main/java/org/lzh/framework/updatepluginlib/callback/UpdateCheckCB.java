package org.lzh.framework.updatepluginlib.callback;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * The update check callback
 */
public interface UpdateCheckCB {

    /**
     * When you start a upgrade task.this method should be invoked.
     * This method runs on a thread which you launch the update task.
     */
    void onCheckStart ();

    /**
     * There are a new version of APK on network
     * @param update Update entity
     */
    void hasUpdate(Update update);

    /**
     * There are no new version for update
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

    /**
     * to be invoked by user press ignore button.
     * @param update Update entity
     */
    void onCheckIgnore(Update update);
}
