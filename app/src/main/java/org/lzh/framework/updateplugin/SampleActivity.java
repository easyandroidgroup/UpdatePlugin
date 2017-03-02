package org.lzh.framework.updateplugin;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.tbruyelle.rxpermissions.RxPermissions;

import org.lzh.framework.updateplugin.update.AllDialogShowStrategy;
import org.lzh.framework.updateplugin.update.CustomApkFileCreator;
import org.lzh.framework.updateplugin.update.NotificationDownloadCreator;
import org.lzh.framework.updateplugin.update.NotificationInstallCreator;
import org.lzh.framework.updateplugin.update.NotificationUpdateCreator;
import org.lzh.framework.updateplugin.update.OkhttpCheckWorker;
import org.lzh.framework.updateplugin.update.OkhttpDownloadWorker;
import org.lzh.framework.updateplugin.widget.CheckedView;
import org.lzh.framework.updatepluginlib.UpdateBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class SampleActivity extends Activity {
    @BindView(R.id.check_worker) CheckedView updateWorker;                          // 选择使用的检查是否更新接口的网络任务
    @BindView(R.id.file_creator) CheckedView fileCreator;                           // 选择使用的缓存文件路径
    @BindView(R.id.update_strategy) CheckedView updateStrategy;                     // 选择使用的更新策略
    @BindView(R.id.download_notice) CheckedView downloadNotice;                     // 选择使用的文件下载通知
    @BindView(R.id.has_update_notice) CheckedView hasUpdateNotice;                  // 选择使用的检测到有更新时的提示
    @BindView(R.id.download_complete_notice) CheckedView downloadCompleteNotice;    // 选择使用的下载完成后的提示
    @BindView(R.id.download_worker) CheckedView downloadWorker;                     // 选择使用的apk下载网络任务
    boolean isPermissionGrant;// 程序是否被允许持有写入权限
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestStoragePermission();
    }

    /** 请求文件读写权限。*/
    private void requestStoragePermission() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        isPermissionGrant = aBoolean;
                    }
                });
    }

    @OnClick(R.id.start_update)
    void onStartClick () {
        UpdateBuilder builder = UpdateBuilder.create();
        // 根据各项是否选择使用默认配置进行使用更新。
        if (!updateWorker.isDefaultSelected()) {
            builder.checkWorker(new OkhttpCheckWorker());
        }

        if (!hasUpdateNotice.isDefaultSelected()) {
            builder.updateDialogCreator(new NotificationUpdateCreator());
        }

        if (!downloadCompleteNotice.isDefaultSelected()) {
            builder.installDialogCreator(new NotificationInstallCreator());
        }

        if (!fileCreator.isDefaultSelected() && isPermissionGrant) {
            builder.fileCreator(new CustomApkFileCreator());
        }

        if (!updateStrategy.isDefaultSelected()) {
            builder.strategy(new AllDialogShowStrategy());
        }

        if (!downloadNotice.isDefaultSelected()) {
            builder.downloadDialogCreator(new NotificationDownloadCreator());
        }

        if (!downloadWorker.isDefaultSelected()) {
            builder.downloadWorker(new OkhttpDownloadWorker());
        }
        /**
         * 以上为常用的需要定制的功能模块。如果需要更多的定制需求。请参考
         * {@link org.lzh.framework.updatepluginlib.UpdateConfig}
         * 类中所使用的其他模块的默认实现方式。
         * */
        builder.check();
    }
}
