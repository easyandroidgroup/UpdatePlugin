package org.lzh.framework.updateplugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.lzh.framework.updateplugin.R;

/**
 * Created by admin on 16-6-21.
 */
public abstract class BaseActivity extends Activity {

    protected Button startUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        startUpdate = getView(R.id.start_update);
        startUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdate();
            }
        });
    }

    abstract void startUpdate();

    <T extends View> T getView (int resId) {
        return (T) findViewById(resId);
    }


}
