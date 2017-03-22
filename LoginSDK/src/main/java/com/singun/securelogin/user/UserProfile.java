package com.singun.securelogin.user;

import android.text.TextUtils;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class UserProfile {
    private int accountType;
    private String accountName;
    private String accountToken;

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(accountName) && !TextUtils.isEmpty(accountToken);
    }

    @Override
    public String toString() {
        return accountName + "/" + accountToken;
    }
}
