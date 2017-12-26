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
package org.lzh.framework.updatepluginlib;

import org.lzh.framework.updatepluginlib.base.CheckCallback;
import org.lzh.framework.updatepluginlib.base.CheckNotifier;
import org.lzh.framework.updatepluginlib.base.CheckWorker;
import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.base.DownloadNotifier;
import org.lzh.framework.updatepluginlib.base.DownloadWorker;
import org.lzh.framework.updatepluginlib.base.FileChecker;
import org.lzh.framework.updatepluginlib.base.FileCreator;
import org.lzh.framework.updatepluginlib.base.InstallNotifier;
import org.lzh.framework.updatepluginlib.base.InstallStrategy;
import org.lzh.framework.updatepluginlib.base.UpdateChecker;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.base.UpdateStrategy;
import org.lzh.framework.updatepluginlib.flow.Launcher;
import org.lzh.framework.updatepluginlib.model.CheckEntity;

/**
 * 此类用于建立真正的更新任务。每个更新任务对应于一个{@link UpdateBuilder}实例。
 *
 * <p>创建更新任务有两种方式：<br>
 *      1. 通过{@link #create()}进行创建，代表将使用默认提供的全局更新配置。此默认更新配置通过{@link UpdateConfig#getConfig()}进行获取。<br>
 *      2. 通过{@link #create(UpdateConfig)}指定使用某个特殊的更新配置。<br>
 *
 * <p>此Builder中的所有配置项，均在{@link UpdateConfig}中有对应的相同方法名的配置函数。此两者的关系为：
 * 在更新流程中，当Builder中未设置对应的配置，将会使用在{@link UpdateConfig}更新配置中所提供的默认配置进行使用
 *
 * <p>正常启动：调用{@link #check()}进行启动。
 *
 * @author haoge
 */
public class UpdateBuilder {

    private Class<? extends CheckWorker> checkWorker;
    private Class<? extends DownloadWorker> downloadWorker;
    private CheckEntity entity;
    private UpdateStrategy updateStrategy;
    private CheckNotifier updateDialogCreator;
    private InstallNotifier installDialogCreator;
    private DownloadNotifier downloadDialogCreator;
    private UpdateParser jsonParser;
    private FileCreator fileCreator;
    private UpdateChecker updateChecker;
    private FileChecker fileChecker;
    private InstallStrategy installStrategy;
    private UpdateConfig config;
    
    private UpdateBuilder(UpdateConfig config) {
        this.config = config;
    }

    /**
     * 使用默认全局配置进行更新任务创建，默认全局配置可通过{@link UpdateConfig#getConfig()}进行获取
     * @return Builder
     */
    public static UpdateBuilder create() {
        return create(UpdateConfig.getConfig());
    }

    /**
     * 指定该更新任务所使用的更新配置。可通过{@link UpdateConfig#createConfig()}进行新的更新配置创建。
     * @param config 指定使用的更新配置
     * @return Builder
     */
    public static UpdateBuilder create(UpdateConfig config) {
        return new UpdateBuilder(config);
    }

    public UpdateBuilder setUrl(String url) {
        this.entity = new CheckEntity().setUrl(url);
        return this;
    }

    public UpdateBuilder setCheckEntity(CheckEntity entity) {
        this.entity = entity;
        return this;
    }

    public UpdateBuilder setUpdateChecker(UpdateChecker checker) {
        this.updateChecker = checker;
        return this;
    }

    public UpdateBuilder setFileChecker(FileChecker checker) {
        this.fileChecker = checker;
        return this;
    }

    public UpdateBuilder setCheckWorker(Class<? extends CheckWorker> checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    public UpdateBuilder setDownloadWorker(Class<? extends DownloadWorker> downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    public UpdateBuilder setDownloadCallback(DownloadCallback downloadCB) {
        this.config.setDownloadCallback(downloadCB);
        return this;
    }

    public UpdateBuilder setCheckCallback(CheckCallback checkCB) {
        this.config.setCheckCallback(checkCB);
        return this;
    }

    public UpdateBuilder setUpdateParser(UpdateParser jsonParser) {
        this.jsonParser = jsonParser;
        return this;
    }

    public UpdateBuilder setFileCreator(FileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    public UpdateBuilder setDownloadNotifier(DownloadNotifier downloadDialogCreator) {
        this.downloadDialogCreator = downloadDialogCreator;
        return this;
    }

    public UpdateBuilder setInstallNotifier(InstallNotifier installDialogCreator) {
        this.installDialogCreator = installDialogCreator;
        return this;
    }

    public UpdateBuilder setCheckNotifier(CheckNotifier updateDialogCreator) {
        this.updateDialogCreator = updateDialogCreator;
        return this;
    }

    public UpdateBuilder setUpdateStrategy(UpdateStrategy strategy) {
        this.updateStrategy = strategy;
        return this;
    }

    public UpdateBuilder setInstallStrategy(InstallStrategy installStrategy) {
        this.installStrategy = installStrategy;
        return this;
    }

    /**
     * 启动更新任务。可在任意线程进行启动。
     */
    public void check() {
        Launcher.getInstance().launchCheck(this);
    }

    public UpdateStrategy getUpdateStrategy() {
        if (updateStrategy == null) {
            updateStrategy = config.getUpdateStrategy();
        }
        return updateStrategy;
    }

    public CheckEntity getCheckEntity () {
        if (this.entity == null) {
            this.entity = config.getCheckEntity();
        }
        return this.entity;
    }

    public UpdateChecker getUpdateChecker() {
        if (updateChecker == null) {
            updateChecker = config.getUpdateChecker();
        }
        return updateChecker;
    }

    public FileChecker getFileChecker() {
        return fileChecker != null ? fileChecker : config.getFileChecker();
    }

    public CheckNotifier getUpdateDialogCreator() {
        if (updateDialogCreator == null) {
            updateDialogCreator = config.getUpdateDialogCreator();
        }
        return updateDialogCreator;
    }

    public InstallNotifier getInstallDialogCreator() {
        if (installDialogCreator == null) {
            installDialogCreator = config.getInstallDialogCreator();
        }
        return installDialogCreator;
    }

    public DownloadNotifier getDownloadDialogCreator() {
        if (downloadDialogCreator == null) {
            downloadDialogCreator = config.getDownloadDialogCreator();
        }
        return downloadDialogCreator;
    }

    public UpdateParser getJsonParser() {
        if (jsonParser == null) {
            jsonParser = config.getJsonParser();
        }
        return jsonParser;
    }

    public Class<? extends CheckWorker> getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = config.getCheckWorker();
        }
        return checkWorker;
    }

    public Class<? extends DownloadWorker> getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = config.getDownloadWorker();
        }
        return downloadWorker;
    }

    public FileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = config.getFileCreator();
        }
        return fileCreator;
    }

    public CheckCallback getCheckCB() {
        return config.getCheckCB();
    }

    public DownloadCallback getDownloadCB() {
        return config.getDownloadCB();
    }

    public InstallStrategy getInstallStrategy() {
        if (installStrategy == null) {
            installStrategy = config.getInstallStrategy();
        }
        return installStrategy;
    }

    public final UpdateConfig getConfig() {
        return config;
    }

}
