package org.lzh.framework.updateplugin.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

/**
 * 自定义检查出有新版本需要更新时的Dialog创建器
 */
public class CustomNeedUpdateCreator extends DialogCreator {
    /**
     * @param update  更新数据实体类
     * @param context 此Dialog创建器使用的activity实例。默认为使用UpdateBuilder.check(activity)方法传入。
     *                 需注意：此activity传入后使用弱引用进行保存。故有可能使用时activity为null。或者已经finished.,
     * @return 需要显示的Dialog
     */
    @Override
    public Dialog create(final Update update, final Activity context) {
        String updateContent = context.getText(org.lzh.framework.updatepluginlib.R.string.update_version_name)
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        AlertDialog.Builder builder =  new AlertDialog.Builder(context)
                .setMessage(updateContent)
                .setTitle(org.lzh.framework.updatepluginlib.R.string.update_title)
                .setPositiveButton(org.lzh.framework.updatepluginlib.R.string.update_immediate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 发送下载请求
                        sendDownloadRequest(update);
                        SafeDialogOper.safeDismissDialog((Dialog) dialog);
                    }
                });
        if (update.isIgnore() && !update.isForced()) {
            builder.setNeutralButton(org.lzh.framework.updatepluginlib.R.string.update_ignore, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 用户忽略此版本更新点击
                    sendUserIgnore(update);
                    SafeDialogOper.safeDismissDialog((Dialog) dialog);
                }
            });
        }

        if (!update.isForced()) {
            builder.setNegativeButton(org.lzh.framework.updatepluginlib.R.string.update_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 用户取消更新
                    sendUserCancel();
                    SafeDialogOper.safeDismissDialog((Dialog) dialog);
                }
            });
        }
        builder.setCancelable(false);
        return builder.create();
    }
}
