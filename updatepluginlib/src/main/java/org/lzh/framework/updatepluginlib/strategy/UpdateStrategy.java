package org.lzh.framework.updatepluginlib.strategy;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author lzh
 */
public interface UpdateStrategy {

    /**
     * whether or not to show update dialog,
     * if set to true,a update dialog will be shown when there is a new version of apk should be updated
     */
    boolean isShowUpdateDialog(Update update);

    /**
     * whether or not automatic installation without show install dialog,
     * if set to false,a install dialog will be shown after download success
     */
    boolean isAutoInstall();

    /**
     * whether or not to show download dialog,
     * if set to true,a download dialog will be shown when download task running
     */
    boolean isShowDownloadDialog();
}
