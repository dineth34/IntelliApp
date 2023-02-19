package com.example.intelliapp.DashBoard;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        setUpView();

    }
    private void setUpView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
//        Toast.makeText(getApplicationContext(),String.valueOf(screenHeight) + " " + String.valueOf(screenWidth),Toast.LENGTH_SHORT).show();

        ImageView curr_position = (ImageView) findViewById(R.id.curr_position);
        final ImageView background = findViewById(R.id.background);
        ViewTreeObserver vto = background.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Get the dimensions of the ImageView here
//                curr_position.setX(background.getWidth()/2);
//                curr_position.setY(background.getHeight()/2);
                curr_position.setX(Float.parseFloat(getIntent().getExtras().get("ratioX").toString())*background.getWidth() - 10);
                curr_position.setY(Float.parseFloat(getIntent().getExtras().get("ratioY").toString())*background.getHeight() - 10);
                // Remove the listener to avoid redundant callbacks
                background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

//        Toast.makeText(getApplicationContext(),String.valueOf(Float.parseFloat(getIntent().getExtras().get("ratioX").toString())) + " " + String.valueOf(s2.getHeight()),Toast.LENGTH_SHORT).show();
    }
}
