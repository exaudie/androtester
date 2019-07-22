package id.co.multindo.sismafmobile.in.zinclude;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by admin on 4/26/2017.
 * edit on app version 1016
 */
public class SQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "andromaf.db";
    private static final int DATABASE_VERSION = 2;
    private Context cntx;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cntx = context;
    }

    // TODO : Struktur tabel menu_index
    /**
     * menu_index >
     **/
    public static final String TABLE_MENUINDEX_OLD = "menu_index_old";
    public static final String TABLE_MENUINDEX = "menu_index";
    public static final String COLMD_IDMENU = "idmenu";
    public static final String COLMD_NMMENU = "namamenu";
    public static final String COLMD_ICONMENU = "iconmenu";
    public static final String COLMD_URUT = "urut";
    public static final String CREATE_MENUINDEX = "CREATE TABLE " + TABLE_MENUINDEX + " ("
            + COLMD_IDMENU + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLMD_NMMENU + " TEXT,"
            + COLMD_ICONMENU + " TEXT,"
            + COLMD_URUT + " INTEGER)";
    //    public static final String CREATE_MENUINDEX = "CREATE TABLE " + TABLE_MENUINDEX + " ("
//            + COLMD_IDMENU + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + COLMD_NMMENU + " TEXT,"
//            + COLMD_ICONMENU + " TEXT)";
    public static final String ALTER_MENUINDEX = "ALTER TABLE " + TABLE_MENUINDEX + " RENAME TO " + TABLE_MENUINDEX_OLD;
    /**
     * < menu_index
     **/

    // TODO : Struktur tabel loginshow
    /**
     * simpan session login
     * loginshow >
     **/
    public static final String TABLE_LOGINSHOW_OLD = "loginshow_old";
    public static final String TABLE_LOGINSHOW = "loginshow";
    public static final String COLLS_NIK = "nik";
    public static final String COLLS_USERNAME = "username";
    public static final String COLLS_FOTO = "foto";
    public static final String COLLS_SUBJABATAN = "subjabatan_user";
    //    public static final String COLLS_TOKEN = "token";
//    public static final String COLLS_NOTIFMENU = "notifautomenu";
    public static final String CREATE_LOGINSHOW = " CREATE TABLE " + TABLE_LOGINSHOW + "("
            + COLLS_NIK + " TEXT, "
            + COLLS_USERNAME + " TEXT, "
            + COLLS_FOTO + " TEXT, "
            + COLLS_SUBJABATAN + " TEXT, PRIMARY KEY(" + COLLS_NIK + "))";
    public static final String ALTER_LOGINSHOW = "ALTER TABLE " + TABLE_LOGINSHOW + " RENAME TO " + TABLE_LOGINSHOW_OLD;
    /**
     * < loginshow
     **/

    // TODO : Struktur tabel historitlpn
    /**
     * historitlpn >
     **/
    public static final String TABLE_HISTORITELP = "historitlpn";
    public static final String COLHT_REGID = "regid";
    public static final String COLHT_STATUS = "status";
    public static final String CREATE_HISTORITELP = "CREATE TABLE " + TABLE_HISTORITELP + "("
            + COLHT_REGID + " TEXT,"
            + COLHT_STATUS + " INTEGER, PRIMARY KEY(" + COLHT_REGID + ") )";
    public static final String ALTER_HISTORITELP = "";

    /**
     * < historitlpn
     **/

    // TODO : Struktur tabel cek dokumen CS
    /**
     * cek dokumen cs >
     **/
    public static final String TABLE_CEKDOK_CS = "cekdokcs";
    public static final String COLCK_REGID = "regid";
    public static final String COLCK_PERJANJIAN = "stsjanji";
    public static final String COLCK_PERJANJIANSEK = "stsjanjisek";
    public static final String COLCK_IDNASABAH = "stsidnas";
    public static final String COLCK_USAHANASABAH = "stsusahanas";
    public static final String COLCK_HASILSURVEY = "stshslsurvey";
    public static final String COLCK_JAMINAN = "stsjaminan";
    public static final String CREATE_CEKDOK_CS = "CREATE TABLE " + TABLE_CEKDOK_CS + "("
            + COLHT_REGID + " TEXT,"
            + COLCK_PERJANJIAN + " INTEGER,"
            + COLCK_PERJANJIANSEK + " INTEGER,"
            + COLCK_IDNASABAH + " INTEGER,"
            + COLCK_USAHANASABAH + " INTEGER,"
            + COLCK_HASILSURVEY + " INTEGER,"
            + COLCK_JAMINAN + " INTEGER, PRIMARY KEY(" + COLCK_REGID + ") )";
    public static final String ALTER_CEKDOK_CS = "";

    /**
     * < cek dokumen cs
     **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_MENUINDEX);
        } catch (SQLiteException e) {
            Log.e("Create DB", "Error Create MENUINDEX.");
        }
        try {
            db.execSQL(CREATE_LOGINSHOW);
        } catch (SQLiteException e) {
            Log.e("Create DB", "Error Create LOGINSHOW.");
        }
        try {
            db.execSQL(CREATE_HISTORITELP);
        } catch (SQLiteException e) {
            Log.e("Create DB", "Error Create HISTORITELP.");
        } try {
            db.execSQL(CREATE_CEKDOK_CS);
        } catch (SQLiteException e) {
            Log.e("Create DB", "Error Create STATUS CEK DOKUMEN.");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (newVersion > oldVersion) {
                db.execSQL(ALTER_LOGINSHOW);
                // Creating the table on its new format (no redundant columns)
                db.execSQL(CREATE_LOGINSHOW);
                // Populating the table with the data
                db.execSQL("INSERT INTO " + TABLE_LOGINSHOW + "(" + COLLS_NIK + "," + COLLS_USERNAME + "," + COLLS_FOTO + "," + COLLS_SUBJABATAN + ") SELECT "
                        + COLLS_NIK + "," + COLLS_USERNAME + "," + COLLS_FOTO + "," + COLLS_SUBJABATAN + " FROM " + TABLE_LOGINSHOW_OLD);
                db.execSQL("DROP TABLE " + TABLE_LOGINSHOW_OLD);
//            db.execSQL("ALTER TABLE foo ADD COLUMN new_column INTEGER DEFAULT 0");
                /**
                 * Update tabel TABLE_MENUINDEX
                 */
                db.execSQL(ALTER_MENUINDEX);
                db.execSQL(CREATE_MENUINDEX);
                db.execSQL("INSERT INTO " + TABLE_MENUINDEX + "(" + COLMD_IDMENU + "," + COLMD_NMMENU + "," + COLMD_ICONMENU + ") SELECT "
                        + COLMD_IDMENU + "," + COLMD_NMMENU + "," + COLMD_ICONMENU + " FROM " + TABLE_MENUINDEX_OLD);
                db.execSQL("DROP TABLE " + TABLE_MENUINDEX_OLD);
            } else {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENUINDEX);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGINSHOW);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORITELP);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CEKDOK_CS);
                onCreate(db);
            }
        } catch (SQLiteException e) {
            Log.e("Upgrade DB", "Error Upgrade Table.");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        Log.e("Downgrade DB", "Error Downgrade Table.");
    }
}