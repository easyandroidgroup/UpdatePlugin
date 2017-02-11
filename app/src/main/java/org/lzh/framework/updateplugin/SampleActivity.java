package org.lzh.framework.updateplugin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.lzh.framework.updateplugin.update.AllDialogShowStrategy;
import org.lzh.framework.updateplugin.update.CustomApkFileCreator;
import org.lzh.framework.updateplugin.update.CustomDownloadWorker;
import org.lzh.framework.updateplugin.update.CustomNeedDownloadCreator;
import org.lzh.framework.updateplugin.update.CustomNeedInstallCreator;
import org.lzh.framework.updateplugin.update.CustomNeedUpdateCreator;
import org.lzh.framework.updateplugin.update.CustomUpdateChecker;
import org.lzh.framework.updateplugin.update.CustomUpdateWorker;
import org.lzh.framework.updateplugin.update.NotificationDownloadCreator;
import org.lzh.framework.updatepluginlib.UpdateBuilder;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SampleActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.default_update).setOnClickListener(this);
        findViewById(R.id.custom_task).setOnClickListener(this);
        findViewById(R.id.custom_checker).setOnClickListener(this);
        findViewById(R.id.custom_dialog).setOnClickListener(this);
        findViewById(R.id.custom_file_creator).setOnClickListener(this);
        findViewById(R.id.custom_strategy).setOnClickListener(this);
        findViewById(R.id.custom_activity_replace).setOnClickListener(this);
        findViewById(R.id.upgrade_in_back_thread).setOnClickListener(this);

        SampleActivityPermissionsDispatcher.requestPermissionWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestPermission() {

    }

    // 使用默认配置进行更新
    void useDefaultUpdate() {
        UpdateBuilder.create()
                .downloadDialogCreator(new NotificationDownloadCreator())
                .check();
    }

    // 使用自定义网络任务进行更新。
    void useCustomTask () {
        UpdateBuilder.create()
                .checkWorker(new CustomUpdateWorker())// 设置自定义的更新任务
                .downloadWorker(new CustomDownloadWorker()) // 设置自定义的下载任务
                .check();
    }

    // 使用自定义的更新检查器。
    void useCustomChecker () {
        UpdateBuilder.create()
                .updateChecker(new CustomUpdateChecker())
                .strategy(new AllDialogShowStrategy())
                .check();
    }

    // 使用自定义的Dialog作为显示效果
    void useCustomDialog () {
        UpdateBuilder.create()
                .updateDialogCreator(new CustomNeedUpdateCreator())
                .downloadDialogCreator(new CustomNeedDownloadCreator())
                .installDialogCreator(new CustomNeedInstallCreator())
                .check();
    }

    // 使用自定义更新策略
    void useCustomUpdateStrataty () {
        UpdateBuilder.create()
                .strategy(new AllDialogShowStrategy())
                .check();
    }

    // 使用自定义下载文件创建器指定下载文件名
    void useCustomApkFileCreator () {
        UpdateBuilder.create()
                .fileCreator(new CustomApkFileCreator())
                .check();
    }

    // 当前页面进行检查更新.同时跳转到AnotherActivity,使用replaceCB替换需要显示Dialog时的Activity实例
    void useCustomReplace() {
        UpdateBuilder builder = UpdateBuilder.create()
                .strategy(new AllDialogShowStrategy());
        builder.check();
        startActivity(new Intent(this,AnotherActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.default_update:
                useDefaultUpdate();
                break;
            case R.id.custom_checker:
                useCustomChecker();
                break;
            case R.id.custom_dialog:
                useCustomDialog();
                break;
            case R.id.custom_file_creator:
                useCustomApkFileCreator();
                break;
            case R.id.custom_strategy:
                useCustomUpdateStrataty();
                break;
            case R.id.custom_task:
                useCustomTask();
                break;
            case R.id.custom_activity_replace:
                useCustomReplace();
                break;
            case R.id.upgrade_in_back_thread:
                upgradeOnBackThread();
                break;
        }
    }

    private void upgradeOnBackThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateBuilder.create().check();
            }
        }).start();
    }
}
