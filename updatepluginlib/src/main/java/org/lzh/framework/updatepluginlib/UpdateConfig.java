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

import android.text.TextUtils;

import org.lzh.framework.updatepluginlib.business.DefaultDownloadWorker;
import org.lzh.framework.updatepluginlib.business.DefaultUpdateWorker;
import org.lzh.framework.updatepluginlib.business.DownloadWorker;
import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultFileChecker;
import org.lzh.framework.updatepluginlib.creator.DefaultFileCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedDownloadCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedInstallCreator;
import org.lzh.framework.updatepluginlib.creator.DefaultNeedUpdateCreator;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.creator.DownloadCreator;
import org.lzh.framework.updatepluginlib.creator.FileChecker;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.DefaultChecker;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.DefaultInstallStrategy;
import org.lzh.framework.updatepluginlib.strategy.InstallStrategy;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.strategy.WifiFirstStrategy;

/**
 * 此类用于提供一些默认使用的更新配置。在进行更新任务时，当{@link UpdateBuilder} 中未设置对应的配置时。
 * 将从此配置类中读取默认的
 */
public class UpdateConfig {

    private UpdateWorker checkWorker;
    private DownloadWorker downloadWorker;
    private UpdateCheckCB checkCB;
    private UpdateDownloadCB downloadCB;
    private CheckEntity entity;
    private UpdateStrategy strategy;
    private DialogCreator updateDialogCreator;
    private InstallCreator installDialogCreator;
    private DownloadCreator downloadDialogCreator;
    private UpdateParser jsonParser;
    private ApkFileCreator fileCreator;
    private UpdateChecker updateChecker;

    private FileChecker fileChecker;

    private InstallStrategy installStrategy;

    private static UpdateConfig DEFAULT;

    public static UpdateConfig getConfig() {
        if (DEFAULT == null) {
            DEFAULT = new UpdateConfig();
        }
        return DEFAULT;
    }

    public static UpdateConfig createConfig() {
        return new UpdateConfig();
    }

    public UpdateConfig url(String url) {
        this.entity = new CheckEntity().setUrl(url);
        return this;
    }

    public UpdateConfig checkEntity (CheckEntity entity) {
        this.entity = entity;
        return this;
    }

    public UpdateConfig updateChecker(UpdateChecker checker) {
        this.updateChecker = checker;
        return this;
    }

    public UpdateConfig fileChecker (FileChecker checker) {
        this.fileChecker = checker;
        return this;
    }

    public UpdateConfig checkWorker(UpdateWorker checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    public UpdateConfig downloadWorker(DownloadWorker downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    public UpdateConfig downloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
        return this;
    }

    public UpdateConfig checkCB (UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
        return this;
    }

    public UpdateConfig jsonParser (UpdateParser jsonParser) {
        this.jsonParser = jsonParser;
        return this;
    }

    public UpdateConfig fileCreator (ApkFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    public UpdateConfig downloadDialogCreator (DownloadCreator downloadDialogCreator) {
        this.downloadDialogCreator = downloadDialogCreator;
        return this;
    }

    public UpdateConfig installDialogCreator (InstallCreator installDialogCreator) {
        this.installDialogCreator = installDialogCreator;
        return this;
    }

    public UpdateConfig updateDialogCreator(DialogCreator updateDialogCreator) {
        this.updateDialogCreator = updateDialogCreator;
        return this;
    }

    public UpdateConfig strategy(UpdateStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public UpdateConfig installStrategy(InstallStrategy installStrategy) {
        this.installStrategy = installStrategy;
        return this;
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = new WifiFirstStrategy();
        }
        return strategy;
    }

    public CheckEntity getCheckEntity () {
        if (this.entity == null || TextUtils.isEmpty(this.entity.getUrl())) {
            throw new IllegalArgumentException("Do not set url in CheckEntity");
        }
        return this.entity;
    }

    public DialogCreator getUpdateDialogCreator() {
        if (updateDialogCreator == null) {
            updateDialogCreator = new DefaultNeedUpdateCreator();
        }
        return updateDialogCreator;
    }

    public InstallCreator getInstallDialogCreator() {
        if (installDialogCreator == null) {
            installDialogCreator = new DefaultNeedInstallCreator();
        }
        return installDialogCreator;
    }

    public UpdateChecker getUpdateChecker() {
        if (updateChecker == null) {
            updateChecker = new DefaultChecker();
        }
        return updateChecker;
    }

    public FileChecker getFileChecker() {
        if (fileChecker == null) {
            fileChecker = new DefaultFileChecker();
        }
        return fileChecker;
    }

    public DownloadCreator getDownloadDialogCreator() {
        if (downloadDialogCreator == null) {
            downloadDialogCreator = new DefaultNeedDownloadCreator();
        }
        return downloadDialogCreator;
    }

    public UpdateParser getJsonParser() {
        if (jsonParser == null) {
            throw new IllegalStateException("update parser is null");
        }
        return jsonParser;
    }

    public UpdateWorker getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = new DefaultUpdateWorker();
        }
        return checkWorker;
    }

    public DownloadWorker getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = new DefaultDownloadWorker();
        }
        return downloadWorker;
    }

    public ApkFileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = new DefaultFileCreator();
        }
        return fileCreator;
    }

    public InstallStrategy getInstallStrategy() {
        if (installStrategy == null) {
            installStrategy = new DefaultInstallStrategy();
        }
        return installStrategy;
    }

    public UpdateCheckCB getCheckCB() {
        return checkCB;
    }

    public UpdateDownloadCB getDownloadCB() {
        return downloadCB;
    }
}

