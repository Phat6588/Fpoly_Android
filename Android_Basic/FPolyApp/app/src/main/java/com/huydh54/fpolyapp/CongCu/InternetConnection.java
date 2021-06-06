package com.huydh54.fpolyapp.CongCu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection {
    public static boolean checkConnection(Context context){
        final ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info!=null){
                if (info.getType()==ConnectivityManager.TYPE_WIFI){
                    return true;
                }else {
                    return info.getType()==ConnectivityManager.TYPE_MOBILE;
                }
            }
        }
        return false;
    }
}
