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
import android.app.ProgressDialog;
import android.util.Log;

import org.lzh.framework.updatepluginlib.base.DownloadNotifier;
import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogHandle;

import java.io.File;

/**
 * 默认使用的下载进度通知创建器: 在此创建Dialog弹窗显示并根据下载回调通知进行进度条更新
 * @author haoge
 */
public class DefaultDownloadNotifier implements DownloadNotifier {
    @Override
    public DownloadCallback create(Update update, Activity activity) {
        if (activity == null || activity.isFinishing()) {
            Log.e("DownDialogCreator--->","show download dialog failed:activity was recycled or finished");
            return null;
        }
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        SafeDialogHandle.safeShowDialog(dialog);
        return new DownloadCallback() {
            @Override
            public void onDownloadStart() {
            }

            @Override
            public void onDownloadComplete(File file) {
                SafeDialogHandle.safeDismissDialog(dialog);
            }

            @Override
            public void onDownloadProgress(long current, long total) {
                int percent = (int) (current * 1.0f / total * 100);
                dialog.setProgress(percent);
            }

            @Override
            public void onDownloadError(Throwable t) {
                SafeDialogHandle.safeDismissDialog(dialog);
            }
        };
    }
}
