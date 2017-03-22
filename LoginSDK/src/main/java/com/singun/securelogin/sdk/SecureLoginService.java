package com.singun.securelogin.sdk;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

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

        @Override
        public int getAccountType() throws RemoteException {
            return UserLogin.getInstance(mContext).getUserProfile().getAccountType();
        }

        @Override
        public String getAccountName() throws RemoteException {
            return UserLogin.getInstance(mContext).getUserProfile().getAccountName();
        }

        @Override
        public String getAccountToken() throws RemoteException {
            return UserLogin.getInstance(mContext).getUserProfile().getAccountToken();
        }
    };
}
