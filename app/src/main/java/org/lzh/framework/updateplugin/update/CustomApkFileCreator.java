package org.lzh.framework.updateplugin.update;

import android.os.Environment;

import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;

import java.io.File;

/**
 * 生成下载apk文件的文件地址
 * 默认使用参考 {@link org.lzh.framework.updatepluginlib.creator.DefaultFileCreator}
 */
public class CustomApkFileCreator implements ApkFileCreator {
    @Override
    public File create(String versionName) {
        // 根据传入的versionName创建下载时使用的文件名
        File path = new File(Environment.getExternalStorageDirectory().getPath() + "/updatePlugin");
        boolean mkdirs = path.mkdirs();
        return new File(path,"ver_" + versionName);
    }
}
