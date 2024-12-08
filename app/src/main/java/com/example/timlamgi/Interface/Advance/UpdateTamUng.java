package com.example.timlamgi.Interface.Advance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timlamgi.CheckInfor;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Database.DBTamUng;
import com.example.timlamgi.Interface.MainActivityHome;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.Model.TamUng;
import com.example.timlamgi.R;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateTamUng extends AppCompatActivity {

    EditText txtSoTien, txtNgayUng;
    TextView tvMaNhanVien, tvTenNhanVien, tvSophieu;
    Calendar calendar;
    int year, month, day;
    Button btnTamUng;
    CheckInfor checkError = new CheckInfor(UpdateTamUng.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tam_ung);
        tvSophieu=findViewById(R.id.tvSoPhieu);
        txtSoTien=findViewById(R.id.txtSoTienUng);
        tvMaNhanVien = findViewById(R.id.tvMaNV);
        tvTenNhanVien = findViewById(R.id.tvHoTen);
        txtNgayUng = findViewById(R.id.txtNgayUng);
        btnTamUng= findViewById(R.id.btnSuaTU);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        showDate(year, month + 1, day);
        String sophieu = getIntent().getExtras().getString("sophieu");
        DBTamUng dbTamUng = new DBTamUng(getApplicationContext());
        ArrayList<TamUng> tamUngs = dbTamUng.layPhieu(sophieu);
        tvSophieu.setText(tamUngs.get(0).getSoPhieu());
        txtSoTien.setText(tamUngs.get(0).getSoTienUng());
        String manv = tamUngs.get(0).getMaNV();
        DBNhanVien dbNhanVien = new DBNhanVien(getApplicationContext());
        ArrayList<NhanVien> nhanViens = dbNhanVien.LayNhanVien(manv);
        tvMaNhanVien.setText(nhanViens.get(0).getMaNV());
        tvTenNhanVien.setText(nhanViens.get(0).getTenNV());

        btnTamUng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtSoTien.getText().toString().isEmpty()) {
                    checkError.checkEmpty(txtSoTien, "Vui lòng nhập số tiền");
                } else {
                    suaTamUng();
                    Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateTamUng.this, MainActivityTamUng.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showDate(int year, int month, int day) {
        txtNgayUng.setText(new StringBuilder().append(day > 9 ? day : "0" + day).append("/").append(month > 9 ?
                month : "0" + month).append("/").append(year));
    }

    private void suaTamUng() {
        TamUng tamUng = new TamUng();
        tamUng.setSoPhieu(tvSophieu.getText().toString());
        tamUng.setNgayTamUng(txtNgayUng.getText().toString());
        tamUng.setMaNV(tvMaNhanVien.getText().toString());
        tamUng.setSoTienUng(txtSoTien.getText().toString());
        DBTamUng dbTamUng = new DBTamUng(getApplicationContext());
        dbTamUng.suaTamUng(tamUng);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.btnHome){
            Intent intent = new Intent(UpdateTamUng.this, MainActivityHome.class);
            startActivity(intent);
        }
        return true;
    }
}