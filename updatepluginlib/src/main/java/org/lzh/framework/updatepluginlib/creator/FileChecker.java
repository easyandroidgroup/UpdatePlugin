package org.lzh.framework.updatepluginlib.creator;

import org.lzh.framework.updatepluginlib.model.Update;

/**
 * A tool to check if the apk file is valid.
 */
public interface FileChecker {

    /**
     * Check if that file is valid before start download task.
     * @param update update entity
     * @param file the cache file name create by {@link ApkFileCreator#create(String)}
     * @return true if check successful
     */
    boolean checkPreFile(Update update, String file);

    /**
     * Check if that file is valid before start install task.
     * @param update update entity
     * @param file the cache file name create by {@link ApkFileCreator#create(String)}
     * @return true if check successful
     */
    boolean checkAfterDownload (Update update, String file);
}
