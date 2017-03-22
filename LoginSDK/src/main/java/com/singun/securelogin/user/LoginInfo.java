package com.singun.securelogin.user;

import android.os.Parcel;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class LoginInfo extends UserProfile {
    private String packageName;
    private String applicationName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return packageName  + "/" + super.toString();
    }
}
