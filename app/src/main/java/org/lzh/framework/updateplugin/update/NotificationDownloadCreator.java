package org.lzh.framework.updateplugin.update;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.base.DownloadNotifier;
import org.lzh.framework.updatepluginlib.impl.DefaultDownloadNotifier;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;
import java.util.UUID;

/**
 * <p>
 *     很多小伙伴提意见说需要一个下载时在通知栏进行进度条显示更新的功能。
 *     此类用于提供此种需求的解决方案。以及如何对其进行定制。满足任意场景使用
 *     默认使用参考：{@link DefaultDownloadNotifier}
 * </p>
 */
public class NotificationDownloadCreator implements DownloadNotifier {
    @Override
    public DownloadCallback create(Update update, Activity activity) {
        // 返回一个UpdateDownloadCB对象用于下载时使用来更新界面。
        return new NotificationCB(activity);
    }

    private static class NotificationCB implements DownloadCallback {

        NotificationManager manager;
        NotificationCompat.Builder builder;
        int id;
        int preProgress;

        NotificationCB (Activity activity) {
            this.manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(activity);
            builder.setProgress(100, 0, false)
                    .setSmallIcon(activity.getApplicationInfo().icon)
                    .setAutoCancel(false)
                    .setContentText("下载中...")
                    .build();
            id = Math.abs(UUID.randomUUID().hashCode());
        }

        @Override
        public void onDownloadStart() {
            // 下载开始时的通知回调。运行于主线程
            manager.notify(id,builder.build());
        }

        @Override
        public void onDownloadComplete(File file) {
            // 下载完成的回调。运行于主线程
            manager.cancel(id);
        }

        @Override
        public void onDownloadProgress(long current, long total) {
            // 下载过程中的进度信息。在此获取进度信息。运行于主线程
            int progress = (int) (current * 1f / total * 100);
            // 过滤不必要的刷新进度
            if (preProgress < progress) {
                preProgress = progress;
                builder.setProgress(100,progress,false);
                manager.notify(id,builder.build());
            }
        }

        @Override
        public void onDownloadError(Throwable t) {
            // 下载时出错。运行于主线程
            manager.cancel(id);
        }
    }
}
