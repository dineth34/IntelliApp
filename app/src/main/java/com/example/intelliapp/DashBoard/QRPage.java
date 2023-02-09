package com.example.intelliapp.DashBoard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.intelliapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;


public class QRPage extends AppCompatActivity {

    private SurfaceView surfaceView;
    private static final int REQUEST_CODE = 1;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_page_layout);

        surfaceView = findViewById(R.id.surfaceView);

        initQRScanner();
    }

    // Method to initialize the barcode detector and camera source
    private void initQRScanner() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QRPage.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(QRPage.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
//                Log.e("QR Value", "SCANNED VALUE: " + barcodes.valueAt(0).displayValue + " " + barcodes.valueAt(barcodes.size()-1).displayValue);
                try {
                    // Code that might throw an exception
                    String barcodeValue = "";

                    // Do something with the detected barcodes
                    if (barcodes.size() >= 1){
                        barcodeValue = barcodes.valueAt(0).displayValue;
                    }

//                    // If a barcode is detected, finish the activity
//                    if (!barcodeValue.isEmpty()) {
//                        Log.e("QR Value", "SCANNED VALUE: " + barcodeValue);
//                        finish();
//                    }
                    if (barcodes.size() != 0) {
                        Intent intent1=new Intent(getBaseContext(), MapScreen.class);
                        intent1.putExtra("barcode", barcodeValue);
                        setResult(RESULT_OK, intent1);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
//                        Intent intent = new Intent();
//                        intent.putExtra("barcode", barcodes.valueAt(0));
//                        setResult(RESULT_OK, intent);
//                        Toast.makeText(getApplicationContext(),barcodeValue,Toast.LENGTH_SHORT).show();

                        Log.e("QR Value", "SCANNED VALUE: " + barcodeValue);

//                        finish();
                    }
                } catch (Exception e) {
                    Log.e("QR Code Scanner", "An error occurred while scanning the QR code: " + e.getMessage());
                }

            }
        });
    }

    private void setUpView() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenWidth = displayMetrics.widthPixels;
//        int screenHeight = displayMetrics.heightPixels;
//        Toast.makeText(getApplicationContext(),String.valueOf(screenHeight) + " " + String.valueOf(screenWidth),Toast.LENGTH_SHORT).show();
//
//        ImageView s = (ImageView) findViewById(R.id.curr_position);
//        s.setX(screenWidth/2);
//        s.setY(screenHeight/2);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1=new Intent(this, WelcomePage.class);
        intent1.putExtra("username",getIntent().getExtras().get("username").toString());
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        overridePendingTransition(0,0);
    }

}