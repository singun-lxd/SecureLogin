package com.singun.securelogin.sdk;

import android.content.Context;

import com.singun.securelogin.config.ConfigFile;
import com.singun.securelogin.config.ConfigProvider;
import com.singun.securelogin.config.IConfigFile;
import com.singun.securelogin.user.ProfileStorage;

import java.util.Map;

/**
 * Created by singun on 2017/3/23 0023.
 */

public class SecureLoginProvider extends ConfigProvider {
    @Override
    protected void updateConfigMap(Map<String, IConfigFile> configMap) {
        Context appContext = getContext().getApplicationContext();
        IConfigFile configFile = UserLogin.getInstance(appContext).getConfigFile();
        configMap.put(configFile.getConfigName(), configFile);
    }
}
