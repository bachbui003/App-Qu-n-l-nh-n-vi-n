package com.example.timlamgi.Interface.CheckOut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.timlamgi.Adapter.AdapterChamCong;
import com.example.timlamgi.Database.DBChamCong;
import com.example.timlamgi.Interface.MainActivityHome;
import com.example.timlamgi.Model.ChamCong;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class MainActivityChamCong extends AppCompatActivity {
    ListView lvChamCong;
    AdapterChamCong adapter_chamcong;
    ArrayList<ChamCong> data_chamcong= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cham_cong);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lvChamCong = findViewById(R.id.lvChamCong);

        DBChamCong dbChamCong = new DBChamCong(getApplicationContext());
        data_chamcong=dbChamCong.layDuLieuCC();
        adapter_chamcong= new AdapterChamCong(MainActivityChamCong.this, R.layout.custom_chamcong, data_chamcong);
        adapter_chamcong.notifyDataSetChanged();
        lvChamCong.setAdapter(adapter_chamcong);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.btnHome){
            Intent intent = new Intent(MainActivityChamCong.this, MainActivityHome.class);
            startActivity(intent);
        }else {
            onBackPressed();
        }

        return true;
    }
}