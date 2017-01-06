#UpdatePlugin  ![ci](https://travis-ci.org/yjfnypeu/UpdatePlugin.svg?branch=master)  [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/UpdatePlugin/images/download.svg) ](https://bintray.com/yjfnypeu/maven/UpdatePlugin/_latestVersion)   <a href="http://www.methodscount.com/?lib=org.lzh.nonview.updateplugin%3AUpdatePlugin%3A0.7.1"><img src="https://img.shields.io/badge/Methods and size-402 | 48 KB-e91e63.svg"/></a>


可任意定制的app更新组件。

### 引入方式：
加入jcenter依赖。
```
allprojects {
    repositories {
        jcenter()
    }
}
```
- 在要使用的项目的build.gradle中。添加依赖：

LastestVersion= [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/UpdatePlugin/images/download.svg) ](https://bintray.com/yjfnypeu/maven/UpdatePlugin/_latestVersion)

```
dependencies {
    ...
    compile "org.lzh.nonview.updateplugin:UpdatePlugin:$LastestVersion"
}
```

###使用方式：

- 首先。在使用前对其进行配置

```
// 建议在Application中进行配置。
// UpdateConfig为全局配置。当在其他页面中。使用UpdateBuilder进行检查更新时。
// 对于没传的参数，会默认使用UpdateConfig中的全局配置
UpdateConfig.getConfig()
            // url 与 checkEntity方法可任选一种填写，且至少必填一种。
            // 数据更新接口数据，此时默认为使用GET请求
            .url(url)
            // 同url方法。CheckEntity方法可填写url,params,method。可在此设置为post请求
            .checkEntity(checkEntity)
            // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
            .jsonParser(new UpdateParser() {
                @Override
                public Update parse(String response) {
                    // 此处根据上面url接口返回的数据response进行update类组装。框架内部会使用此
                    // 组装的update实例判断是否需要更新以做进一步工作
                    return update;
                }
            })
```

- 在要进行检查更新的地方，使用UpdateBuilder类进行检查

```
UpdateBuilder.create().check(MainActivity.this);
```

OK。最简单的用法就这么点。当然。如果需要查看其他配置请查看[详细配置说明](./Usage.md)

###流程图
![](./screenshots/updatePlugin.png)

流程图中的UpdateWorker/UpdateParser/UpdateChecker等均为框架提供的对外接口,如需要对框架根据自己的业务需要进行定制,均可实现这些接口并设置到UpdateConfig或者UpdateBuilder中进行定制使用,

###更新日志：
- 1.1.0
```
修复多个项目集成使用时安装时出现INSTALL_FAILED_CONFLICTING_PROVIDER错误
```

- 1.0
```
兼容Android 7.0+
最低版本兼容到8
```
- 0.9
```
优化apk下载速度。
```
- 0.8
```
添加InstallChecker接口。用于部分场景下能方便的对apk安装前进行预校验
优化自带文件下载器。修复由于接口不支持断点下载时。对上一次完整下载后再次下载时验证失败导致的重新下载apk问题
```

###联系作者
email: 470368500@qq.com
QQ群: 108895031