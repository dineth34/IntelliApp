package com.example.intelliapp.DashBoard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intelliapp.R;
import com.example.intelliapp.login.Login;

public class WelcomePage extends AppCompatActivity implements View.OnClickListener {
    private TextView username_welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page_layout);
        findViewById(R.id.viewMaps).setOnClickListener(this);
        username_welcome = (TextView) this.findViewById(R.id.welcome_username);
        username_welcome.setText(getIntent().getExtras().get("username").toString());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1=new Intent(this, Login.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        overridePendingTransition(0,0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewMaps:
                Intent intent1=new Intent(this, QRPage.class);
                intent1.putExtra("username",getIntent().getExtras().get("username").toString());
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