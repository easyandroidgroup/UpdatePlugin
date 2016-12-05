package org.lzh.framework.updateplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.lzh.framework.updateplugin.update.AllDialogShowStrategy;
import org.lzh.framework.updateplugin.update.CustomActivityReplaceCB;
import org.lzh.framework.updateplugin.update.CustomApkFileCreator;
import org.lzh.framework.updateplugin.update.CustomDownloadWorker;
import org.lzh.framework.updateplugin.update.CustomNeedDownloadCreator;
import org.lzh.framework.updateplugin.update.CustomNeedInstallCreator;
import org.lzh.framework.updateplugin.update.CustomNeedUpdateCreator;
import org.lzh.framework.updateplugin.update.CustomUpdateChecker;
import org.lzh.framework.updateplugin.update.CustomUpdateWorker;
import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.creator.InstallChecker;
import org.lzh.framework.updatepluginlib.model.Update;

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
    }

    // 使用默认配置进行更新
    void useDefaultUpdate() {
        UpdateBuilder.create().check(this);
    }

    // 使用自定义网络任务进行更新。
    void useCustomTask () {
        UpdateBuilder.create()
                .checkWorker(new CustomUpdateWorker())// 设置自定义的更新任务
                .downloadWorker(new CustomDownloadWorker()) // 设置自定义的下载任务
                .check(this);
    }

    // 使用自定义的更新检查器。
    void useCustomChecker () {
        UpdateBuilder.create()
                .updateChecker(new CustomUpdateChecker())
                .strategy(new AllDialogShowStrategy())
                .check(this);
    }

    // 使用自定义的Dialog作为显示效果
    void useCustomDialog () {
        UpdateBuilder.create()
                .updateDialogCreator(new CustomNeedUpdateCreator())
                .downloadDialogCreator(new CustomNeedDownloadCreator())
                .installDialogCreator(new CustomNeedInstallCreator())
                .check(this);
    }

    // 使用自定义更新策略
    void useCustomUpdateStrataty () {
        UpdateBuilder.create()
                .strategy(new AllDialogShowStrategy())
                .check(this);
    }

    // 使用自定义下载文件创建器指定下载文件名
    void useCustomApkFileCreator () {
        UpdateBuilder.create()
                .fileCreator(new CustomApkFileCreator())
                .check(this);
    }

    // 当前页面进行检查更新.同时跳转到AnotherActivity,使用replaceCB替换需要显示Dialog时的Activity实例
    void useCustomReplace() {
        UpdateBuilder.create()
                .replaceCB(new CustomActivityReplaceCB())
                .strategy(new AllDialogShowStrategy())//让所有流程的Dialog全显示.用作展示流程
                .check(this);
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
        }
    }
}
