package com.example.timlamgi.Interface.CheckOut;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timlamgi.CheckInfor;
import com.example.timlamgi.Database.DBChamCong;
import com.example.timlamgi.Database.DBNVChamCong;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Model.ChamCong;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddChamCong extends AppCompatActivity {
    TextView tvMaNhanVien, tvTenNhanVien;
    EditText txtSoNgayCong, txtNgayChamCong;
    Button btnAdd;
    Calendar calendar;
    int year, month, day;
    ArrayList<NhanVien> dataNV = new ArrayList<>();
    CheckInfor checkError = new CheckInfor(AddChamCong.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cham_cong);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvMaNhanVien = findViewById(R.id.tvMaNV);
        tvTenNhanVien=findViewById(R.id.tvHoTen);
        txtNgayChamCong =findViewById(R.id.txtNgayChamCong);
        txtSoNgayCong=findViewById(R.id.txtSoNgayCong);
        btnAdd =findViewById(R.id.btnthemCC);
        calendar =Calendar.getInstance();
        year =calendar.get(Calendar.YEAR);
        month= calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);

//        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        //        String goal = outFormat.format(day);

        String pattern = "EEEE MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        Log.d("dayname", date+" "+day);

        showDate(year, month + 1, day);
        String manv = getIntent().getExtras().getString("ma");
        DBNhanVien dbNhanVien= new DBNhanVien(this);
        dataNV = dbNhanVien.LayNhanVien(manv);
        tvMaNhanVien.setText(dataNV.get(0).getMaNV());
        tvTenNhanVien.setText(dataNV.get(0).getTenNV());

        //Lấy số công nhân viên
        DBNVChamCong dbnvChamCong = new DBNVChamCong(this);
        int soCongNV = dbnvChamCong.LaySoCongNV(manv);
        Log.d("soCong",soCongNV+"");
        String soCong = Integer.toString(soCongNV);
        txtSoNgayCong.setText(soCong);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBChamCong dbChamCong = new DBChamCong(getApplicationContext());
                boolean check = dbChamCong.checkChamCong(txtNgayChamCong.getText().toString(), tvMaNhanVien.getText().toString());
                if (txtNgayChamCong.getText().toString().isEmpty() || txtSoNgayCong.getText().toString().isEmpty()) {
                    checkError.checkEmpty(txtNgayChamCong, "Hãy nhập ngày");
                    checkError.checkEmpty(txtSoNgayCong, "Hãy nhập ngày công");
                } else if (check == true) {
                    Toast.makeText(getApplicationContext(), "Nhân viên này đã chấm công rồi", Toast.LENGTH_SHORT).show();

                } else {

                    int soCong = Integer.parseInt(txtSoNgayCong.getText().toString());
                    if (soCong>30) {
                        Toast.makeText(getApplicationContext(), "Bạn đã nhập quá công!", Toast.LENGTH_SHORT).show();
                    }else if(soCong < 0){
                        Toast.makeText(getApplicationContext(), "Không nhập số âm!", Toast.LENGTH_SHORT).show();
                    }else {
                        themChamCong();
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddChamCong.this, MainActivityChamCong.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });


    }

    private void themChamCong() {
        ChamCong chamCong = new ChamCong();
        chamCong.setMaNV(tvMaNhanVien.getText().toString());
        chamCong.setThangChamCong(txtNgayChamCong.getText().toString());
        chamCong.setSoNgayCong(txtSoNgayCong.getText().toString());
        DBChamCong dbChamCong = new DBChamCong(getApplicationContext());
        dbChamCong.themChamCong(chamCong);

    }

    private void showDate(int year, int month, int day) {
        txtNgayChamCong.setText(new StringBuilder().append(day > 9 ? day : "0"+day).append("/").append(month > 9 ?
                month : "0" + month).append("/").append(year));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}