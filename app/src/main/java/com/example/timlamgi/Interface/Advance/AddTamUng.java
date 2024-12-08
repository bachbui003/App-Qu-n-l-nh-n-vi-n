package com.example.timlamgi.Interface.Advance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timlamgi.CheckInfor;
import com.example.timlamgi.Database.DBHelper;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Database.DBTamUng;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.Model.TamUng;
import com.example.timlamgi.R;

import java.util.ArrayList;
import java.util.Calendar;

import java.text.NumberFormat;
import java.util.Locale;

public class AddTamUng extends AppCompatActivity {
    DBHelper dbHelper;

    EditText txtSophieu, txtSoTien, txtNgayUng;
    TextView tvMaNhanVien, tvTenNhanVien;
    Calendar calendar;
    int year, month, day;
    Button btnTamUng;
    CheckInfor checkError = new CheckInfor(AddTamUng.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tam_ung);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtSophieu=findViewById(R.id.txtSoPhieu);
        txtSoTien= findViewById(R.id.txtSoTienUng);
        txtNgayUng = findViewById(R.id.txtNgayUng);
        tvMaNhanVien= findViewById(R.id.tvMaNV);
        tvTenNhanVien = findViewById(R.id.tvHoTen);
        btnTamUng = findViewById(R.id.btnthemTU);
        calendar =Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        showDate(year, month + 1, day);
        String manv =getIntent().getExtras().getString("ma");
        DBNhanVien dbNhanVien =new DBNhanVien(getApplicationContext());

        ArrayList<NhanVien> nhanViens=dbNhanVien.LayNhanVien(manv);
        tvMaNhanVien.setText(nhanViens.get(0).getMaNV());
        tvTenNhanVien.setText(nhanViens.get(0).getTenNV());


        btnTamUng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());

                DBTamUng dbTamUng = new DBTamUng(getApplicationContext());
                boolean check = dbTamUng.checkSoPhieu(txtSophieu.getText().toString());
                boolean check1= dbTamUng.checkTamUng(txtNgayUng.getText().toString(), tvMaNhanVien.getText().toString());

                float heSoLuong = 0;

                // Lấy hệ số lương từ cơ sở dữ liệu
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT hesoluong FROM NhanVien WHERE manv = ?", new String[]{tvMaNhanVien.getText().toString()});

                if (cursor.moveToFirst()) {
                    heSoLuong = cursor.getFloat(0); // Lấy giá trị hệ số lương
                }
                cursor.close();

                // Tính tổng lương
                float tongluong = heSoLuong * 26; // Giả sử 26 ngày công
                float gioiHanUng = tongluong / 2 ;
                NumberFormat currencyVN = NumberFormat.getInstance(new Locale("vi", "VN"));
                String formattedgioiHanUng = currencyVN.format(gioiHanUng); // Định dạng số tiền

                int soTienUng = Integer.parseInt(txtSoTien.getText().toString());
                if (txtSophieu.getText().toString().isEmpty() || txtSoTien.getText().toString().isEmpty()) {
                    checkError.checkEmpty(txtSophieu, "Hãy nhập mã tạm ứng");
                    checkError.checkEmpty(txtSoTien, "Hãy nhập số tiền");
                } else if (check == true) {
                    txtSophieu.setError("Mã đã tồn tại");
                    txtSophieu.isFocused();
                } else if (check1 == true) {
                    Toast.makeText(getApplicationContext(), "Nhân viên đã tạm ứng trong tháng này rồi", Toast.LENGTH_SHORT).show();
                }else if (soTienUng > gioiHanUng) {
                    Toast.makeText(getApplicationContext(), "Nhân viên này không thể ứng quá " + formattedgioiHanUng + " VNĐ!", Toast.LENGTH_SHORT).show();

                }

                else {
                    themTamUng();
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTamUng.this, MainActivityTamUng.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    private void themTamUng() {
        TamUng tamUng = new TamUng();
        tamUng.setSoPhieu(txtSophieu.getText().toString());
        tamUng.setMaNV(tvMaNhanVien.getText().toString());
        tamUng.setNgayTamUng(txtNgayUng.getText().toString());
        tamUng.setSoTienUng(txtSoTien.getText().toString());
        DBTamUng dbTamUng = new DBTamUng(getApplicationContext());
        dbTamUng.themTamUng(tamUng);

    }

    private void showDate(int year, int month, int day) {
        txtNgayUng.setText(new StringBuilder().append(day > 9 ? day : "0" + day).append("/").append(month > 9 ?
                month : "0" + month).append("/").append(year));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}