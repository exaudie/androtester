package com.exam.ztes.Zinclude;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;

import com.exam.ztes.R;


/**
 * Created by eko_setiadi on 30/07/2017.
 */

public abstract class RuntimePermission {

    public static final int REQUEST_PERMISSION = 10;

    public static void requestAppPermissions(final Activity activity, final String[] requestedPermissions, final int stringId, final int requestCode) {

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermissions = false;
        for (String permissionn : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(activity, permissionn);
            showRequestPermissions = showRequestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionn);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (showRequestPermissions) {
                Snackbar.make(activity.findViewById(android.R.id.content), stringId, Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(activity, requestedPermissions, requestCode);
                    }
                }).show();
            } else {
                ActivityCompat.requestPermissions(activity, requestedPermissions, requestCode);
            }
        }
//        else {
//            onPermissionsGranted(requestCode);
//        }

    }

    public static void requestAppPermissions2(final Activity activity, final String[] requestedPermissions, final int stringId, final int requestCode) {

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermissions = false;
        for (String permissionn : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(activity, permissionn);
            showRequestPermissions = showRequestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionn);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, requestedPermissions, requestCode);
        }
    }

    public static void requestPermissionsResult(final Activity activity, int requestCode, String[] permissions, int[] grantResults, final int stringId) {
        SparseIntArray mErrorString = new SparseIntArray();
        mErrorString.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int pernisson : grantResults) {
            permissionCheck = permissionCheck + pernisson;
        }

        if ((grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck) {

        } else {
            Snackbar.make(activity.findViewById(android.R.id.content), mErrorString.get(requestCode),
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.parse("package : " + activity.getPackageCodePath()));
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    activity.startActivity(i);
                }
            }).show();
        }

    }

    public static boolean permisi_location(Activity activity) {
        int permissionFind = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCoarse = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= 23 &&
                permissionFind != PackageManager.PERMISSION_GRANTED &&
                permissionCoarse != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static boolean permisi_location2(Activity activity) {
        int permissionFind = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCoarse = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= 23 &&
                permissionFind != PackageManager.PERMISSION_GRANTED &&
                permissionCoarse != PackageManager.PERMISSION_GRANTED) {
            RuntimePermission.requestAppPermissions2(activity, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    R.string.msg_permission, RuntimePermission.REQUEST_PERMISSION);
            return false;
        }
        return true;
    }

    public static boolean permisi_phonymanag(Activity activity) {
        int permissionCheck2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static boolean permisi_phonymanag2(Activity activity) {
        int permissionCheck2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
//        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            RuntimePermission.requestAppPermissions2(activity, new String[]{
                            Manifest.permission.READ_PHONE_STATE},
                    R.string.msg_permission, RuntimePermission.REQUEST_PERMISSION);
            return false;
        }
        return true;
    }


}
