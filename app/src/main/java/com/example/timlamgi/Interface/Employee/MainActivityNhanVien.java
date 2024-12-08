package com.example.timlamgi.Interface.Employee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.timlamgi.Adapter.AdapterNhanVien;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Interface.MainActivityHome;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class MainActivityNhanVien extends AppCompatActivity {

    Button btnThem, btnFind;
    EditText edtInput;
    ListView lvDanhSachNV;
    AdapterNhanVien adapterNV;
    ArrayList<NhanVien> dataNV = new ArrayList<>();
    DBNhanVien dbNhanVien;

    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnThem=findViewById(R.id.btnThem);
        btnFind=findViewById(R.id.btnTim);
        edtInput=findViewById(R.id.edtInput);
        lvDanhSachNV = findViewById(R.id.lvNhanVien);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dbNhanVien = new DBNhanVien(this);

        HienThiDL();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNhanVien.class);
                startActivity(intent);
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataNV.clear();
                String getInput=edtInput.getText().toString();
                dataNV = dbNhanVien.getDataByID(getInput);
                adapterNV =new AdapterNhanVien(MainActivityNhanVien.this, R.layout.custom_nhanvien, dataNV);
                adapterNV.notifyDataSetChanged();
                lvDanhSachNV.setAdapter(adapterNV);

                if (dataNV.size()==0 || edtInput.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Không Tìm Thấy!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void HienThiDL() {
        DBNhanVien dbNhanVien = new DBNhanVien(this);
        dataNV = dbNhanVien.LayDSNhanVien();
        adapterNV =new AdapterNhanVien(MainActivityNhanVien.this, R.layout.custom_nhanvien, dataNV);
        adapterNV.notifyDataSetChanged();
        lvDanhSachNV.setAdapter(adapterNV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
//        getMenuInflater().inflate(R.menu.menu_all, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityNhanVien.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát không ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
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
            Intent intent = new Intent(MainActivityNhanVien.this, MainActivityHome.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}