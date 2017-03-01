package org.lzh.framework.updateplugin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import org.lzh.framework.updateplugin.update.AllDialogShowStrategy;
import org.lzh.framework.updateplugin.update.CustomApkFileCreator;
import org.lzh.framework.updateplugin.update.NotificationDownloadCreator;
import org.lzh.framework.updateplugin.update.OkhttpCheckWorker;
import org.lzh.framework.updateplugin.update.OkhttpDownloadWorker;
import org.lzh.framework.updateplugin.widget.CheckedView;
import org.lzh.framework.updatepluginlib.UpdateBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SampleActivity extends Activity {
    @BindView(R.id.start_update) Button startUpdate;
    @BindView(R.id.check_worker) CheckedView updateWorker;
    @BindView(R.id.file_creator) CheckedView fileCreator;
    @BindView(R.id.update_strategy) CheckedView updateStrategy;
    @BindView(R.id.download_notice) CheckedView downloadNotice;
    @BindView(R.id.download_worker) CheckedView downloadWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_update)
    void onStartClick () {
        UpdateBuilder builder = UpdateBuilder.create();
        // 根据各项是否选择使用默认配置进行使用更新。
        if (!updateWorker.isDefaultSelected()) {
            builder.checkWorker(new OkhttpCheckWorker());
        }

        if (!fileCreator.isDefaultSelected()) {
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
