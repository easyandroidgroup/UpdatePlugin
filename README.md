# UpdatePlugin  ![ci](https://travis-ci.org/yjfnypeu/UpdatePlugin.svg?branch=master)  [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/UpdatePlugin/images/download.svg) ](https://bintray.com/yjfnypeu/maven/UpdatePlugin/_latestVersion)   <a href="http://www.methodscount.com/?lib=org.lzh.nonview.updateplugin%3AUpdatePlugin%3A0.7.1"><img src="https://img.shields.io/badge/Methods and size-402 | 48 KB-e91e63.svg"/></a>


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

### 效果展示

- 使用默认更新：


### 使用方式：

[查看wiki](https://github.com/yjfnypeu/UpdatePlugin/wiki)

### 流程图
![](./screenshots/updatePlugin.png)

流程图中的UpdateWorker/UpdateParser/UpdateChecker等均为框架提供的对外接口,如需要对框架根据自己的业务需要进行定制,均可实现这些接口并设置到UpdateConfig或者UpdateBuilder中进行定制使用,

### 更新日志：

- 2.0
```
新增一个ActivityManager.用于在框架需要弹窗提示时获取当前栈顶的Activity进行Dialog创建
最低版本支持提高到14.得益于新机制，启动检查更新任务时不用手动传递Activity且可以在任意线程进行启动
移除原有的用于在更新进程中需要显示Dialog时提供的替换Activity功能
完善UpdateCheckCB回调
优化代码
```

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