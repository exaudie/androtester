package com.exam.ztes.DurasiTelpon;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.exam.ztes.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DurasiTelponLog extends AppCompatActivity {

    Context cntx;
    Activity actv;

    SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String timenow;
    String taget = "081575943888";

    TextView tv_;
    Timer timerCekLog;

    BroadcastReceiver callBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final Bundle b = intent.getExtras();
            assert b != null;
            String state = b.getString(TelephonyManager.EXTRA_STATE);

            if (state == null) {

                //Outgoing call
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.e("tag", "Outgoing number : " + number);

            } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                timenow = String.valueOf(new Date().getTime());
                Log.e("tag", "EXTRA_STATE_OFFHOOK");

            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

                Log.e("tag", "EXTRA_STATE_IDLE" + String.valueOf(b));
                if (b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER).equals(taget)) {

                    timerCekLog = new Timer();
                    timerCekLog.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getCallDetails(taget);
                        }
                    }, 1000, 1000);

                }

            } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                //Incoming call
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.e("tag", "Incoming number : " + number);

            } else {
                Log.e("tag", "none");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durasi_telpon_log);

        cntx = DurasiTelponLog.this;
        actv = DurasiTelponLog.this;

        ActivityCompat.requestPermissions(actv, new String[]{Manifest.permission.CALL_PHONE}, 10);

        tv_ = findViewById(R.id.tv_);
        Button btn_telp = findViewById(R.id.btn_telp);
        btn_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(cntx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivityForResult(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + taget)), 119);
            }
        });
        registerReceiver(callBroadcastReceiver, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callBroadcastReceiver);
    }

    private void getCallDetails(String nohp) {
        String[] projection = new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION, CallLog.Calls.LAST_MODIFIED

        };
        StringBuffer sb = new StringBuffer();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC  ";

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, CallLog.Calls.NUMBER + " = ? and " + CallLog.Calls.LAST_MODIFIED + ">=?", new String[]{nohp, timenow}, strOrder);
        Log.e("Handlers", "Called on main thread " + cursor.getCount());
        sb.append(timenow + "\nCall Details :");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(0);
                String number = cursor.getString(2);
                String type = cursor.getString(3); // https://developer.android.com/reference/android/provider/CallLog.Calls.html#TYPE
                String time = mdformat.format(new Date(Long.parseLong(cursor.getString(4)))); // epoch time - https://developer.android.com/reference/java/text/DateFormat.html#parse(java.lang.String
                String durasi = cursor.getString(5);
                String last_modified = mdformat.format(new Date(Long.parseLong(cursor.getString(6))));

                sb.append("\n" + name + "\nPhone Number:--- " + number + " \nCall Type:--- " + type + " \nCall Date:--- \n\n" + time + "\n" + last_modified + "\n\nCall duration in sec :--- " + durasi);
                sb.append("\n----------------------------------");

                cursor.moveToNext();
            }
            timerCekLog.cancel();
        }
        cursor.close();
        Log.e("Agil value --- ", sb.toString());
        tv_.setText(sb.toString());
    }
}