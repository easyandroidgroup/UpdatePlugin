package org.lzh.framework.updateplugin.update;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.creator.DownloadCreator;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;
import java.util.UUID;

public class NotificationDownloadCreator implements DownloadCreator {
    @Override
    public UpdateDownloadCB create(Update update, Activity activity) {
        return new NotificationCB(activity);
    }

    static class NotificationCB implements UpdateDownloadCB {

        CharSequence applicationName;
        NotificationManager manager;
        Notification notification;
        int id;

        public NotificationCB (Activity activity) {
            this.applicationName = activity.getApplicationInfo().loadLabel(activity.getPackageManager());
            this.manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            this.notification = new Notification();
            this.notification.icon = activity.getApplicationInfo().icon;
            id = Math.abs(UUID.randomUUID().hashCode());
        }

        @Override
        public void onUpdateStart() {
            notification.tickerText = "下载中：" + 0 + "%";
            manager.notify(id,notification);
        }

        @Override
        public void onUpdateComplete(File file) {
            manager.cancel(id);
        }

        @Override
        public void onUpdateProgress(long current, long total) {
            int progress = (int) (current * 1f / total * 100);
            notification.tickerText = "下载中：" + progress + "%";
        }

        @Override
        public void onUpdateError(int code, String errorMsg) {
            manager.cancel(id);
        }
    }
}
