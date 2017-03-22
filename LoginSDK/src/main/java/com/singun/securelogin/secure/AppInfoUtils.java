package com.singun.securelogin.secure;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class AppInfoUtils {
    /**
     * 获取应用签名的md5值
     * @param context
     * @return
     */
    public static String getSignatureMd5(Context context, String packageName){
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return "";
        }
        try {
            PackageInfo pInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (int j = 0; j < pInfo.signatures.length; j++) {
                byte[] signatures = pInfo.signatures[j].toByteArray();
                if (signatures != null) {
                    return toHexString(signatures);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Lower case.
     *
     * @param bytes
     * @return
     */
    public static String toHexString(final byte[] bytes) {
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md == null){
            return null;
        }
        md.update(bytes);
        byte[] tmp = md.digest();
        if(tmp == null || tmp.length <= 0){
            return null;
        }
        StringBuilder sb = new StringBuilder(tmp.length * 2);
        int v = 0;
        String hexV = null;
        for (byte b : tmp) {
            v = b & 0xFF;
            hexV = Integer.toHexString(v);
            if (hexV.length() < 2) {
                sb.append("0");
            }
            sb.append(hexV);
        }
        return sb.toString();
    }

    public static String getApplicationName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String appName = "";
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            appName = pm.getApplicationLabel(appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
        } catch (Exception e) {
        }
        return appName;
    }
}
