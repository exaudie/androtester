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
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.ztes.R;
import com.exam.ztes.Zinclude.CekKoneksi;
import com.exam.ztes.Zinclude.LoadingCustom;
import com.exam.ztes.Zinclude.RuntimePermission;
import com.exam.ztes.Zinclude.SQLKontrol;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DurasiTelpon extends AppCompatActivity {

    Context cntx;
    Activity actv;
    String noHpShowroom = "081575943888", RegId = "1";
    TextView tv_;

    SQLKontrol sqlKontrol;

    int timerCekLogDurasi = 0, timerCekLogDurasiMax = 15;
    Timer timerCekLog;
    String timeCall = "", lastIdLogCall = "";
    LoadingCustom loadingCustom;
    boolean kontakFormApp = false, resumeActv = false, stsIdle = false, catatHistoryCall = true;
    BroadcastReceiver callBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            assert b != null;
            String state = b.getString(TelephonyManager.EXTRA_STATE);

            if (state == null) {
            } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                Log.e("tag", "EXTRA_STATE_OFFHOOK");
                timeCall = String.valueOf(new Date().getTime());
                stsIdle = false;
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Log.e("tag", "EXTRA_STATE_IDLE");/*incoming_number*/
                if (b.containsKey(TelephonyManager.EXTRA_INCOMING_NUMBER)) {
                    if (b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER).equals(noHpShowroom) && kontakFormApp) {
                        timerCekLogDurasi = 0;
                        stsIdle = true;
                        kontakFormApp = false;
                        loadingCustom.show();
                        timerCekLog = new Timer();
                        timerCekLog.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                /*if (CekKoneksi.isOnline(cntx) && resumeActv) {
                                    timerCekLogDurasi++;
                                    getCallDetails(noHpShowroom);
                                    if (timerCekLogDurasi == timerCekLogDurasiMax) {
                                        stsIdle = false;
                                        resumeActv = false;
                                        cancel();
                                        loadingCustom.dismiss();
                                    }
                                }*/
                                catatHistoryCall2(noHpShowroom);
                                if (CekKoneksi.isOnline(cntx) && resumeActv) {
                                    timerCekLogDurasi++;
                                    sendHistoryCall();
                                    if (timerCekLogDurasi == timerCekLogDurasiMax) {
                                        stsIdle = false;
                                        resumeActv = false;
                                        cancel();
                                        loadingCustom.dismiss();
                                    }
                                }
                            }
                        }, 1000, 1000);
                    }
                }
            } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Log.e("tag", "EXTRA_STATE_RINGING");
            } else {
                Log.e("tag", "none");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durasi_telpon);
        cntx = DurasiTelpon.this;
        actv = DurasiTelpon.this;

        RuntimePermission.requestAppPermissions2(actv, new String[]{
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CALL_LOG},
                R.string.msg_permission, RuntimePermission.REQUEST_PERMISSION);
        tv_ = findViewById(R.id.tv_);

        /* Generate database SQLite */
        sqlKontrol = new SQLKontrol(cntx);
        loadingCustom = new LoadingCustom(cntx);

        Button btn_telp = findViewById(R.id.btn_telp);
        btn_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastHistoryCall(noHpShowroom);
                if (ActivityCompat.checkSelfPermission(actv, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    RuntimePermission.requestAppPermissions2(actv, new String[]{
                                    Manifest.permission.CALL_PHONE},
                            R.string.msg_permission, RuntimePermission.REQUEST_PERMISSION);
                } else {
                    /** Kontak Showroom */
                    startActivityForResult(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + noHpShowroom)), 119);
                    kontakFormApp = true;
                }
            }
        });
        registerReceiver(callBroadcastReceiver, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));

//        tv_.setText(noHpShowroom + " | " + sqlKontrol.getHistoryTelp(RegId) + " detik");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(cntx, requestCode + " | " + resultCode + " | " + String.valueOf(data), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stsIdle) {// cek jika selesai telpon dari aplikasi
            resumeActv = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callBroadcastReceiver);
        if (timerCekLog != null) {
            timerCekLog.cancel();
        }
    }

    private void getLastHistoryCall(String nohp) {
        if (ActivityCompat.checkSelfPermission(actv, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(actv, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            RuntimePermission.requestAppPermissions2(actv, new String[]{
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CALL_LOG},
                    R.string.msg_permission, RuntimePermission.REQUEST_PERMISSION);
        } else {
            String strOrder = CallLog.Calls._ID + " desc limit 1";
            Cursor cursor = cntx.getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls._ID}, CallLog.Calls.NUMBER + " = ? ", new String[]{nohp}, strOrder);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
                lastIdLogCall = cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));
//                    cursor.moveToNext();
//                }
                Log.e("zxxxxxxzzxzxz", "getLastHistoryCall | lastIdLogCall : " + lastIdLogCall);
            }
            cursor.close();
        }
    }

    private void catatHistoryCall2(String nohp) {
        if (catatHistoryCall) {
            String[] projection = new String[]{
                    CallLog.Calls._ID,
                    CallLog.Calls.CACHED_NAME,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DATE,
                    CallLog.Calls.DURATION
            };
            String strOrder = CallLog.Calls._ID + " desc limit 1";

            if (ActivityCompat.checkSelfPermission(actv, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(actv, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                RuntimePermission.requestAppPermissions2(actv, new String[]{
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_CALL_LOG},
                        R.string.msg_permission, RuntimePermission.REQUEST_PERMISSION);
            } else {
                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Cursor cursor = cntx.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, CallLog.Calls.NUMBER + " = ? and " + CallLog.Calls._ID + " > ?", new String[]{nohp, lastIdLogCall}, strOrder);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int durasi = 0;

//                Gson gson = new Gson();
//                String json = gson.toJson(cursor);
                    while (!cursor.isAfterLast()) {
                        String sDurasi = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                        durasi = Integer.parseInt(sDurasi);
                        Log.e("zxxxxxxzzxzxz", "catatHistoryCall2 | newIdLogCall : " + cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID)));
                        cursor.moveToNext();
                    }
                    if (durasi > sqlKontrol.getHistoryTelp(RegId)) {
                        sqlKontrol.updateHistoryTelp(RegId, durasi);
                        catatHistoryCall = false;
                        Log.e("zxxxxxxzzxzxz", "catatHistoryCall2");
                    }
                }
                cursor.close();

            }
        }
    }

    private void sendHistoryCall() {
        if (sqlKontrol.getHistoryTelp(RegId) > 0) {
            Log.e("zxxxxxxzzxzxz", "sendHistoryCall");
            stsIdle = false;
            resumeActv = false;
            loadOrderSor(false);
            timerCekLog.cancel();
        }
    }

    private void loadOrderSor(boolean loading) {
        if (!loading) {
            loadingCustom.dismiss();
        }
//        tv_.setText(noHpShowroom + " | " + sqlKontrol.getHistoryTelp(RegId) + " detik");
        Log.e("rtserysryr", "loadOrderSor | " + noHpShowroom + " | " + sqlKontrol.getHistoryTelp(RegId) + " detik");
        sqlKontrol.deleteHistoryTelp(RegId);
    }
}