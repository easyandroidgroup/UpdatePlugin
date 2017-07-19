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

public class Update {

    /**
     * original data.
     */
    private String original;

    /**
     * indicated whether or not to force update,update dialog should not be disable by back key or touch outside if set true
     */
    private boolean forced;
    /**
     * update content,
     */
    private String updateContent;
    /**
     * update url
     */
    private String updateUrl;
    /**
     * update time
     */
    private long updateTime;
    /**
     * update code
     */
    private int versionCode;
    /**
     * update name
     */
    private String versionName;

    /**
     * indicated whether or not to update this version
     */
    private boolean ignore;

    public Update(String original) {
        this.original = original;
    }

    public boolean isForced() {
        return forced;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return "Update{" +
                "original='" + original + '\'' +
                ", forced=" + forced +
                ", updateContent='" + updateContent + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", updateTime=" + updateTime +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", ignore=" + ignore +
                '}';
    }
}
