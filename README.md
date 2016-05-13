可任意定制的app更新组件。

引入方式：

- 该框架放置于jitPack上。所以需要在根目录中添加jitPack库：
```
allprojects {
    repositories {
        ...
        maven { url "https://www.jitpack.io" }
    }
}
```
- 在要使用的项目的build.gradle中。添加依赖：
```
dependencies {
    ...
    compile 'com.github.yjfnypeu:UpdatePlugin:0.2'
}
```

使用方式：

- 首先。在使用前对其进行配置

```
// 建议在Application中进行配置。
// UpdateConfig为全局配置。当在其他页面中。使用UpdateBuilder进行检查更新时。
// 对于没传的参数，会默认使用UpdateConfig中的全局配置
UpdateConfig.getConfig()
            // 必填：数据更新接口
            .url(url)
            // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
            .jsonParser(new UpdateParser() {
                @Override
                public Update parse(String response) {
                    // 此处根据上面url接口返回的数据response进行update类组装。
                    return update;
                }
            })
```

- 在要进行检查更新的地方，使用UpdateBuilder类进行检查

```
UpdateBuilder.create().check(MainActivity.this);
```

OK。最简单的用法就这么点。当然。既然是可任意配置，那么下面来说说怎么根据自己的需要来配置出属于自己的更新组件

配置分为全局配置与局部配置:

- 全局配置在UpdateConfig类中。如上面的url,jsonParser就是全局配置的其中两个。
- 局部配置在UpdateBuilder类中。UpdateBuilder类中集成了UpdateConfig类中的全部配置。当使用UpdateBuilder进行check更新的时候。当没对此builder进行对应的配置设置的时候。会自动使用UpdateConfig中的配置

下面来看看除了以上所说的url与jsonParser外都支持哪些配置。

**ps:以下配置均为不必要配置。若不配置则使用框架内部默认配置**

- 配置检查更新时的回调。

```
checkCB(new UpdateCheckCB() {
    @Override
    public void hasUpdate(Update update) {
        // 有新版本APK更新的回调
    }

    @Override
    public void noUpdate() {
        // 没有新版本的回调
    }

    @Override
    public void onCheckError(int i, String s) {
        // 检查错误的回调
    }
    
    @Override 
    public void onUserCancel() {
        // 用于取消更新时的回调
    }
})
```

- 新版本APK下载时的回调

```
downloadCB(new UpdateDownloadCB() {
    @Override
    public void onUpdateStart() {
        // 下载开始 
    }
    
    @Override
    public void onUpdateComplete(File file) {
        // 下载完成 
    }
    
    @Override
    public void onUpdateProgress(long current, long total) {
        // 下载进度 
    }
    
    @Override
    public void onUpdateError(int code, String errorMsg) {
        // 下载apk错误 
    }
})
```

- 自定义更新接口的访问任务，默认参考：[DefaultUpdateWorker.java](https://github.com/yjfnypeu/UpdatePlugin/blob/master/updatepluginlib/src/main/java/org/lzh/framework/updatepluginlib/business/DefaultUpdateWorker.java)

```
checkWorker(new UpdateWorker() {

    @Override
    protected String check(String url) throws Exception {
        // 此处运行于子线程。在此进行更新接口访问 
        return null;
    }
})
```

- 自定义文件下载接口的访问任务。默认参考：[DefaultDownloadWorker](https://github.com/yjfnypeu/UpdatePlugin/blob/master/updatepluginlib/src/main/java/org/lzh/framework/updatepluginlib/business/DefaultDownloadWorker.java)

```
downloadWorker(new DownloadWorker() {
    @Override
    protected void download(String url, File file) throws Exception {
        // TODO: 2016/5/11 此处运行于子线程，在此进行文件下载任务
    }
})
```

- 自定义下载文件缓存,默认参考：[DefaultFileCreator](https://github.com/yjfnypeu/UpdatePlugin/blob/master/updatepluginlib/src/main/java/org/lzh/framework/updatepluginlib/creator/DefaultFileCreator.java)

```
.fileCreator(new ApkFileCreator() {
    @Override
    public File create(String versionName) {
        // versionName 为解析的Update实例中的update_url数据。在这里可自定义下载文件缓存路径及文件名。放置于File中
        return null;
    }
})
```

- 自定义更新策略，默认WIFI下自动下载更新，参考：[WifiFirstStrategy](https://github.com/yjfnypeu/UpdatePlugin/blob/master/updatepluginlib/src/main/java/org/lzh/framework/updatepluginlib/strategy/WifiFirstStrategy.java)

```
strategy(new UpdateStrategy() {
    @Override
    public boolean isShowUpdateDialog(Update update) {
        // 是否在检查到有新版本更新时展示Dialog。
        return false;
    }

    @Override
    public boolean isAutoInstall() {
        // 下载完成后，是否自动更新。此属性与是否isShowInstallDialog互斥
        // 当isShowInstallDialog为true时。此条无效
        return false;
    }

    @Override
    public boolean isShowInstallDialog() {
        // 下载完成后。是否显示提示安装的Dialog
        return false;
    }

    @Override
    public boolean isShowDownloadDialog() {
        // 在APK下载时。是否显示下载进度的Dialog
        return false;
    }
})
```

- 自定义检查出更新后显示的Dialog，默认参考：[DefaultNeedUpdateCreator](https://github.com/yjfnypeu/UpdatePlugin/blob/master/updatepluginlib/src/main/java/org/lzh/framework/updatepluginlib/creator/DefaultNeedUpdateCreator.java)

```
updateDialogCreator(new DialogCreator() {
    @Override
    public Dialog create(Update update, Activity activity, UpdateBuilder updateBuilder) {
        // 此处为检查出有新版本需要更新时的回调。运行于主线程，在此进行更新Dialog的创建 
        // 对于用户自定义的Dialog。用户可自行在此更新update中的数据对Dialog进行展示。
        // 在用户需要立即更新时。调用此类中的sendDownloadRequest(update,activity);
        // 在用户需要取消更新时。调用此类中的sendUserCancel();
        return dialog;
    }
})
```

- 自定义下载时的进度条Dialog,默认参考：[DefaultNeedDownloadCreator](https://github.com/yjfnypeu/UpdatePlugin/blob/master/updatepluginlib/src/main/java/org/lzh/framework/updatepluginlib/creator/DefaultNeedDownloadCreator.java)

```
.downloadDialogCreator(new DownloadCreator() {
    @Override
    public UpdateDownloadCB create(Update update, Activity activity) {
        // 此处为正在下载APK时的回调。运行于主线程。在此进行Dialog自定义与显示操作。
        // 需要在此创建并返回一个UpdateDownloadCB回调。用于对Dialog进行更新。
        return cb;
    }
})
```

- 自定义下载完成后。显示的Dialog,默认参考[DefaultNeedInstallCreator](https://github.com/yjfnypeu/UpdatePlugin/blob/master/updatepluginlib/src/main/java/org/lzh/framework/updatepluginlib/creator/DefaultNeedInstallCreator.java)

```
installDialogCreator(new InstallCreator() {
    @Override
    public Dialog create(Update update, String s, Activity activity) {
        // 此处为下载APK完成后的回调。运行于主线程。在此创建Dialog
        // 在用户需要立即更新时。调用InstallUtil.installApk(activity,path);
        // 在用户需要取消更新时。调用此类中的sendUserCancel();
        return dialog;
    }
})
```

最后举个例子来对更新策略做个说明：

```
public class MyApplication extends Application {
    private String apkFile = "http://apk.hiapk.com/web/api.do?qt=8051&id=721";

    @Override
    public void onCreate() {
        super.onCreate();
        // Application中对Config进行配置
        UpdateConfig.getConfig()
                .url("http://www.baidu.com")// 随便模拟的一个网络接口。
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) {
                        // 此处模拟一个Update对象，传入接口返回的原始数据进去保存。
                        // 若用户需要自定义的时候。对于有额外参数。可从中获取并定制。
                        Update update = new Update(response);
                        // 此apk包的更新时间
                        update.setUpdateTime(System.currentTimeMillis());
                        // 此apk包的下载地址
                        update.setUpdateUrl(apkFile);
                        // 此apk包的版本号
                        update.setVersionCode(2);
                        // 此apk包的版本名称
                        update.setVersionName("2.0");
                        // 此apk包的更新内容
                        update.setUpdateContent("测试更新");
                        // 此apk包是否为强制更新
                        update.setForced(true);
                        // 是否忽略此次版本更新
                        update.setIgnore(false);
                        return update;
                    }
                })
                // 此参数可不配置。配置在此作为全局的更新接口回调通知
                .checkCB(new EmptyCheckCB() {

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        Toast.makeText(MyApplication.this, "更新失败：code:" + code + ",errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUserCancel() {
                        Toast.makeText(MyApplication.this, "用户取消更新", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void noUpdate() {
                        Toast.makeText(MyApplication.this, "无更新", Toast.LENGTH_SHORT).show();
                    }
                })
    }
}
```

接下来在需要进行更新的Activity中进行更新检查

```
UpdateBuilder.create().check(MainActivity.this);
```

此处使用默认的更新策略：WIFI下自动更新。流量环境下提示再下载提示更新：

![default_strategy](https://raw.githubusercontent.com/yjfnypeu/UpdatePlugin/master/screenshots/default_strategy.gif)

再使用自定义策略：

```
UpdateBuilder.create()
    .strategy(new UpdateStrategy() {
        @Override
        public boolean isShowUpdateDialog(Update update) {
            // 有新更新直接展示
            return true;
        }

        @Override
        public boolean isAutoInstall() {
            return false;
        }

        @Override
        public boolean isShowInstallDialog() {
            // 展示下载完成安装提示
            return true;
        }

        @Override
        public boolean isShowDownloadDialog() {
            // 展示下载进度
            return true;
        }
    })
    .check(MainActivity.this);
```

![default_all_dialog_show.gif](https://raw.githubusercontent.com/yjfnypeu/UpdatePlugin/master/screenshots/default_all_dialog_show.gif)

再来一个。直接下载完成后。自动弹出安装页面

```
UpdateBuilder.create()
                        .strategy(new UpdateStrategy() {
                            @Override
                            public boolean isShowUpdateDialog(Update update) {
                                return false;
                            }

                            @Override
                            public boolean isAutoInstall() {
                                return true;
                            }

                            @Override
                            public boolean isShowInstallDialog() {
                                return false;
                            }

                            @Override
                            public boolean isShowDownloadDialog() {
                                return false;
                            }
                        })
                        .check(MainActivity.this);
```

![default_auto_install.gif](https://raw.githubusercontent.com/yjfnypeu/UpdatePlugin/master/screenshots/default_auto_install.gif)