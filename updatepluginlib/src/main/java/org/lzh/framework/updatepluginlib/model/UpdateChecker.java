package org.lzh.framework.updatepluginlib.model;

/**
 * The checker to check whether or not should be updated
 * Created by lzh on 2016/6/20.
 */
public interface UpdateChecker {

    /**
     * check if is a new version
     */
    boolean check(Update update);
}
