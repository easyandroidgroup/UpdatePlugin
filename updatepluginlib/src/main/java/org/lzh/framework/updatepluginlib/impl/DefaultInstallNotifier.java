/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lzh.framework.updatepluginlib.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;

import org.lzh.framework.updatepluginlib.R;
import org.lzh.framework.updatepluginlib.base.InstallNotifier;
import org.lzh.framework.updatepluginlib.util.SafeDialogHandle;

/**
 * 默认使用的下载完成后的通知创建器：创建一个弹窗提示用户已下载完成。可直接安装。
 *
 * @author haoge
 */
public class DefaultInstallNotifier extends InstallNotifier {

    @Override
    public Dialog create(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            Log.e("DownDialogCreator--->","show install dialog failed:activity was recycled or finished");
            return null;
        }
        String updateContent = activity.getText(R.string.update_version_name)
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(R.string.install_title)
                .setMessage(updateContent)
                .setPositiveButton(R.string.install_immediate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!update.isForced()) {
                            SafeDialogHandle.safeDismissDialog((Dialog) dialog);
                        }
                        sendToInstall();
                    }
                });

        if (!update.isForced() && update.isIgnore()) {
            builder.setNeutralButton(R.string.update_ignore, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendCheckIgnore();
                    SafeDialogHandle.safeDismissDialog((Dialog) dialog);
                }
            });
        }

        if (!update.isForced()) {
            builder.setNegativeButton(R.string.update_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendUserCancel();
                    SafeDialogHandle.safeDismissDialog((Dialog) dialog);
                }
            });
        }
        AlertDialog installDialog = builder.create();
        installDialog.setCancelable(false);
        installDialog.setCanceledOnTouchOutside(false);
        return installDialog;
    }

}
