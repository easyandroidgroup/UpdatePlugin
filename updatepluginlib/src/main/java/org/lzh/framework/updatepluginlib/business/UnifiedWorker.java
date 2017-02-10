package org.lzh.framework.updatepluginlib.business;

public class UnifiedWorker {

    private volatile boolean isRunning;

    void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning () {
        return isRunning;
    }
}
