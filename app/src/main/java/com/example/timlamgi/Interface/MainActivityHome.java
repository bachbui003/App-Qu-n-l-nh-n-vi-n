package com.example.timlamgi.Interface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.timlamgi.Interface.CheckOut.MainActivityChamCong;
import com.example.timlamgi.Interface.Employee.MainActivityNhanVien;
import com.example.timlamgi.Interface.Departments.MainActivityPhongBan;
import com.example.timlamgi.Interface.Advance.MainActivityTamUng;
import com.example.timlamgi.Interface.Task.MainActivityTask;
import com.example.timlamgi.Interface.TotalSalary.MainActivityTongLuong;
import com.example.timlamgi.R;

public class MainActivityHome extends AppCompatActivity {

    Button btnDSNhanVien, btnDanhSachPB, btnDSChamCong, btnDSTamUng, btnThongKe, btnTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDSNhanVien = findViewById(R.id.btnDSNhanVien);
        btnDanhSachPB = findViewById(R.id.btnDepartment);
        btnDSChamCong = findViewById(R.id.btnDSChamCong);
        btnDSTamUng=findViewById(R.id.btnDSTamUng);
        btnThongKe=findViewById(R.id.btnThongKe);
        btnTask = findViewById(R.id.btnTask);

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityTask.class);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityTongLuong.class);
                startActivity(intent);
            }
        });
        btnDSNhanVien.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(getApplicationContext(), MainActivityNhanVien.class);
             startActivity(intent);
             Log.e("click", "onClick");
         }
     });

        btnDanhSachPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityPhongBan.class);
                startActivity(intent);
            }
        });

        btnDSChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityHome.this, MainActivityChamCong.class);
                startActivity(intent);
            }
        });

        btnDSTamUng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityTamUng.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityHome.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát không ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(),MainActivityLogin.class);
                    startActivity(intent);
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
        }
        return super.onOptionsItemSelected(item);
    }

}