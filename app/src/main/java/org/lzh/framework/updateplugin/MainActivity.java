package org.lzh.framework.updateplugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.lzh.framework.updatepluginlib.UpdateConfig;

public class MainActivity extends AppCompatActivity {

    private String apkFile = "http://p.gdown.baidu.com/1085ce1b0a2b544d716413178475a205238e41494888635bb6045caf844b8853f2e7fd7d0bd810bb03895e733d4425bf03dfa553870944126f7052e6d4071467e1da72015a6fccf3a0b0210b5fe1d1c9af51966a2c61846a013bd61d9bf6ba4b40f61d8d9aea49af1538926fee90cd00a0ecc6fcce156d67fb1b348a0c4f290db899e47d8bc3ff47c7bf4a842d2a80f824d2ca081ba038cea860a4502e62079a745d61e7dc2c385e893a1569d9e87db7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UpdateConfig.install(this);
    }
}
