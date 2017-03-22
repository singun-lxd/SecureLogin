package com.singun.securelogin.server;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.singun.securelogin.secure.AppInfoUtils;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class ServerMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String sign = AppInfoUtils.getSignatureMd5(this, "com.singun.securelogin.client");
        Log.e("singun", "signMd5:" + sign);

        String sign2 = AppInfoUtils.getSignatureMd5(this, "com.cmcm.whatscalllite");
        Log.e("singun", "signMd5:" + sign2);
    }
}
