package org.lzh.framework.updateplugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.lzh.framework.updatepluginlib.UpdateBuilder;

public class MainActivity extends BaseActivity {
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UpdateBuilder中可设置的配置与UpdateConfig中一致。检查更新入口调用check方法
                // 对于UpdateBuilder中未设置的参数。会默认使用UpdateConfig中的配置

                UpdateBuilder.create()
                        .check(MainActivity.this);


            }
        });
        findViewById(R.id.finishAndToNext).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EmptyActivity.class));
                finish();
            }
        });
    }
}
