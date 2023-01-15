package com.example.intelliapp.signup;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intelliapp.R;
import com.example.intelliapp.login.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText usernameInput;
    private EditText emailAddressInput;
    private EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        findViewById(R.id.register).setOnClickListener(this);
        usernameInput = (EditText)this.findViewById(R.id.usernameInput);
        emailAddressInput = (EditText)this.findViewById(R.id.emailAddressInput);
        passwordInput = (EditText)this.findViewById(R.id.passwordInput);
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
        switch (view.getId()){
            case R.id.register:
                Intent intent2=new Intent(this, Login.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Map<String, Object> data = new HashMap<>();
                data.put("username", usernameInput.getText().toString());
                data.put("email", emailAddressInput.getText().toString());
                data.put("password",passwordInput.getText().toString());
                db.collection("users")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_SHORT).show();
                                startActivity(intent2);
                                overridePendingTransition(0,0);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;
        }
    }
}
