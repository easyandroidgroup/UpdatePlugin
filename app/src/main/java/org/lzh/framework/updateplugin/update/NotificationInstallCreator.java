package org.lzh.framework.updateplugin.update;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.NotificationCompat;

import org.lzh.framework.updateplugin.R;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;

import java.util.UUID;

public class NotificationInstallCreator extends InstallCreator {
    private final static String ACTION_INSTALL = "action.complete.install";
    private final static String ACTION_CANCEL = "action.complete.cancel";
    private RequestInstallReceiver requestInstallReceiver;
    private String path;
    int id;
    NotificationManager manager;
    @Override
    public Dialog create(Update update, String path, Activity activity) {
        requestInstallReceiver = new RequestInstallReceiver();
        registerReceiver(activity);
        createNotification(activity);
        this.path = path;

        // 由于需要使用通知实现。此处返回null即可
        return null;
    }

    private void createNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("UpdatePlugin")
                .setContentText("APK安装包已下载完成，点击安装")
                .setDeleteIntent(createCancelPendingIntent(context))
                .setContentIntent(createOneShotPendingIntent(context));

        Notification notification = builder.build();
        int id = Math.abs(UUID.randomUUID().hashCode());
        manager.notify(id,notification);
    }

    private PendingIntent createCancelPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_CANCEL);
        return PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private PendingIntent createOneShotPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_INSTALL);
        return PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public class RequestInstallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println(intent);
            unregisterReceiver(context);

            if (ACTION_INSTALL.equals(intent.getAction())) {
                // 发送安装请求。继续更新流程
                sendToInstall(path);
            } else {
                // 中断更新流程并通知用户取消更新
                sendUserCancel();
            }
            manager.cancel(id);
        }
    }

    private void registerReceiver (Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_INSTALL);
        filter.addAction(ACTION_CANCEL);
        context.registerReceiver(requestInstallReceiver, filter);
    }

    private void unregisterReceiver (Context context) {
        context.unregisterReceiver(requestInstallReceiver);
    }
}
