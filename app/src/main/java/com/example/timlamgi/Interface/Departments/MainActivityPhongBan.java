package com.example.timlamgi.Interface.Departments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.timlamgi.Adapter.AdapterPB;
import com.example.timlamgi.Database.DBPhongBan;
import com.example.timlamgi.Interface.MainActivityHome;
import com.example.timlamgi.Model.PhongBan;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class MainActivityPhongBan extends AppCompatActivity {

    Button btnThem;
    ArrayList<PhongBan> data_PB = new ArrayList<>();
    ListView lvPhongBan;
    AdapterPB adapter_PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phong_ban);
        btnThem=findViewById(R.id.btnThem);
        lvPhongBan =findViewById(R.id.lvPhongBan);
        setEvent();
    }

    private void setEvent() {
        HienThiDL();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityPhongBan.this, AddPhongBan.class);
                startActivity(intent);
            }
        });
    }

    private void HienThiDL() {
        DBPhongBan dbPhongBan = new DBPhongBan(this);
        data_PB= dbPhongBan.LayDL();
        adapter_PB = new AdapterPB(MainActivityPhongBan.this, R.layout.custom_phongban, data_PB);
        lvPhongBan.setAdapter(adapter_PB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityPhongBan.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát không ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog1 = builder.create();
            alertDialog1.show();
        }else {
            Intent intent = new Intent(MainActivityPhongBan.this, MainActivityHome.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}