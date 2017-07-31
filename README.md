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
    compile "org.lzh.nonview.updateplugin:UpdatePlugin:LastestVersion"
}
```

### 效果展示

- 使用默认更新：


### 使用方式：

[请访问wiki查看具体使用方法](https://github.com/yjfnypeu/UpdatePlugin/wiki)

流程图中的UpdateWorker/UpdateParser/UpdateChecker等均为框架提供的对外接口,如需要对框架根据自己的业务需要进行定制,均可实现这些接口并设置到UpdateConfig或者UpdateBuilder中进行定制使用,

### 联系作者
email: 470368500@qq.com

<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=99e758d20823a18049a06131b6d1b2722878720a437b4690e238bce43aceb5e1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="安卓交流会所" title="安卓交流会所"></a>

或者手动加入QQ群: 108895031
