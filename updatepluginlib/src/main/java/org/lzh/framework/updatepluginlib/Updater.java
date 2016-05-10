package org.lzh.framework.updatepluginlib;

import android.app.Activity;

import org.lzh.framework.updatepluginlib.business.IUpdateExecutor;
import org.lzh.framework.updatepluginlib.business.UpdateExecutor;
import org.lzh.framework.updatepluginlib.callback.DefaultCheckCB;

/**
 * @author Administrator
 */
public class Updater {
    private static Updater updater;
    private UpdateConfig config;
    private IUpdateExecutor executor;

    private Updater() {}

    static Updater create(UpdateConfig config) {
        updater = new Updater();
        updater.executor = UpdateExecutor.getInstance();
        updater.config = config;
        return updater;
    }

    public static Updater getInstance() {
        if (updater == null) {
            throw new IllegalStateException("should initial UpdateConfig first,call UpdateConfig.install(Context)");
        }
        return updater;
    }

    public void checkUpdate(Activity activity,UpdateBuilder builder) {
        DefaultCheckCB checkCB = new DefaultCheckCB(activity);
        checkCB.setBuilder(builder);
        executor.check(config.getUrl(),new DefaultCheckCB(activity));
    }

}
