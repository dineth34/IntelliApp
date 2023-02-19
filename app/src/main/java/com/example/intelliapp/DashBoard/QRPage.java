package com.example.intelliapp.DashBoard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.intelliapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;


public class QRPage extends AppCompatActivity {

    private SurfaceView surfaceView;
    private static final int REQUEST_CODE = 1;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentSnapshot document;

    private String[] list;

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
                        list = barcodeValue.split("_");
                    }


                    if (list[0].equals("intelliapp") && list[1] != null){
                        DocumentReference docRef = db.collection("entries").document(list[1]);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                    } else if (list[0] != "intelliapp" ) {
//                        Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT).show();
                    }

//                    // If a barcode is detected, finish the activity
//                    if (!barcodeValue.isEmpty()) {
//                        Log.e("QR Value", "SCANNED VALUE: " + barcodeValue);
//                        finish();
//                    }
                    if (barcodes.size() != 0 && document.exists()) {
                        Intent intent1=new Intent(getBaseContext(), MapScreen.class);
                        intent1.putExtra("barcode", barcodeValue);
                        intent1.putExtra("ratioX", document.get("ratioX").toString());
                        intent1.putExtra("ratioY", document.get("ratioY").toString());
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