package com.singun.securelogin.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class LoginSignReceiver extends BroadcastReceiver {
    public static final String SIGN_ACTION = "com.singun.securelogin.sign";

    @Override
    public void onReceive(Context context, Intent intent) {
        // use for sign that it is a app using secure login
    }
}
