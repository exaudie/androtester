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
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.ztes.R;

public class DurasiTelpon extends AppCompatActivity {

    Context cntx;
    Activity actv;
    String taget = "081575943888";
    TextView tv_;
    TelephonyManager telephony;


    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    private static final String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int PERMISSION_REQUEST = 100;

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private TelephonyManager mTelephonyManager;

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    static boolean flag = false;
    static long start_time, end_time;
    BroadcastReceiver callBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(context, String.valueOf(intent), Toast.LENGTH_SHORT).show();
            Log.e("intent", String.valueOf(intent));
            Bundle bb=intent.getExtras();
            Log.e("getExtras", String.valueOf(bb));

            if (action.equalsIgnoreCase("android.intent.action.PHONE_STATE")) {
                if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                        TelephonyManager.EXTRA_STATE_RINGING)) {
                    start_time = System.currentTimeMillis();

                }
                if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                        TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                    Toast.makeText(context, " EXTRA_STATE_OFFHOOK ", Toast.LENGTH_SHORT).show();
                    //Store total_time somewhere or pass it to an Activity using intent
                }
                if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                        TelephonyManager.EXTRA_STATE_IDLE)) {
                    end_time = System.currentTimeMillis();
                    //Total time talked =
                    long total_time = end_time - start_time;
                    Toast.makeText(context, total_time + " = " + end_time + " - " + start_time, Toast.LENGTH_SHORT).show();
                    //Store total_time somewhere or pass it to an Activity using intent
                }
            }

//            final Bundle b = intent.getExtras();
//            assert b != null;
//            String state = b.getString(TelephonyManager.EXTRA_STATE);
//
//            if (state == null) {
//
//                //Outgoing call
//                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
//                Log.e("tag", "Outgoing number : " + number);
//
//            } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
//
//                Log.e("tag", "EXTRA_STATE_OFFHOOK");
//
//            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
//
//                Log.e("tag", "EXTRA_STATE_IDLE" + String.valueOf(b));
//                if (b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER).equals(taget)) {
//                    new CountDownTimer(20000, 2000) {
//                        @Override
//                        public void onTick(long l) {
//                            Toast.makeText(cntx, "onTick", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            Toast.makeText(cntx, "onFinish", Toast.LENGTH_SHORT).show();
////                            getCallDetails(b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
//                        }
//                    }.start();
//
//                }
//
//            } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//
//                //Incoming call
//                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//                Log.e("tag", "Incoming number : " + number);
//
//            } else {
//                Log.e("tag", "none");
//            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durasi_telpon);
        cntx = DurasiTelpon.this;
        actv = DurasiTelpon.this;
        tv_ = findViewById(R.id.tv_);


        ActivityCompat.requestPermissions(actv, PERMISSIONS, 10);
//        callPhoneManager();
        Button btn_telp = findViewById(R.id.btn_telp);
        btn_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + taget)), 119);
            }
        });
        registerReceiver(callBroadcastReceiver, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
/*
//        MyPhoneStateListener phoneListener = new MyPhoneStateListener();
//        telephony = (TelephonyManager) cntx
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
//

*/
//
//        TelephonyManager telephonyManager =
//                (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//
//        PhoneStateListener callStateListener = new PhoneStateListener() {
//            public void onCallStateChanged(int state, String incomingNumber)
//            {
//                if(state==TelephonyManager.CALL_STATE_RINGING){
//                    Toast.makeText(getApplicationContext(),"Phone Is Riging",
//                            Toast.LENGTH_LONG).show();
//                }
//                if(state==TelephonyManager.CALL_STATE_OFFHOOK){
//                    Toast.makeText(getApplicationContext(),"Phone is Currently in A call",
//                            Toast.LENGTH_LONG).show();
//                }
//
//                if(state==TelephonyManager.CALL_STATE_IDLE){
//                    Toast.makeText(getApplicationContext(),"phone is neither ringing nor in a call",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//
//        };
//        telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(cntx, requestCode + " | " + resultCode + " | " + String.valueOf(data), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callBroadcastReceiver);
    }

    /*
        private void getCallDetails(String nohp) {
            String[] projection = new String[]{
                    CallLog.Calls.CACHED_NAME,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DATE,
                    CallLog.Calls.DURATION
            };
            StringBuffer sb = new StringBuffer();
            String strOrder = android.provider.CallLog.Calls.DATE + " DESC";

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
            Cursor cursor = cntx.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, CallLog.Calls.NUMBER+"=?", new String[]{nohp}, strOrder);
            sb.append("Call Details :");
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                String type = cursor.getString(2); // https://developer.android.com/reference/android/provider/CallLog.Calls.html#TYPE
                String time = cursor.getString(3); // epoch time - https://developer.android.com/reference/java/text/DateFormat.html#parse(java.lang.String
                String durasi = cursor.getString(4);

                sb.append("\nPhone Number:--- " + number + " \nCall Type:--- " + type + " \nCall Date:--- " + time + " \nCall duration in sec :--- " + durasi);
                sb.append("\n----------------------------------");
            }
            cursor.close();
            Log.e("Agil value --- ", sb.toString());
            tv_.setText(sb.toString());
        }
    */
    /*private static class MyPhoneStateListener extends PhoneStateListener {

        public static Boolean phoneRinging = false;

        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.d("DEBUG", "IDLE");
                    phoneRinging = false;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d("DEBUG", "OFFHOOK");
                    phoneRinging = false;
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d("DEBUG", "RINGING");
                    phoneRinging = true;

                    break;
            }

        }
    }*/

    /*private void callPhoneManager() {

        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mTelephonyManager.listen(new PhoneCallback(tv_), PhoneStateListener.LISTEN_CALL_STATE
                | PhoneStateListener.LISTEN_CELL_INFO // Requires API 17
                | PhoneStateListener.LISTEN_CELL_LOCATION
                | PhoneStateListener.LISTEN_DATA_ACTIVITY
                | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                | PhoneStateListener.LISTEN_SERVICE_STATE
                | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                | PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
                | PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);
    }*/
}