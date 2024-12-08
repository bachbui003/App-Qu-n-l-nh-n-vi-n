package com.example.timlamgi.Interface.TotalSalary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.timlamgi.Adapter.AdapterThongKe;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Model.ThongKe;
import com.example.timlamgi.R;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivityTongLuong extends AppCompatActivity {
    ListView lvThongKe;
    ArrayList<ThongKe> thongke = new ArrayList<>();
    AdapterThongKe adapterThongKe;
    private static final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tong_luong);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvThongKe = findViewById(R.id.lvThongKe);
        Button btnExportExcel = findViewById(R.id.btnExportExcel); // Thêm nút xuất Excel

        final DBNhanVien dbNhanVien = new DBNhanVien(getApplicationContext());
        thongke = dbNhanVien.layDSThongKe();

        adapterThongKe = new AdapterThongKe(this, R.layout.custom_tongluong, thongke);
        lvThongKe.setAdapter(adapterThongKe);

        lvThongKe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivityTongLuong.this, ChiTietTongLuong.class);
                ThongKe itemThongKe = thongke.get(position);

                intent.putExtra("tenItem", itemThongKe.getTenNV());
                intent.putExtra("idItem", itemThongKe.getMaNV());
                intent.putExtra("pbItem", itemThongKe.getTenPhong());
                intent.putExtra("hslItem", itemThongKe.getHeSoLuong());
                intent.putExtra("soNgayCongItem", itemThongKe.getSoNgayCong());
                intent.putExtra("tamUngItem", itemThongKe.getTienTamUng());
                intent.putExtra("ngayChamCongItem", itemThongKe.getNgayChamCong());
                intent.putExtra("luongCoBanItem", itemThongKe.getLuongCoBan());
                intent.putExtra("luongThucLanhItem", itemThongKe.getLuongThucLanh());

                startActivity(intent);
            }
        });

        btnExportExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivityTongLuong.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivityTongLuong.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                } else {
                    exportToExcel();
                }
            }
        });
    }

    private void exportToExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thong Ke");

        // Tạo hàng tiêu đề
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên NV");
        headerRow.createCell(1).setCellValue("Mã NV");
        headerRow.createCell(2).setCellValue("Tên Phòng");
        headerRow.createCell(3).setCellValue("Hệ Số Lương");
        headerRow.createCell(4).setCellValue("Số Ngày Công");
        headerRow.createCell(5).setCellValue("Tiền Tạm Ứng");
        headerRow.createCell(6).setCellValue("Ngày Chấm Công");
        headerRow.createCell(7).setCellValue("Lương Cơ Bản");
        headerRow.createCell(8).setCellValue("Lương Thực Lãnh");

        // Ghi dữ liệu vào file
        // Ghi dữ liệu vào file
        int rowNum = 1;
        for (ThongKe item : thongke) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getTenNV());
            row.createCell(1).setCellValue(item.getMaNV());
            row.createCell(2).setCellValue(item.getTenPhong());
            row.createCell(3).setCellValue(item.getHeSoLuong());
            row.createCell(4).setCellValue(item.getSoNgayCong());
            row.createCell(5).setCellValue(item.getTienTamUng());
            row.createCell(6).setCellValue(item.getNgayChamCong());
            row.createCell(7).setCellValue(item.getLuongCoBan());
            row.createCell(8).setCellValue(item.getLuongThucLanh());
        }

        // Lưu file
        try {
            // Đường dẫn để lưu file Excel
            String filePath = getExternalFilesDir(null) + "/ThongKe.xlsx";
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            Toast.makeText(this, "Xuất file Excel thành công: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi xuất file Excel!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportToExcel();
            } else {
                Toast.makeText(this, "Quyền truy cập bị từ chối!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
}