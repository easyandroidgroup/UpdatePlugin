package org.lzh.framework.updatepluginlib.strategy;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public interface UpdateStrategy {

    boolean isShowUpdateDialog(Update update);
}
