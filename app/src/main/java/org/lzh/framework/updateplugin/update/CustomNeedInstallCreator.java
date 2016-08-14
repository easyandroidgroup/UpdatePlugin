package org.lzh.framework.updateplugin.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

/**
 *
 */
public class CustomNeedInstallCreator extends InstallCreator {
    /**
     * 在下载完成后。当更新策略的inAutoInstall为false时。即会调用此create方法。
     * 返回dialog。并进行显示
     * @param update 此更新版本的实体类
     * @param path 此次更新下载的文件，由配置类的fileCreate方法指定
     * @param activity 此Dialog创建器使用的activity实例。默认为使用UpdateBuilder.check(activity)方法传入。
     *                 需注意：此activity传入后使用弱引用进行保存。故有可能使用时activity为null。或者已经finished.
     * @return 需要显示的Dialog
     */
    @Override
    public Dialog create(final Update update, final String path, Activity activity) {
        String updateContent = "需要更新的版本号"
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(org.lzh.framework.updatepluginlib.R.string.install_title)
                .setMessage(updateContent)
                .setPositiveButton(org.lzh.framework.updatepluginlib.R.string.install_immediate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!update.isForced()) {
                            SafeDialogOper.safeDismissDialog((Dialog) dialog);
                        }
                        // 需要进行安装时。调用此方法
                        sendToInstall(path);
                    }
                });

        if (!update.isForced() && update.isIgnore()) {
            builder.setNeutralButton(org.lzh.framework.updatepluginlib.R.string.update_ignore, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 当用户需要忽略此版本更新时。调用此方法
                    sendCheckIgnore(update);
                    SafeDialogOper.safeDismissDialog((Dialog) dialog);
                }
            });
        }

        if (!update.isForced()) {
            builder.setNegativeButton(org.lzh.framework.updatepluginlib.R.string.update_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 当用户取消更新时。调用此方法
                    sendUserCancel();
                    SafeDialogOper.safeDismissDialog((Dialog) dialog);
                }
            });
        }
        AlertDialog installDialog = builder.create();
        installDialog.setCancelable(false);
        installDialog.setCanceledOnTouchOutside(false);
        return installDialog;
    }
}
