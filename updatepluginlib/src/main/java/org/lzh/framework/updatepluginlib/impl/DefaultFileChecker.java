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

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.base.FileChecker;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.ActivityManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 默认的apk文件检查器。
 *
 * <p>实现逻辑：<br>
 *     - 若{@link Update#getMd5()}为空时。使用{@link PackageManager}读取apk的版本信息并与{@link Update#versionCode}比对<br>
 *     - 若{@link Update#getMd5()}不为空。则计算apk的md5值并与Update中的md5值进行比对。<br>
 *     - 若为在{@link #onCheckBeforeInstall()}，即下载完成后启动安装任务之前的检查。当此时检查失败。则删除apk文件。
 * @author haoge
 */
public class DefaultFileChecker extends FileChecker {

    private void check() throws Exception {
        if (!TextUtils.isEmpty(update.getMd5())) {
            // 当md5值不为null时。使用md5进行检查
            checkByMD5();
        } else {
            checkByPM();
        }
    }

    private void checkByPM() {
        Context context = ActivityManager.get().getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        if (packageInfo.versionCode != update.getVersionCode()) {
            throw new IllegalStateException(
                    String.format("The version code not matched between apk and update entity. apk is %s but update is %s",
                            packageInfo.versionCode, update.getVersionCode())
            );
        }
    }

    private void checkByMD5() throws Exception{
        String md5 = getMD5(file);
        if (!update.getMd5().equals(md5)) {
            throw new RuntimeException(
                    String.format("The md5 not matched between apk and update entity. apk is %s but update is %s",
                            md5, update.getMd5())
            );
        }
    }

    @Override
    public boolean onCheckBeforeDownload() {
        try {
            check();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onCheckBeforeInstall() throws Exception {
        try {
            check();
        } catch (Exception e) {
            // 检查失败。删除apk
            file.delete();
            throw e;
        }
    }

    private String getMD5(File file) throws IOException {
        String md5str = "";
        InputStream is = null;
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2 将消息变成byte数组
            is = new FileInputStream(file);
            byte[] buffer = new byte[8 * 1024];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }

            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest();

            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return md5str;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
