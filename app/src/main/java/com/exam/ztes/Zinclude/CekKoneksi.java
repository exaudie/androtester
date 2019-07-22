package com.exam.ztes.Zinclude;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 6/7/2017.
 */
public class CekKoneksi {
    public static boolean isOnline(Context cntx) {
        ConnectivityManager cm = (ConnectivityManager) cntx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //jika ada koneksi return true
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }else {
            return false;
        }
        //jika tidak ada koneksi return false
    }
}
