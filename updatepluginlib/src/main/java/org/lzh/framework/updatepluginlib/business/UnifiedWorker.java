package org.lzh.framework.updatepluginlib.business;

/**
 * Created by lzh on 16-6-21.
 */
public class UnifiedWorker {

    private volatile boolean isRunning;

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning () {
        return isRunning;
    }
}
