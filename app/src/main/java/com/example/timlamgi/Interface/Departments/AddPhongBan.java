package com.example.timlamgi.Interface.Departments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timlamgi.CheckInfor;
import com.example.timlamgi.Database.DBHelper;
import com.example.timlamgi.Database.DBPhongBan;
import com.example.timlamgi.Model.PhongBan;
import com.example.timlamgi.R;

public class AddPhongBan extends AppCompatActivity {

    EditText txtMaPB, txtTenPB;
    Button btnThem;
    DBHelper dbHelper;
    DBPhongBan dbPhongBan;
    CheckInfor checkInfor =new CheckInfor(AddPhongBan.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phong_ban);
        dbHelper = new DBHelper(this);
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        dbPhongBan = new DBPhongBan(this);

        setControl();
        setEvent();
    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("infor", txtMaPB.getText().toString()+"null");
                ThemDL();
            }
        });
    }

    private void ThemDL() {

        if (txtMaPB.getText().toString().isEmpty() || txtTenPB.getText().toString().isEmpty()) {
            checkInfor.checkEmpty(txtMaPB, "Hãy nhập mã phòng");
            checkInfor.checkEmpty(txtTenPB, "Hãy nhập tên phòng");
        } else {
            DBPhongBan dbPhongBan = new DBPhongBan(getApplicationContext());
            boolean check = dbPhongBan.checkMaPhong(txtMaPB.getText().toString());
            if (check == true) {
                txtMaPB.setError("Mã phòng đã tồn tại");
                txtMaPB.isFocused();
            }else {
                PhongBan phongBan = new PhongBan();
                phongBan.setMaPhong(txtMaPB.getText().toString());
                phongBan.setTenPhong(txtTenPB.getText().toString());
                dbPhongBan.Them(phongBan);
                Toast.makeText(AddPhongBan.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddPhongBan.this, MainActivityPhongBan.class);
                startActivity(intent);
            }
        }
    }

    private void setControl() {
        txtMaPB = findViewById(R.id.txtMaPhong);
        txtTenPB = findViewById(R.id.txtTenPhong);
        btnThem = findViewById(R.id.btnthemPB);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}