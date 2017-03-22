package com.singun.securelogin.server;

import android.content.Context;
import android.content.Intent;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class NavUtils {

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, ServerMainActivity.class);
        context.startActivity(intent);
    }

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, ServerLoginActivity.class);
        context.startActivity(intent);
    }
}
