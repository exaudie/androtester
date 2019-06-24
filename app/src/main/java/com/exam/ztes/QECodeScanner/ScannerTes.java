package com.exam.ztes.QECodeScanner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.exam.ztes.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannerTes extends AppCompatActivity {
    Activity actv;
    SurfaceView cameraView;

    BarcodeDetector barcode;

    CameraSource cameraSource;

    SurfaceHolder holder;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        actv = ScannerTes.this;

//        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        cameraView = findViewById(R.id.surfaceView);
        initialiseDetectorsAndSources();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    private void initialiseDetectorsAndSources() {

        cameraView.setZOrderMediaOverlay(true);
//
        holder = cameraView.getHolder();

        barcode = new BarcodeDetector.Builder(this)

                .setBarcodeFormats(Barcode.QR_CODE)

                .build();

        if (!barcode.isOperational()) {

            Toast.makeText(getApplicationContext(), "Sorry couldnt setup the detector", Toast.LENGTH_LONG).show();

            this.finish();

        }

        cameraSource = new CameraSource.Builder(this, barcode)

                .setFacing(CameraSource.CAMERA_FACING_BACK)

                .setRequestedFps(24)

                .setAutoFocusEnabled(true)

                .setRequestedPreviewSize(1920,1024)

                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override

            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                try {
                    if (ActivityCompat.checkSelfPermission(actv, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(actv, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }



            @Override

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {



            }



            @Override

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {



            }

        });

        barcode.setProcessor(new Detector.Processor<Barcode>() {

            @Override

            public void release() {



            }



            @Override

            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcode = detections.getDetectedItems();

                if (barcode.size() > 0) {

//                    Intent intent = new Intent();
//
//                    intent.putExtra("barcode", barcode.valueAt(0));
//
//                    setResult(RESULT_OK, intent);
//
//
                    etections();
                    finish();
                }

            }

        });
    }
    private void etections(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }
}
