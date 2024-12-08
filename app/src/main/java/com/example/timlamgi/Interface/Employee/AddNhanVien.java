package com.example.timlamgi.Interface.Employee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.timlamgi.CheckInfor;
import com.example.timlamgi.Database.DBAccount;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Database.DBPhongBan;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AddNhanVien extends AppCompatActivity {

    final int RESQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    Calendar calendar;
    int year, month, day;
    EditText edtMaNV, edtTenNV, edtNgaySinh, edtLuong, edtSdt, edtMk;
    RadioButton radNam, radNu;
    Spinner spPhongBan;
    ImageView imgAnhDaiDien;
    Button btnAdd, btnChonHinh;

    DBPhongBan dbPhongBan;
    CheckInfor checkError = new CheckInfor(AddNhanVien.this);
    ArrayList<String> dsPhong;
    ArrayAdapter dataPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        edtMaNV = findViewById(R.id.txtMaNV);
        edtTenNV = findViewById(R.id.txtTenNV);
        edtNgaySinh = findViewById(R.id.txtNgaySinh);
        radNam = findViewById(R.id.radNam);
        radNu = findViewById(R.id.radNu);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        spPhongBan = findViewById(R.id.spTenPB);
        imgAnhDaiDien = findViewById(R.id.imgHinhDaiDien);
        edtLuong = findViewById(R.id.edtLuong);
        edtSdt = findViewById(R.id.txtSDT);
        edtMk = findViewById(R.id.txtPassword);

        btnAdd = findViewById(R.id.btnThem);
        imgAnhDaiDien.setImageResource(R.drawable.man);

        dbPhongBan = new DBPhongBan(this);
        dsPhong=dbPhongBan.LayDSPhong();
        dataPhong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsPhong);
        spPhongBan.setAdapter(dataPhong);
        showDate(year, month + 1, day);



        imgAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBNhanVien dbNhanVien = new DBNhanVien(getApplicationContext());
                boolean check = dbNhanVien.checkMaNhanVien(edtMaNV.getText().toString());
                DBAccount dbAccount = new DBAccount(getApplicationContext());
                boolean checkSdt = dbAccount.checkSdtNhanvien(edtSdt.getText().toString());

                Log.d("edtmanv", edtMaNV.getText().toString().isEmpty()+"");

                if (edtMaNV.getText().toString().isEmpty() ||
                        edtTenNV.getText().toString().isEmpty() ||
                        edtLuong.getText().toString().isEmpty() ||
                        edtSdt.getText().toString().isEmpty() ||
                        edtMk.getText().toString().isEmpty()) {
                    checkError.checkEmpty(edtMaNV,"Hãy nhập mã nhân viên");
                    checkError.checkEmpty(edtTenNV,"Hãy nhập tên nhân viên");
                    checkError.checkEmpty(edtLuong,"Hãy nhập hệ số lương");
                    checkError.checkEmpty(edtSdt,"Hãy nhập số điện thoại");
                    checkError.checkEmpty(edtMk,"Hãy nhập mật khẩu");

                } else if (check == true) {
                    edtMaNV.setError("Mã nhân viên đã tồn tại");
                    edtMaNV.isFocused();
                }else if(checkSdt == true) {
                    edtSdt.setError("SDT đã tồn tại");
                    edtSdt.isFocused();
                } else {
                    if (spPhongBan.getCount()==0) {
                        Toast.makeText(getApplication(), "Hãy thêm phòng ban", Toast.LENGTH_SHORT).show();
                    }else {
                        ThemDL();
                        Toast.makeText(AddNhanVien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddNhanVien.this, MainActivityNhanVien.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void ThemDL() {
        DBNhanVien dbNhanVien = new DBNhanVien(getApplicationContext());
        NhanVien nhanVien = new NhanVien();
        String sdt = edtSdt.getText().toString();
        String matkhau = edtMk.getText().toString();
        String manv = edtMaNV.getText().toString();
        nhanVien.setMaNV(manv);
        nhanVien.setTenNV(edtTenNV.getText().toString());
        nhanVien.setSdt(sdt);
        nhanVien.setMatkhau(matkhau);
        nhanVien.setNgaySinh(edtNgaySinh.getText().toString());
        if (radNu.isChecked() == true) {
            nhanVien.setGioiTinh("Nữ");
            radNam.setChecked(false);
        }
        if (radNam.isChecked() == true) {
            nhanVien.setGioiTinh("Nam");
        }

            String maPhong = dbNhanVien.layMaPhong(spPhongBan.getSelectedItem().toString());
            nhanVien.setMaPhong(maPhong);
            nhanVien.setBacLuong(edtLuong.getText().toString());
            byte[] image = getByteArrayFromImageView(imgAnhDaiDien);
            nhanVien.setImage(image);

            dbNhanVien.Them(nhanVien);

            //Thêm account
            new DBAccount(getApplicationContext()).themAccount(sdt, matkhau, manv, new DBAccount.onClickListenerRs() {
                @Override
                public void success() {
//                    Toast.makeText(getApplicationContext(), "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void fail() {
//                    Toast.makeText(getApplicationContext(), "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 80, 100, true);
                    imgAnhDaiDien.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = Bitmap.createScaledBitmap(bitmap, 80, 100, true);
                imgAnhDaiDien.setImageBitmap(bitmap);
            }
        }
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }
    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }

    private void showDate(int year, int month, int day) {
        edtNgaySinh.setText(new StringBuilder().append("0").append("/").append(
                "0").append("/").append(year));
    }

    private byte[] getByteArrayFromImageView(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        bmp = Bitmap.createScaledBitmap(bmp, 80, 80, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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