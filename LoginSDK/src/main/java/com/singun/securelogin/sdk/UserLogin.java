package com.singun.securelogin.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.singun.securelogin.secure.AppInfoUtils;
import com.singun.securelogin.user.LoginInfo;
import com.singun.securelogin.user.ProfileStorage;
import com.singun.securelogin.user.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class UserLogin {
    private static final String[] SECURE_SIGN_KEY_LIST = {
            "7195598ccf30fe51d5dc6f1e94f855e4",
            "a1ef99670163d5a4c424651a16849f51"
    };

    private static UserLogin sInstance;

    private ProfileStorage mProfileStorage;

    private UserLogin(Context context) {
        mProfileStorage = new ProfileStorage(context);
    }

    public static synchronized UserLogin getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UserLogin(context);
        }
        return sInstance;
    }

    public boolean isLogin() {
        UserProfile userProfile = mProfileStorage.getUserProfile();
        return userProfile.isValid();
    }

    public UserProfile getUserProfile() {
        return mProfileStorage.getUserProfile();
    }

    public void quickLogin(UserProfile userProfile) {
        mProfileStorage.setUserProfile(userProfile);
    }

    @WorkerThread
    public LoginInfo login(String accountName, String password) {
        LoginInfo loginInfo = new LoginInfo();
        // todo fack login
        String nameData = accountName + password + SystemClock.uptimeMillis();
        String token = AppInfoUtils.toHexString(nameData.getBytes());
        loginInfo.setAccountName(accountName);
        loginInfo.setAccountToken(token);
        mProfileStorage.setUserProfile(loginInfo);
        return loginInfo;
    }

    public void logout() {
        mProfileStorage.clearUserProfile();
    }

    @WorkerThread
    public LoginInfo checkLoginData(Context context) {
        List<LoginInfo> loginInfoList = getLoginDataList(context, false);
        if (!loginInfoList.isEmpty()) {
            return loginInfoList.get(0);
        }
        return null;
    }

    @WorkerThread
    public List<LoginInfo> getLoginDataList(Context context, boolean allData) {
        List<LoginInfo> loginInfoList = new ArrayList<>();
        if (context == null) {
            return loginInfoList;
        }
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return loginInfoList;
        }
        Intent intent = new Intent(LoginSignReceiver.SIGN_ACTION);
        List<ResolveInfo> infoList = pm.queryBroadcastReceivers(intent, 0);
        if (infoList == null || infoList.size() <= 0) {
            return loginInfoList;
        }
        int size = infoList.size();
        for (int i = 0; i < size; i++) {
            ResolveInfo info = infoList.get(i);
            if (info == null) {
                continue;
            }
            String packageName = getPackageName(info);
            if (packageName == null || packageName.equals(context.getPackageName())) {
                continue;
            }
            LoginInfo loginInfo = getUserInfoFromPackage(context, packageName);
            if (loginInfo != null) {
                loginInfoList.add(loginInfo);
            }
            if (!allData && !loginInfoList.isEmpty()) {
                break;
            }
        }
        return loginInfoList;
    }

    private LoginInfo getUserInfoFromPackage(Context context, String packageName) {
        final Context appContext = context.getApplicationContext();
        if (!checkSecurity(appContext, packageName)) {
            return null;
        }
        final LoginInfo loginInfo = createLoginInfo(appContext, packageName);

        Intent bindIntent = new Intent();
        bindIntent.setClassName(packageName, SecureLoginService.class.getName());
        final CountDownLatch cdl = new CountDownLatch(1);
        boolean bindSuccess = appContext.bindService(bindIntent, new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                try {
                    appContext.unbindService(this);
                } catch (Exception e) {
                } catch (Error e) {
                } finally {
                    cdl.countDown();
                }
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    LoginAction loginAction = LoginAction.Stub.asInterface(service);
                    loginInfo.setAccountType(loginAction.getAccountType());
                    loginInfo.setAccountName(loginAction.getAccountName());
                    loginInfo.setAccountToken(loginAction.getAccountToken());
                } catch (RemoteException e) {
                } catch (Exception e) {
                } finally {
                    try {
                        appContext.unbindService(this);
                    } catch (Exception e) {
                    } catch (Error e) {
                    }
                    cdl.countDown();
                }
            }
        }, Context.BIND_AUTO_CREATE);

        if (bindSuccess) {
            try {
                cdl.await(4500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
            }
        }

        return loginInfo.isValid() ? loginInfo : null;
    }

    private boolean checkSecurity(Context context, String packageName) {
        String signMd5 = AppInfoUtils.getSignatureMd5(context, packageName);
        for (String validKey : SECURE_SIGN_KEY_LIST) {
            if (TextUtils.equals(signMd5, validKey)) {
                return true;
            }
        }
        return false;
    }

    private LoginInfo createLoginInfo(Context context, String packageName) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPackageName(packageName);
        String appName = AppInfoUtils.getApplicationName(context, packageName);
        loginInfo.setApplicationName(appName);
        return loginInfo;
    }

    private String getPackageName(ResolveInfo info) {
        String packageName = null;
        if (info.activityInfo != null) {
            packageName = info.activityInfo.packageName;
        }
        if (packageName == null && info.activityInfo != null &&
                info.activityInfo.applicationInfo != null) {
            packageName = info.activityInfo.applicationInfo.packageName;
        }
        return packageName;
    }
}
