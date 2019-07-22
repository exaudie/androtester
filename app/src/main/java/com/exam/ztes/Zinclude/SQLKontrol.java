package com.exam.ztes.Zinclude;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by admin on 10/30/2017.
 * edit on app version 1016
 */

public class SQLKontrol {
    private Context cntx;
    private SQLHelper sqlHelper;
    private SQLiteDatabase db;

    public SQLKontrol(Context cntx) {
        this.cntx = cntx;
        /** Generate database */
        sqlHelper = new SQLHelper(cntx);
        /** End Generate database */
    }

    /**
     * Menyimpan Data User ketika Login
     *
     * @param //dtuser
     */
   /* public void saveLogin(DataUser dtuser) {
        String nik, username, foto, subjabatan_user;
        nik = dtuser.getNik();
        username = dtuser.getUsername();
        foto = dtuser.getFoto();
        subjabatan_user = dtuser.getSubjabatan_user();

        db = sqlHelper.getWritableDatabase();
        ContentValues valueins = new ContentValues();
        valueins.put("nik", nik);
        valueins.put("username", username);
        valueins.put("foto", foto);
        valueins.put("subjabatan_user", subjabatan_user);
        db.insert(sqlHelper.TABLE_LOGINSHOW, null, valueins);
        db.close();
    }*/

    public void updateFotoProfile(String pfoto, String pnik) {
        db = sqlHelper.getWritableDatabase();
        String Query = "Select * from " + sqlHelper.TABLE_LOGINSHOW + " where nik = '" + pnik + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() > 0) {
            ContentValues valueup = new ContentValues();
            valueup.put("foto", pfoto);
            db.update(sqlHelper.TABLE_LOGINSHOW, valueup, "nik = '" + pnik + "'", null);
            cursor.close();
        }
        db.close();
    }

    /**
     * Tampilkan Data User Yang Login
     *
     * @return
     */
   /* public DataUser viewLogin() {
        DataUser dtuser = new DataUser();
        db = sqlHelper.getReadableDatabase();
        Cursor cuser = db.rawQuery("select * from " + sqlHelper.TABLE_LOGINSHOW, null);
        cuser.moveToFirst();
        while (!cuser.isAfterLast()) {
            dtuser.setNik(cuser.getString(cuser.getColumnIndex("nik")));
            dtuser.setUsername(cuser.getString(cuser.getColumnIndex("username")));
            dtuser.setFoto(cuser.getString(cuser.getColumnIndex("foto")));
            dtuser.setSubjabatan_user(cuser.getString(cuser.getColumnIndex("subjabatan_user")));
            cuser.moveToNext();
        }
        cuser.close();
        db.close();
        return dtuser;
    }*/

    /**
     * Cek LOGIN pada SplashScreen
     */
    /*public void checkLogin() {
        if (!this.is_login()) {
            Intent i = new Intent(cntx, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            cntx.startActivity(i);
        } else {
            Intent i = new Intent(cntx, IndexActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            cntx.startActivity(i);
        }
    }*/

    /**
     * Cek data LOGIN
     *
     * @return
     */
    private boolean is_login() {
        db = sqlHelper.getReadableDatabase();
        String Query = "Select * from " + sqlHelper.TABLE_LOGINSHOW;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    /**
     * Delete data login (LOGOUT)
     */
    public void deleteLogout() {
        db = sqlHelper.getWritableDatabase();
        db.delete(sqlHelper.TABLE_LOGINSHOW, null, null);
        db.close();
    }

    /**
     * Mengatur ulang menu user
     *
     * @param menu
     */

    public void setMenuUser(JSONArray menu) {
        try {
            db = sqlHelper.getWritableDatabase();
            db.execSQL("delete from " + sqlHelper.TABLE_MENUINDEX);
            for (int i = 0; i < menu.length(); i++) {
                JSONObject me = menu.getJSONObject(i);

                ContentValues values = new ContentValues();
                values.put(sqlHelper.COLMD_IDMENU, me.getString(sqlHelper.COLMD_IDMENU));
                values.put(sqlHelper.COLMD_NMMENU, me.getString(sqlHelper.COLMD_NMMENU));
                values.put(sqlHelper.COLMD_ICONMENU, me.getString(sqlHelper.COLMD_ICONMENU));
                values.put(sqlHelper.COLMD_URUT, me.getString(sqlHelper.COLMD_URUT));
                db.insert(sqlHelper.TABLE_MENUINDEX, null, values);
            }
            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> getMenuUser() {
        ArrayList<ArrayList<Object>> daftarmenu = new ArrayList<ArrayList<Object>>();
        db = sqlHelper.getReadableDatabase();
        Cursor menu = db.rawQuery("select * from " + sqlHelper.TABLE_MENUINDEX + " order by " + sqlHelper.COLMD_URUT + "," + sqlHelper.COLMD_IDMENU, null);
        menu.moveToFirst();
        while (!menu.isAfterLast()) {
            ArrayList<Object> pilmenu = new ArrayList<Object>();
            pilmenu.add(menu.getString(menu.getColumnIndex("idmenu")));
            pilmenu.add(menu.getString(menu.getColumnIndex("namamenu")));
            pilmenu.add(menu.getString(menu.getColumnIndex("iconmenu")));
            daftarmenu.add(pilmenu);
            menu.moveToNext();
        }
        menu.close();
        db.close();
        return daftarmenu;
    }

   /* public List<MenuUtama> getMenuUserV2() {
       List<MenuUtama> daftarmenu = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        Cursor menu = db.rawQuery("select * from " + sqlHelper.TABLE_MENUINDEX + " order by " + sqlHelper.COLMD_URUT + "," + sqlHelper.COLMD_IDMENU, null);
        menu.moveToFirst();
        while (!menu.isAfterLast()) {
            MenuUtama menuUtama=new MenuUtama();
            menuUtama.setIdmenu(menu.getString(menu.getColumnIndex("idmenu")));
            menuUtama.setNamamenu(menu.getString(menu.getColumnIndex("namamenu")));
            menuUtama.setIconmenu(menu.getString(menu.getColumnIndex("iconmenu")));
            daftarmenu.add(menuUtama);
            menu.moveToNext();
        }
        menu.close();
        db.close();
        return daftarmenu;
    }*/

    /**
     * riwayat panggilan
     */
    //    regid, status
    public int getHistoryTelp(String regid) {
        int detik = 0;
        db = sqlHelper.getReadableDatabase();
        Cursor cstts = db.rawQuery("select * from " + sqlHelper.TABLE_HISTORITELP + " where regid='" + regid + "'", null);
        if (cstts.getCount() > 0) {
            cstts.moveToFirst();
            detik = cstts.getInt(cstts.getColumnIndex("status"));
        }
        cstts.close();
        db.close();
        return detik;
    }

    public void deleteHistoryTelp(String regid) {
        db = sqlHelper.getWritableDatabase();
        db.execSQL("delete from " + sqlHelper.TABLE_HISTORITELP + " where regid = '" + regid + "'");
        db.close();
    }

    public void updateHistoryTelp(String regid, int detik) {
        db = sqlHelper.getWritableDatabase();
        Cursor cstts = db.rawQuery("select * from " + sqlHelper.TABLE_HISTORITELP + " where regid = '" + regid + "'", null);
        if (cstts.getCount() > 0) {
            ContentValues valueup = new ContentValues();
            valueup.put("status", detik);
            db.update(sqlHelper.TABLE_HISTORITELP, valueup, "regid = '" + regid + "'", null);
            cstts.close();
        } else {
            ContentValues valueins = new ContentValues();
            valueins.put("regid", regid);
            valueins.put("status", detik);
            db.insert(sqlHelper.TABLE_HISTORITELP, null, valueins);
            cstts.close();
        }
        db.close();
    }

    /**
     * Status Cek Dokumen
     */


}