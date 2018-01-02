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
import org.lzh.framework.updatepluginlib.impl.DefaultCheckWorker;
import org.lzh.framework.updatepluginlib.impl.DefaultDownloadNotifier;
import org.lzh.framework.updatepluginlib.impl.DefaultDownloadWorker;
import org.lzh.framework.updatepluginlib.impl.DefaultFileChecker;
import org.lzh.framework.updatepluginlib.impl.DefaultFileCreator;
import org.lzh.framework.updatepluginlib.impl.DefaultInstallNotifier;
import org.lzh.framework.updatepluginlib.impl.DefaultInstallStrategy;
import org.lzh.framework.updatepluginlib.impl.DefaultUpdateChecker;
import org.lzh.framework.updatepluginlib.impl.DefaultUpdateNotifier;
import org.lzh.framework.updatepluginlib.impl.ForcedUpdateStrategy;
import org.lzh.framework.updatepluginlib.impl.WifiFirstStrategy;
import org.lzh.framework.updatepluginlib.model.CheckEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 此类用于提供一些默认使用的更新配置。
 *
 * <p>在进行更新任务时，当{@link UpdateBuilder} 中未设置对应的配置时。将从此配置类中读取默认的配置进行使用
 *
 * @author haoge
 */
public final class UpdateConfig {

    private Class<? extends CheckWorker> checkWorker;
    private Class<? extends DownloadWorker> downloadWorker;
    private CheckEntity entity;
    private UpdateStrategy updateStrategy;
    private CheckNotifier checkNotifier;
    private InstallNotifier installNotifier;
    private DownloadNotifier downloadNotifier;
    private UpdateParser updateParser;
    private FileCreator fileCreator;
    private UpdateChecker updateChecker;
    private FileChecker fileChecker;
    private InstallStrategy installStrategy;
    private ExecutorService executor;
    private CheckCallback checkCallback;
    private DownloadCallback downloadCallback;

    private static UpdateConfig DEFAULT;

    /**
     * 获取一个全局默认的更新配置。正常情况下，使用的即是此默认的更新配置。
     *
     * <p>当使用{@link UpdateBuilder#check()}建立新的更新任务时，则将使用此默认的更新配置进行默认配置提供
     * @return 默认的更新配置。
     */
    public static UpdateConfig getConfig() {
        if (DEFAULT == null) {
            DEFAULT = new UpdateConfig();
        }
        return DEFAULT;
    }

    /**
     * 创建一个新的更新配置提供使用，当有多个需求需要用到更新逻辑时，可使用此方法针对不同的更新逻辑创建不同的更新配置使用：
     * 如插件化远程插件的下载。
     *
     * <p>要使用此方式创建出来的更新配置。需要使用{@link UpdateBuilder#create(UpdateConfig)}进行更新任务创建。
     *
     * @return 新的更新配置。
     */
    public static UpdateConfig createConfig() {
        return new UpdateConfig();
    }

    /**
     * 配置更新api。此方法设置的api是对于只有url数据。请求方式为GET请求时所使用的。
     *
     * <p>请注意：此配置方法与{@link #setCheckEntity(CheckEntity)}互斥。
     *
     * @param url 用于进行检查更新的url地址
     * @return itself
     * @see #setCheckEntity(CheckEntity)
     */
    public UpdateConfig setUrl(String url) {
        this.entity = new CheckEntity().setUrl(url);
        return this;
    }

    /**
     * 配置更新api。此方法是用于针对复杂api的需求进行配置的。本身提供url,method,params。对于其他需要的数据。
     * 可通过继承此{@link CheckEntity}实体类，加入更多数据。并通过{@link #setCheckWorker(Class)}配置对应
     * 的网络任务进行匹配兼容
     * @param entity 更新api数据实体类
     * @return itself
     */
    public UpdateConfig setCheckEntity(CheckEntity entity) {
        this.entity = entity;
        return this;
    }

    /**
     * 配置更新数据检查器。默认使用{@link DefaultUpdateChecker}
     *
     * @param checker 更新检查器。
     * @return itself
     * @see UpdateChecker
     */
    public UpdateConfig setUpdateChecker(UpdateChecker checker) {
        this.updateChecker = checker;
        return this;
    }

    /**
     * 配置更新文件检查器，默认使用{@link DefaultFileChecker}
     * @param checker 文件检查器
     * @return itself
     * @see FileChecker
     */
    public UpdateConfig setFileChecker(FileChecker checker) {
        this.fileChecker = checker;
        return this;
    }

    /**
     * 配置更新api的访问网络任务，默认使用{@link DefaultCheckWorker}
     * @param checkWorker 更新api访问网络任务。
     * @return itself
     * @see CheckWorker
     */
    public UpdateConfig setCheckWorker(Class<? extends CheckWorker> checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    /**
     * 配置apk下载网络任务，默认使用{@link DefaultDownloadWorker}
     * @param downloadWorker 下载网络任务
     * @return itself
     * @see DownloadWorker
     */
    public UpdateConfig setDownloadWorker(Class<? extends DownloadWorker> downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    /**
     * 配置下载回调监听。
     * @param callback 下载回调监听
     * @return itself
     * @see DownloadCallback
     */
    public UpdateConfig setDownloadCallback(DownloadCallback callback) {
        this.downloadCallback = callback;
        return this;
    }

    /**
     * 配置更新检查回调监听
     *
     * @param callback 更新检查回调器
     * @return itself
     * @see CheckCallback
     */
    public UpdateConfig setCheckCallback(CheckCallback callback) {
        this.checkCallback = callback;
        return this;
    }

    /**
     * 配置更新数据解析器。
     * @param updateParser 解析器
     * @return itself
     * @see UpdateParser
     */
    public UpdateConfig setUpdateParser(UpdateParser updateParser) {
        this.updateParser = updateParser;
        return this;
    }

    /**
     * 配置apk下载缓存文件创建器 默认参考{@link DefaultFileCreator}
     * @param fileCreator 文件创建器
     * @return itself
     * @see FileCreator
     */
    public UpdateConfig setFileCreator(FileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    /**
     * 配置下载进度通知创建器 默认参考{@link DefaultDownloadNotifier}
     * @param notifier 下载进度通知创建器
     * @return itself
     * @see DownloadNotifier
     */
    public UpdateConfig setDownloadNotifier(DownloadNotifier notifier) {
        this.downloadNotifier = notifier;
        return this;
    }

    /**
     * 配置启动安装任务前通知创建器 默认参考{@link DefaultInstallNotifier}
     *
     * @param notifier 下载完成后。启动安装前的通知创建器
     * @return itself
     * @see InstallNotifier
     */
    public UpdateConfig setInstallNotifier(InstallNotifier notifier) {
        this.installNotifier = notifier;
        return this;
    }

    /**
     * 配置检查到有更新时的通知创建器 默认参考{@link DefaultUpdateNotifier}
     * @param notifier 通知创建器
     * @return itself
     * @see CheckNotifier
     */
    public UpdateConfig setCheckNotifier(CheckNotifier notifier) {
        this.checkNotifier = notifier;
        return this;
    }

    /**
     * 配置更新策略，默认使用{@link WifiFirstStrategy}, 当更新数据为要求强制更新时。则强制使用{@link ForcedUpdateStrategy}
     * @param strategy 更新策略
     * @return itself
     * @see UpdateStrategy
     * @see WifiFirstStrategy
     * @see ForcedUpdateStrategy
     */
    public UpdateConfig setUpdateStrategy(UpdateStrategy strategy) {
        this.updateStrategy = strategy;
        return this;
    }

    /**
     * 配置安装策略 默认参考 {@link DefaultInstallStrategy}
     *
     * @param installStrategy 安装策略
     * @return itself
     * @see InstallStrategy
     */
    public UpdateConfig setInstallStrategy(InstallStrategy installStrategy) {
        this.installStrategy = installStrategy;
        return this;
    }

    public UpdateStrategy getUpdateStrategy() {
        if (updateStrategy == null) {
            updateStrategy = new WifiFirstStrategy();
        }
        return updateStrategy;
    }

    public CheckEntity getCheckEntity () {
        if (this.entity == null || TextUtils.isEmpty(this.entity.getUrl())) {
            throw new IllegalArgumentException("Do not set setUrl in CheckEntity");
        }
        return this.entity;
    }

    public CheckNotifier getCheckNotifier() {
        if (checkNotifier == null) {
            checkNotifier = new DefaultUpdateNotifier();
        }
        return checkNotifier;
    }

    public InstallNotifier getInstallNotifier() {
        if (installNotifier == null) {
            installNotifier = new DefaultInstallNotifier();
        }
        return installNotifier;
    }

    public UpdateChecker getUpdateChecker() {
        if (updateChecker == null) {
            updateChecker = new DefaultUpdateChecker();
        }
        return updateChecker;
    }

    public FileChecker getFileChecker() {
        if (fileChecker == null) {
            fileChecker = new DefaultFileChecker();
        }
        return fileChecker;
    }

    public DownloadNotifier getDownloadNotifier() {
        if (downloadNotifier == null) {
            downloadNotifier = new DefaultDownloadNotifier();
        }
        return downloadNotifier;
    }

    public UpdateParser getUpdateParser() {
        if (updateParser == null) {
            throw new IllegalStateException("update parser is null");
        }
        return updateParser;
    }

    public Class<? extends CheckWorker> getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = DefaultCheckWorker.class;
        }
        return checkWorker;
    }

    public Class<? extends DownloadWorker> getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = DefaultDownloadWorker.class;
        }
        return downloadWorker;
    }

    public FileCreator getFileCreator() {
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

    public ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(2);
        }
        return executor;
    }

    public CheckCallback getCheckCallback() {
        return checkCallback;
    }

    public DownloadCallback getDownloadCallback() {
        return downloadCallback;
    }


}

