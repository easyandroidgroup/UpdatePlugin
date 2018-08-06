package org.lzh.framework.updateplugin;

import android.app.Application;

import org.json.JSONObject;
import org.lzh.framework.updateplugin.widget.ToastTool;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * @author Administrator
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // UpdateConfig为全局配置。当在其他页面中。使用UpdateBuilder进行检查更新时。
        // 对于没传的参数，会默认使用UpdateConfig中的全局配置
        ToastTool.init(this);
        UpdateConfig.getConfig()
                // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
                .setUrl("https://raw.githubusercontent.com/yjfnypeu/UpdatePlugin/master/update.json")
//                .setCheckEntity(new CheckEntity().setMethod(HttpMethod.GET).setUrl("http://www.baidu.com"))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .setUpdateParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) throws Exception{
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                        JSONObject object = new JSONObject(response);
                        Update update = new Update();
                        // 此apk包的下载地址
                        update.setUpdateUrl(object.optString("update_url"));
                        // 此apk包的版本号
                        update.setVersionCode(object.optInt("update_ver_code"));
                        // 此apk包的版本名称
                        update.setVersionName(object.optString("update_ver_name"));
                        // 此apk包的更新内容
                        update.setUpdateContent(object.optString("update_content"));
                        // 此apk包是否为强制更新
                        update.setForced(false);
                        // 是否显示忽略此次版本更新按钮
                        update.setIgnore(object.optBoolean("ignore_able",false));
                        return update;
                    }
                })
                // TODO: 2016/5/11 除了以上两个参数为必填。以下的参数均为非必填项。
                // 检查更新接口是否有新版本更新的回调。
//                .setCheckCallback(callback)
                // apk下载的回调
//                .setDownloadCallback(callback)
                // 自定义更新检查器。
                /*.setUpdateChecker(new UpdateChecker() {
                    @Override
                    public boolean check(Update update) {
//                           此处根据上面jsonParser解析出的update对象来判断是否此update代表的
//                           版本应该被更新。返回true为需要更新。返回false代表不需要更新
                        return false;
                    }
                })
                 // 自定义更新接口的访问任务
                .setCheckWorker(new CheckWorker() {
                    @Override
                    protected String check(CheckEntity setUrl) throws Exception {
                        // TODO: 2016/5/11 此处运行于子线程。在此进行更新接口访问
                        return null;
                    }
                })
                // 自定义apk下载任务
                .setDownloadWorker(new DownloadWorker() {
                    @Override
                    protected void download(String setUrl, File file) throws Exception {
                        // TODO: 2016/5/11 此处运行于子线程，在此进行文件下载任务
                    }
                })
                // 自定义下载文件缓存,默认下载至系统自带的缓存目录下
                .setFileCreator(new FileCreator() {
                    @Override
                    public File create(String versionName) {
                        // TODO: 2016/5/11 versionName 为解析的Update实例中的update_url数据。在些可自定义下载文件缓存路径及文件名。放置于File中
                        return null;
                    }
                })
                // 自定义更新策略，默认WIFI下自动下载更新
                .setUpdateStrategy(new UpdateStrategy() {
                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        // 是否在检查到有新版本更新时展示Dialog。
                        return false;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        // 是否自动更新.当为自动更新时。代表下载成功后不通知用户。直接调起安装。
                        return false;
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        // 在APK下载时。是否显示下载进度的Dialog
                        return false;
                    }
                })
                // 自定义检查出更新后显示的Dialog，
                .setCheckNotifier(new CheckNotifier() {
                    @Override
                    public Dialog create(Update update, Activity context) {
                        // TODO: 2016/5/11 此处为检查出有新版本需要更新时的回调。运行于主线程，在此进行更新Dialog的创建
                        return null;
                    }
                })
                // 自定义下载时的进度条Dialog
                .setDownloadNotifier(new DownloadNotifier() {
                    @Override
                    public DownloadCallback create(Update update, Activity activity) {
                        // TODO: 2016/5/11 此处为正在下载APK时的回调。运行于主线程。在此进行Dialog自定义与显示操作。
                        // TODO: 2016/5/11 需要在此创建并返回一个UpdateDownloadCB回调。用于对Dialog进行更新。
                        return null;
                    }
                })
                // 自定义下载完成后。显示的Dialog
                .setInstallNotifier(new InstallNotifier() {
                    @Override
                    public Dialog create(Update update, String s, Activity activity) {
                        // TODO: 2016/5/11 此处为下载APK完成后的回调。运行于主线程。在此创建Dialog
                        return null;
                    }
                })*/;

    }
}
