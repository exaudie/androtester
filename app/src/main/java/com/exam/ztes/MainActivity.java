package com.exam.ztes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exam.ztes.DurasiTelpon.DurasiTelpon;
import com.exam.ztes.FilePicker.FilePicker;
import com.exam.ztes.FormInput.FormInput;
import com.exam.ztes.InputPopup.InputPopUp;
import com.exam.ztes.Openpdf.OpenPdf;
import com.exam.ztes.PdfView.PdfView;
import com.exam.ztes.QECodeScanner.ScannerTes;
import com.exam.ztes.SelectItemList.SelectItemList;
import com.exam.ztes.UploadFile.UploadFile;
import com.exam.ztes.ViewImage.ScrollingActivity;
import com.exam.ztes.WebSocket.WebSocket;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_inputpopup = findViewById(R.id.btn_inputpopup);
        btn_inputpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputPopUp.class);
                startActivity(intent);
            }
        });

        Button btn_selectlist = findViewById(R.id.btn_selectlist);
        btn_selectlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectItemList.class);
                startActivity(intent);
            }
        });

        Button btn_openpdf = findViewById(R.id.btn_openpdf);
        btn_openpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OpenPdf.class);
                startActivity(intent);
            }
        });
        Button btn_pdfview = findViewById(R.id.btn_pdfview);
        btn_pdfview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PdfView.class);
                startActivity(intent);
            }
        });
        Button btn_filepicker = findViewById(R.id.btn_filepicker);
        btn_filepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FilePicker.class);
                startActivity(intent);
            }
        });
        Button btn_telp = findViewById(R.id.btn_telp);
        btn_telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DurasiTelpon.class);
                startActivity(intent);
            }
        });

        Button btn_uploadfile = findViewById(R.id.btn_uploadfile);
        btn_uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadFile.class);
                startActivity(intent);
            }
        });
        Button btn_websocket = findViewById(R.id.btn_websocket);
        btn_websocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebSocket.class);
                startActivity(intent);
            }
        });
        Button btn_qrcodescanner = findViewById(R.id.btn_qrcodescanner);
        btn_qrcodescanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScannerTes.class);
                startActivity(intent);
            }
        });
        Button btn_forminput = findViewById(R.id.btn_forminput);
        btn_forminput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormInput.class);
                startActivity(intent);
            }
        });
        Button btn_viewimage = findViewById(R.id.btn_viewimage);
        btn_viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });
    }
}
