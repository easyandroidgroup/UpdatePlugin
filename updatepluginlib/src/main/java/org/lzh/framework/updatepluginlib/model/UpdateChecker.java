package org.lzh.framework.updatepluginlib.model;

/**
 * The checker to check whether or not should be updated
 * Created by lzh on 2016/6/20.
 */
public interface UpdateChecker {

    /**
     * check if is a new version
     * @param update The update entity to be checked
     * @return true if is valid
     */
    boolean check(Update update);
}
