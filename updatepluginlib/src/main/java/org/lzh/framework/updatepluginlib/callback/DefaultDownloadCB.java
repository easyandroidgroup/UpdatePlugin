package org.lzh.framework.updatepluginlib.callback;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public class DefaultDownloadCB implements UpdateDownloadCB {

    private Update update;
    private UpdateBuilder builder;

    public DefaultDownloadCB(Update update,UpdateBuilder builder) {
        this.update = update;
        this.builder = builder;
    }

    @Override
    public void onUpdateStart() {

    }

    @Override
    public void onUpdateComplete() {
    }

    @Override
    public void onUpdateProgress() {
    }

    @Override
    public void onUpdateError() {
    }
}
