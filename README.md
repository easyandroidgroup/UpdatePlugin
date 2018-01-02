# UpdatePlugin [![](https://jitpack.io/v/yjfnypeu/UpdatePlugin.svg)](https://jitpack.io/#yjfnypeu/UpdatePlugin)   <a href="http://www.methodscount.com/?lib=org.lzh.nonview.updateplugin%3AUpdatePlugin%3A0.7.1"><img src="https://img.shields.io/badge/Methods and size-402 | 48 KB-e91e63.svg"/></a>


可任意定制的app更新组件。

### 原理
UpdatePlugin主要基于对整个更新流程的梳理，针对更新流程中可能的被用户需要定制的节点。提供对应的定制接口出来提供用户进行各种定制；

为了方便用户直接使用，对于更新流程中非用户必须定制的接口。框架也对各个节点实现了一套自身默认的定制接口。做到拿来即用的。

### 特性

框架提供了最大的可定制性。轻松应付各种奇葩需求：

**对于各种提供的可定制接口任务。请参考[更新配置说明](https://github.com/yjfnypeu/UpdatePlugin/wiki/Configurations-Explains)**

基于以上配置。框架提供了以下特性：

- 支持断点下载
- 支持Android 7.+ 应用安装方式
- 支持接入任意更新api
- 支持强制更新、忽略此版本更新逻辑
- 支持对apk进行安全检查，防止类似DNS劫持后被替换更新apk包的情况
- 支持指定apk下载文件地址
- 支持定制接入更新网络任务。适配更多网络使用场景
- 支持定制各种更新策略。比如默认使用的WIFI下默认直接下载后再通知更新，非WIFI下先通知更新再启动下载等。
- 支持定制安装策略。比如在插件化、热修复环境下进行定制使用
- 支持任意定制更新流程中的各种通知：检查到有更新时的通知、下载时的进度条通知、下载完成后安装之前的通知。

### 引入方式：

由于各种原因，现将依赖仓库地址，从jCenter迁移到JitPack，请升级新版使用时注意一下：

加入JitPack仓库依赖。
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
- 在要使用的项目的build.gradle中。添加依赖：

LastestVersion= [![](https://jitpack.io/v/yjfnypeu/UpdatePlugin.svg)](https://jitpack.io/#yjfnypeu/UpdatePlugin)

```
dependencies {
    ...
    compile "com.github.yjfnypeu:UpdatePlugin:$LastestVersion"
}
```


### 效果展示

请通过下载此[demo.apk](https://raw.githubusercontent.com/yjfnypeu/UpdatePlugin/master/screenshots/app-debug.apk)进行体验

### 流程图

![flow chart](./screenshots/flow_chart.png)

### 使用方式：
[请访问wiki查看具体使用方法](https://github.com/yjfnypeu/UpdatePlugin/wiki)

### 联系作者
email: 470368500@qq.com

<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=99e758d20823a18049a06131b6d1b2722878720a437b4690e238bce43aceb5e1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="安卓交流会所" title="安卓交流会所"></a>

或者手动加入QQ群: 108895031
