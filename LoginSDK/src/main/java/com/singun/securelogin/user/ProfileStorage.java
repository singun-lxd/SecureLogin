package com.singun.securelogin.user;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class ProfileStorage {
    private static final String CONFIG_NAME = "secure_login_config";

    private static final String ACCOUNT_TYPE = "account_type";
    private static final String ACCOUNT_NAME = "account_name";
    private static final String ACCOUNT_TOKEN = "account_token";

    private SharedPreferences mSharedPreferences;

    public ProfileStorage(Context context) {
        mSharedPreferences = context.getSharedPreferences(CONFIG_NAME, MODE_PRIVATE);
    }

    public UserProfile getUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setAccountType(mSharedPreferences.getInt(ACCOUNT_TYPE, 0));
        userProfile.setAccountName(mSharedPreferences.getString(ACCOUNT_NAME, ""));
        userProfile.setAccountToken(mSharedPreferences.getString(ACCOUNT_TOKEN, ""));
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(ACCOUNT_TYPE, userProfile.getAccountType());
        editor.putString(ACCOUNT_NAME, userProfile.getAccountName());
        editor.putString(ACCOUNT_TOKEN, userProfile.getAccountToken());
        editor.apply();
    }

    public void clearUserProfile() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(ACCOUNT_TYPE);
        editor.remove(ACCOUNT_NAME);
        editor.remove(ACCOUNT_TOKEN);
        editor.apply();
    }
}
