package com.example.timlamgi.Interface.TotalSalary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.timlamgi.R;

import java.text.NumberFormat;
import java.util.Locale;

public class ChiTietTongLuong extends AppCompatActivity {

    TextView tvMaNV, tvTenNV, tvNgayChamCong, tvTenPhongBan, tvHeSoLuong, tvSoNgayCong, tvTamUng, tvLuongCoBan, tvThucLanh;
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tong_luong);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvMaNV = findViewById(R.id.tvMaNhanVien);
        tvTenNV = findViewById(R.id.tvTenNV);
        tvNgayChamCong = findViewById(R.id.tvNgayChamCong);
        tvTenPhongBan = findViewById(R.id.tvTenPhongBan);
        tvHeSoLuong = findViewById(R.id.tvHeSoLuong);
        tvSoNgayCong = findViewById(R.id.tvSoNgayCong);
        tvTamUng = findViewById(R.id.tvTamUng);
        tvLuongCoBan = findViewById(R.id.tvLuongCB);
        tvThucLanh = findViewById(R.id.tvThucLanh);

        Intent intent = getIntent();
        String tenItem = intent.getStringExtra("tenItem");
        String idItem = intent.getStringExtra("idItem");
        String pbItem = intent.getStringExtra("pbItem");
        String hslItem = intent.getStringExtra("hslItem");
        String soNgayCongItem = intent.getStringExtra("soNgayCongItem");
        String tamUngItem =intent.getStringExtra("tamUngItem");
        String ngayChamCongItem =intent.getStringExtra("ngayChamCongItem");
        String luongCoBanItem =intent.getStringExtra("luongCoBanItem");
        String luongThucLanhItem = intent.getStringExtra("luongThucLanhItem");

//        TextView tvMaNV, tvTenNV, tvNgayChamCong, tvTenPhongBan, tvHeSoLuong, tvSoNgayCong, tvTamUng, tvLuongCoBan, tvThucLanh;
        tvMaNV.setText(idItem);
        tvTenNV.setText(tenItem);
        tvNgayChamCong.setText(ngayChamCongItem);
        tvTenPhongBan.setText(pbItem);
        tvHeSoLuong.setText(currencyVN.format(Integer.parseInt(hslItem)));
        tvSoNgayCong.setText(soNgayCongItem);
        if (tamUngItem != null) {
            tvTamUng.setText(currencyVN.format(Integer.parseInt(tamUngItem)));
        }else {
            tamUngItem = "0";
            tvTamUng.setText(currencyVN.format(Integer.parseInt(tamUngItem)));
        }
        tvLuongCoBan.setText(currencyVN.format(Integer.parseInt(luongCoBanItem)));
        tvThucLanh.setText(currencyVN.format(Integer.parseInt(luongThucLanhItem)));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}