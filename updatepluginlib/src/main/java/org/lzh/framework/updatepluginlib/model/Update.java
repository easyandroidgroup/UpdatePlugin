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
