package com.example.intelliapp.DashBoard;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intelliapp.R;

public class MapScreen extends AppCompatActivity {
    private TextView scanned_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_screen_layout);
        scanned_text = (TextView) this.findViewById(R.id.scanned_text);
        scanned_text.setText(getIntent().getExtras().get("barcode").toString());
        Log.e("QR Value", "SCANNED VALUE : Map Page " + getIntent().getExtras().get("barcode").toString());

    }
}
