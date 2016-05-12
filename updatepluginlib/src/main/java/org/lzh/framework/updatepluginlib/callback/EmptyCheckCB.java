package org.lzh.framework.updatepluginlib.callback;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public class EmptyCheckCB implements UpdateCheckCB {
    @Override
    public void hasUpdate(Update update) {

    }

    @Override
    public void noUpdate() {

    }

    @Override
    public void onCheckError(int code, String errorMsg) {

    }

    @Override
    public void onUserCancel() {

    }
}
