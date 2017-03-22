package com.singun.securelogin.client;

import android.content.Context;
import android.content.Intent;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class NavUtils {

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, ClientMainActivity.class);
        context.startActivity(intent);
    }

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, ClientLoginActivity.class);
        context.startActivity(intent);
    }
}
