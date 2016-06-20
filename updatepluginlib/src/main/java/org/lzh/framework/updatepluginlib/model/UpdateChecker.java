package org.lzh.framework.updatepluginlib.model;

/**
 * 新版本检查器
 * Created by lzh on 2016/6/20.
 */
public interface UpdateChecker {

    /**
     * 判断是否含有新版本
     * @return 返回true代表需要更新。
     */
    boolean check(Update update);
}
