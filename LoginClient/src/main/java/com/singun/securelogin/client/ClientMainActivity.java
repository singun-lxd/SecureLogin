package com.singun.securelogin.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.singun.securelogin.sdk.UserLogin;
import com.singun.securelogin.user.UserProfile;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class ClientMainActivity extends AppCompatActivity {
    private UserLogin mUserLogin = new UserLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {
                UserProfile userInfo = mUserLogin.checkLoginData(ClientMainActivity.this);
                Log.e("singun", userInfo.toString());
            }
        }.start();
    }
}
