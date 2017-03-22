package com.singun.securelogin.sdk;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.singun.securelogin.secure.SecureUtil;
import com.singun.securelogin.user.UserProfile;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class SecureLoginService extends Service {
    private IBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new LoginBinder(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Process.killProcess(Process.myPid());
    }

    private static class LoginBinder extends LoginAction.Stub {
        private Context mContext;

        public LoginBinder(Context context) {
            mContext = context.getApplicationContext();
        }

        private boolean checkSecurity() {
            String packageName = mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
            return SecureUtil.checkSecurity(mContext, packageName);
        }

        @Override
        public UserProfile getUserProfile() throws RemoteException {
            if (!checkSecurity()) {
                return null;
            }
            return UserLogin.getInstance(mContext).getUserProfile();
        }
    };
}
