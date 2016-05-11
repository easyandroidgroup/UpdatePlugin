package org.lzh.framework.updateplugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String apkFile = "http://apk.hiapk.com/web/api.do?qt=8051&id=721";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBuilder.create()
                        .url("http://www.baidu.com")
                        .strategy(new UpdateStrategy() {
                            @Override
                            public boolean isShowUpdateDialog(Update update) {
                                return true;
                            }

                            @Override
                            public boolean isAutoInstall() {
                                return true;
                            }

                            @Override
                            public boolean isShowInstallDialog() {
                                return true;
                            }

                            @Override
                            public boolean isShowDownloadDialog() {
                                return true;
                            }
                        })
                        .jsonParser(new UpdateParser() {
                            @Override
                            public Update parse(String httpResponse) {
                                Update update = new Update("");
                                update.setForced(false);
                                update.setUpdateContent("测试");
                                update.setUpdateUrl(apkFile);
                                update.setVersionCode(3);
                                update.setVersionName("3.1");
                                update.setUpdateTime(System.currentTimeMillis());
                                return update;
                            }
                        })
                        .checkCB(new UpdateCheckCB() {
                            @Override
                            public void hasUpdate(Update update) {

                            }

                            @Override
                            public void noUpdate() {
                                Toast.makeText(MainActivity.this, "noUpdate", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCheckError(int code, String errorMsg) {
                                Toast.makeText(MainActivity.this, "checkError:code:" + code + ",errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .downloadCB(new UpdateDownloadCB() {
                            @Override
                            public void onUpdateStart() {
                                Toast.makeText(MainActivity.this, "onUpdateStart", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onUpdateComplete(File file) {
                                Toast.makeText(MainActivity.this, "onUpdateComplete", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onUpdateProgress(long current, long total) {

                            }

                            @Override
                            public void onUpdateError(int code, String errorMsg) {
                                Toast.makeText(MainActivity.this, "更新失败：code:" + code + ",errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .check(MainActivity.this);
            }
        });


    }
}
