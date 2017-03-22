package com.singun.securelogin.user;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class UserProfile implements Parcelable {
    private int accountType;
    private String accountName;
    private String accountToken;

    public UserProfile() {
        accountType = 0;
        accountName = "";
        accountToken = "";
    }

    public UserProfile(Parcel in) {
        accountType = in.readInt();
        accountName = in.readString();
        accountToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accountType);
        dest.writeString(accountName);
        dest.writeString(accountToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

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
