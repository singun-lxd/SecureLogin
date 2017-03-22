package com.singun.securelogin.sdk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class SecureLoginService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    private IBinder mBinder = new LoginAction.Stub() {

        @Override
        public int getAccountType() throws RemoteException {
            return 0;
        }

        @Override
        public String getAccountName() throws RemoteException {
            return "fuckingName";
        }

        @Override
        public String getAccountToken() throws RemoteException {
            return "fuckingValue";
        }
    };
}
