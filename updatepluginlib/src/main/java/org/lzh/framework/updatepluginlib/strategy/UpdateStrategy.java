package org.lzh.framework.updatepluginlib.strategy;

import org.lzh.framework.updatepluginlib.model.Update;

public interface UpdateStrategy {

    /**
     * whether or not to show update dialog,
     * if set to true,a update dialog will be shown when there is a new version of apk should be updated
     * @param update The update entity to create update dialog.
     * @return true if should show update dialog
     */
    boolean isShowUpdateDialog(Update update);

    /**
     * whether or not automatic installation without show install dialog,
     * if set to false,a install dialog will be shown after download success
     * @return true if should install apk without show install dialog
     */
    boolean isAutoInstall();

    /**
     * whether or not to show download dialog,
     * if set to true,a download dialog will be shown when download task running
     * @return true if should show download dialog
     */
    boolean isShowDownloadDialog();
}
