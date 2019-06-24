package com.exam.ztes.UploadFile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.exam.ztes.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class UploadFile extends AppCompatActivity {
    Uri path;
    TextView tv_,tv_replay;
    Activity actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        actv = UploadFile.this;

        Button btn_choosefile = findViewById(R.id.btn_choosefile);
        tv_ = findViewById(R.id.tv_);
        tv_replay = findViewById(R.id.tv_replay);
        Button btn_upload = findViewById(R.id.btn_upload);

        btn_choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosefile();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosUp();
//                (new Upload(UploadFile.this, path)).execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                path = data.getData();
                try {
                    tv_.setText(path.getPath()+"\n\n"+PathUtil.getPath(actv,path));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void choosefile(){
//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent intentChooser = Intent.createChooser(intent, "Select a file");
        startActivityForResult(intentChooser, 1);
    }

    private void prosUp() {
        final Dialog dialog = new Dialog(actv);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading);

        class UpFil extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                tv_replay.setText(s);
                dialog.dismiss();
            }

            @Override
            protected String doInBackground(String... strings) {

                return uploadSingleFile();
            }
        }
        UpFil upfil = new UpFil();
        upfil.execute();

    }

    public String uploadSingleFile() {
        String response = "";

        FileUploaderV2 multipart = null;
        try {
            multipart = new FileUploaderV2("http://sismaf.co.id:8181/MultindoMobile/zrest/ZTester/aksidok", "UTF-8");

            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");


            String uploadFileName = "zzzztesupload.pdf";
//            File file = new File(path.getPath());
            File file = new File(PathUtil.getPath(actv,path));

            multipart.addFilePart("userfile", file, uploadFileName); //XA note : upload file name
            Log.e("XXXXXXXXXXXXXXXX",file.getAbsolutePath());
            response = multipart.finish2().toString();
        } catch (IOException e) {
            response = "{\"status\":\"RTO\",\"notif\":\"Error : 0 - \",\"result\":[]}";
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return response;
    }
}
