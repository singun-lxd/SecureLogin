package com.singun.securelogin.server;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.singun.securelogin.sdk.UserLogin;
import com.singun.securelogin.secure.AppInfoUtils;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class ServerMainActivity extends AppCompatActivity implements View.OnClickListener {
    private UserLogin mUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLogin = UserLogin.getInstance(getApplicationContext());
        if (!mUserLogin.isLogin()) {
            signOut();
            return;
        }

        setContentView(R.layout.activity_main);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mUserLogin.logout();
        signOut();
    }

    private void signOut() {
        finish();
        NavUtils.startLoginActivity(this);
    }
}
