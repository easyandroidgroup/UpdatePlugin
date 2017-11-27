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
package org.lzh.framework.updatepluginlib.model;

import org.lzh.framework.updatepluginlib.creator.DefaultNeedInstallCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedUpdateCreator;
import org.lzh.framework.updatepluginlib.strategy.ForcedUpdateStrategy;

/**
 * 此实体类用于存储框架所需的更新数据。
 *
 * @author haoge
 */
public class Update {

    private boolean forced;
    private boolean ignore;
    private String updateContent;
    private String updateUrl;
    private int versionCode;
    private String versionName;
    private String md5;

    /**
     * <p>指定是否要求展示忽略此版本更新按钮：
     *
     * <p>此属性为非必须属性。即框架核心操作层并未依赖此属性。此属性只用于提供的默认弹窗通知中：{@link DefaultNeedInstallCreator} 和 {@link DefaultNeedUpdateCreator}
     *
     * @param ignore True代表在弹出通知中进行展示 <b>忽略此版本更新按钮</b>
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * <p>指定是否要求进行强制更新。当设置为强制更新时，将会导致设置的更新策略无效。而直接使用框架内部所提供的{@link ForcedUpdateStrategy}进行更新策略管理
     *
     * @param forced True代表此版本需要进行强制更新
     */
    public void setForced(boolean forced) {
        this.forced = forced;
    }

    /**
     * 设置此次版本更新内容，将在更新弹窗通知中使用
     * @param updateContent 更新内容
     */
    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    /**
     * 设置此次版本的远程更新apk包
     * @param updateUrl 更新包url地址
     */
    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    /**
     * 新版本apk的版本号。此版本号将被用于与本地apk进行版本号比对。判断该版本是否应该被更新. 默认版本号检查器为：{@link DefaultChecker}.
     * @param versionCode apk版本号
     */
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * 新版本的apk的版本名。
     * @param versionName version name
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * 指定下载文件的md5值。用于对下载文件进行检查时使用。
     * @param md5 MD5
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isForced() {
        return forced;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getMd5() {
        return md5;
    }

    @Override
    public String toString() {
        return "Update{" +
                ", forced=" + forced +
                ", updateContent='" + updateContent + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", ignore=" + ignore +
                '}';
    }
}
