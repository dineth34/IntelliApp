package com.example.intelliapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.intelliapp.DashBoard.QRPage;
import com.example.intelliapp.DashBoard.Sensor;
import com.example.intelliapp.login.Login;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        findViewById(R.id.button1).setOnClickListener(this);
        Log.e("QR Code Scanner", "MAIN ACTIVITY ");
    }

    @Override
    public void onClick(View view) {
        Log.e("QR Code Scanner", "MAIN ACTIVITY ");
        switch (view.getId()) {
            case R.id.button1:
                Intent intent1=new Intent(this, Sensor.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                overridePendingTransition(0,0);
                break;
            default:
                break;
        }
    }
}