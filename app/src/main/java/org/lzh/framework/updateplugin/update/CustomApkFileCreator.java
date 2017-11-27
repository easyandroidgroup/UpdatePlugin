package org.lzh.framework.updateplugin.update;

import android.os.Environment;

import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * 生成下载apk文件的文件地址
 * 默认使用参考 {@link org.lzh.framework.updatepluginlib.creator.DefaultFileCreator}
 */
public class CustomApkFileCreator implements ApkFileCreator {
    @Override
    public File create(Update update) {
        // 根据传入的versionName创建一个文件。供下载时网络框架使用
        File path = new File(Environment.getExternalStorageDirectory().getPath() + "/updatePlugin");
        path.mkdirs();
        return new File(path,"UpdatePlugin_" + update.getVersionName());
    }

}
