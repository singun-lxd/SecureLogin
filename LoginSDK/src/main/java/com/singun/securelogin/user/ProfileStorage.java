package com.singun.securelogin.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.singun.securelogin.config.ConfigFile;
import com.singun.securelogin.config.IConfigFile;
import com.singun.securelogin.config.SyncConfigFile;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class ProfileStorage extends SyncConfigFile {
    private static final String CONFIG_NAME = "secure_login_config";

    private static final String ACCOUNT_TYPE = "account_type";
    private static final String ACCOUNT_NAME = "account_name";
    private static final String ACCOUNT_TOKEN = "account_token";

    private IConfigFile mConfigFile;

    public ProfileStorage(Context context) {
        super(context, CONFIG_NAME);
        mConfigFile = new ProfileConfig(context);
    }

    private static class ProfileConfig extends ConfigFile {
        public ProfileConfig(Context context) {
            super(context, CONFIG_NAME);
        }
    }

    public IConfigFile getRealConfigFile() {
        return getConfigFile(false);
    }

    private IConfigFile getConfigFile(boolean sync) {
        if (sync) {
            return this;
        } else {
            return mConfigFile;
        }
    }

    public UserProfile getUserProfile(boolean sync) {
        IConfigFile configFile = getConfigFile(sync);
        UserProfile userProfile = new UserProfile();
        userProfile.setAccountType(configFile.getIntValue(ACCOUNT_TYPE, 0));
        userProfile.setAccountName(configFile.getStringValue(ACCOUNT_NAME, ""));
        userProfile.setAccountToken(configFile.getStringValue(ACCOUNT_TOKEN, ""));
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile, boolean sync) {
        IConfigFile configFile = getConfigFile(sync);
        configFile.setIntValue(ACCOUNT_TYPE, userProfile.getAccountType());
        configFile.setStringValue(ACCOUNT_NAME, userProfile.getAccountName());
        configFile.setStringValue(ACCOUNT_TOKEN, userProfile.getAccountToken());
    }

    public void clearUserProfile(boolean sync) {
        List<String> removeKeys = new ArrayList<>();
        removeKeys.add(ACCOUNT_TYPE);
        removeKeys.add(ACCOUNT_NAME);
        removeKeys.add(ACCOUNT_TOKEN);

        IConfigFile configFile = getConfigFile(sync);
        configFile.removes(removeKeys);
    }
}
