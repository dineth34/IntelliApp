package com.example.intelliapp.DashBoard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intelliapp.R;
import com.example.intelliapp.login.Login;

import java.util.ArrayList;


public class MapList extends AppCompatActivity {
    private ListView lvItem;
    private ArrayList<String> itemArray;
    private ArrayAdapter<String> itemAdapter;
    int selected=0;

    ArrayList allLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_layout);
        setUpView();
    }

    private void setUpView() {
        lvItem = (ListView)this.findViewById(R.id.listView_open);
        allLists = new ArrayList<>();
        itemArray = new ArrayList<String>();
        itemArray.clear();

        itemArray.add("Sumanadasa Building 2nd Floor");
        itemArray.add("Goda Canteen");
        itemArray.add("L Canteen");
        itemArray.add("Library Ground Floor");

        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemArray);
        lvItem.setAdapter(itemAdapter);

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