package com.singun.securelogin.secure;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by singun on 2017/3/23 0023.
 */

public class SecureUtil {
    private static final String[] SECURE_SIGN_KEY_LIST = {
            "7195598ccf30fe51d5dc6f1e94f855e4",
            "a1ef99670163d5a4c424651a16849f51"
    };

    public static boolean checkSecurity(Context context, String packageName) {
        String signMd5 = AppInfoUtils.getSignatureMd5(context, packageName);
        for (String validKey : SECURE_SIGN_KEY_LIST) {
            if (TextUtils.equals(signMd5, validKey)) {
                return true;
            }
        }
        return false;
    }
}
