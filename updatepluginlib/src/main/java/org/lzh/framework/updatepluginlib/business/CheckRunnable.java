package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;

/**
 * @author Administrator
 */
public class CheckRunnable implements Runnable{

    private String url;
    private UpdateCheckCB checkCB;

    public CheckRunnable(String url, UpdateCheckCB checkCB) {
        this.url = url;
        this.checkCB = checkCB;
    }

    @Override
    public void run() {

    }
}
