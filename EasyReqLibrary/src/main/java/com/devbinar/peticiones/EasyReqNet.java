package com.devbinar.peticiones;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class EasyReqNet {

    public static boolean internet_enabled(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        if(actNetInfo != null && actNetInfo.isConnected()){
            return true;
        }else{

            return false;
        }
    }

    public static boolean have_internet(Context context) {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            if(reachable){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
