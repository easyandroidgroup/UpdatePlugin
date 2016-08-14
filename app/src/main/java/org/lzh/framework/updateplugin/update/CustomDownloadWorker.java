package org.lzh.framework.updateplugin.update;

import org.lzh.framework.updatepluginlib.business.DefaultDownloadWorker;
import org.lzh.framework.updatepluginlib.business.DownloadWorker;

import java.io.File;

/**
 * 自定义的APK文件下载任务
 * 默认使用参考 {@link org.lzh.framework.updatepluginlib.business.DefaultDownloadWorker}
 */
public class CustomDownloadWorker extends DownloadWorker {
    @Override
    protected void download(String url, File target) throws Exception {
        // 在方法运行于后台线程。在此进行文件下载任务，将url指定的文件下载于文件target中并保存。
        // 在此进行下载时。需要调用sendUpdateProgress(current,total)进行下载进度的传递。以便更新界面
        // 此处为模拟下载任务
        for (int i = 0; i < 100; i++) {
            Thread.sleep(500);
            sendUpdateProgress(i,100);
        }
    }
}
