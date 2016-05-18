package org.lzh.framework.updatepluginlib.strategy;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author lzh
 */
public interface UpdateStrategy {

    /**
     * whether or not to show update dialog
     */
    boolean isShowUpdateDialog(Update update);

    /**
     * whether or not automatic installation without show install dialog
     */
    boolean isAutoInstall();

    /**
     * whether or not to show install dialog
     */
    boolean isShowInstallDialog();

    /**
     * whether or not to show download dialog
     */
    boolean isShowDownloadDialog();
}
