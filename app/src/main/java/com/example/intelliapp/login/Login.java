package com.example.intelliapp.login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intelliapp.DashBoard.WelcomePage;
import com.example.intelliapp.R;
import com.example.intelliapp.signup.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<QueryDocumentSnapshot> users = new ArrayList<QueryDocumentSnapshot>();
    private EditText emailAddressInput;
    private EditText passwordInput;
    private TextView username_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_screen_layout);
        findViewById(R.id.log_in).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);
        emailAddressInput = (EditText)this.findViewById(R.id.emailAddressInput);
        passwordInput = (EditText)this.findViewById(R.id.passwordInput);
//        username_welcome = (TextView) this.findViewById(R.id.welcome_username);
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add(document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_in:
                Intent intent1=new Intent(this, WelcomePage.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                int index = -1;
                for (QueryDocumentSnapshot queryDocumentSnapshot : users){
                    if (queryDocumentSnapshot.get("email").toString().equals(emailAddressInput.getText().toString())){
                        index = users.indexOf(queryDocumentSnapshot);
                    }
                }
                if(index != -1 && users.get(index).get("password").toString().equals(passwordInput.getText().toString())){
//                    username_welcome.setText(users.get(index).get("username").toString());
                    intent1.putExtra("username",users.get(index).get("username").toString());
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                }else{
                    Toast.makeText(getApplicationContext(),"Password Incorrect",Toast.LENGTH_SHORT).show();
                }


//                Toast.makeText(getApplicationContext(),emailAddressInput.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_up:
                Intent intent2=new Intent(this, SignUp.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                overridePendingTransition(0,0);
                break;
            default:
                break;
        }
    }
}
