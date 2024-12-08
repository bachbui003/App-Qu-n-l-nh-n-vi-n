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
import android.widget.TextView;
import android.widget.Toast;

import com.example.timlamgi.CheckInfor;
import com.example.timlamgi.Database.DBAccount;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Database.DBPhongBan;
import com.example.timlamgi.Model.Accounts;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class UpdateNhanVien extends AppCompatActivity {

    final int RESQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    EditText edtTenNV, edtNgaySinh, edtLuong, edtSDT, edtMk;
    TextView tvMa;
    Button btnUpdate, btnChonHinh;
    RadioButton radNam, radNu;
    Spinner spPhongBan;
    ImageView imgAnhDaiDien;
    ArrayList<NhanVien> dataNV = new ArrayList<>();
    DBPhongBan dbPhongBan;
    ArrayList<String> dsPhong;
    ArrayAdapter adapterPB;

    CheckInfor checkError = new CheckInfor(UpdateNhanVien.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nhan_vien);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvMa = findViewById(R.id.tvMaNV);
        edtTenNV = findViewById(R.id.txtTenNV);
        edtNgaySinh = findViewById(R.id.txtNgaySinh);
        radNam = findViewById(R.id.radNam);
        radNu = findViewById(R.id.radNu);
        spPhongBan = findViewById(R.id.spTenPB);
        edtLuong = findViewById(R.id.edtLuong);
        edtSDT = findViewById(R.id.txtSDTnv);
        edtMk = findViewById(R.id.txtPasswordnv);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        imgAnhDaiDien = findViewById(R.id.imgHinhDaiDien);

        String gioiTinh, phongBan;
        dbPhongBan = new DBPhongBan(getApplicationContext());
        dsPhong= dbPhongBan.LayDSPhong();
        adapterPB = new ArrayAdapter<>(UpdateNhanVien.this, android.R.layout.simple_spinner_item, dsPhong);
        spPhongBan.setAdapter(adapterPB);

        String masv = getIntent().getExtras().getString("manv");
        DBNhanVien dbNhanVien  =new DBNhanVien(this);
        dataNV = dbNhanVien.LayNhanVien(masv);
        tvMa.setText(dataNV.get(0).getMaNV());
        edtTenNV.setText(dataNV.get(0).getTenNV());
        edtSDT.setText(dataNV.get(0).getSdt());
        edtMk.setText(dataNV.get(0).getMatkhau());
        edtNgaySinh.setText(dataNV.get(0).getNgaySinh());
        gioiTinh = dataNV.get(0).getGioiTinh();
        if (gioiTinh.equals("Nam")) {
            radNam.setChecked(true);
        }
        if (gioiTinh.equals("Nữ")){
            radNu.setChecked(true);
        }

        String tenPb = dbNhanVien.layTenPhong(dataNV.get(0).getMaPhong());
        selectValue(spPhongBan, tenPb);

        edtLuong.setText(dataNV.get(0).getBacLuong());
        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(dataNV.get(0).getImage(), 0, dataNV.get(0).getImage().length);
        imgAnhDaiDien.setImageBitmap(bmHinhDaiDien);

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTenNV.getText().toString().isEmpty() ||edtLuong.getText().toString().isEmpty() || edtSDT.getText().toString().isEmpty() || edtMk.getText().toString().isEmpty()) {
                    checkError.checkEmpty(edtTenNV,"Hãy nhập tên nhân viên");
                    checkError.checkEmpty(edtLuong,"Hãy nhập hệ số lương");
                    checkError.checkEmpty(edtSDT,"Hãy nhập số điện thoại");
                    checkError.checkEmpty(edtMk,"Hãy nhập mật khẩu");

                } else {
//                    DBAccount dbAccount = new DBAccount(getApplicationContext());
//                    boolean checkSdt = dbAccount.checkSdtNhanvien(edtSDT.getText().toString());
//                    if (checkSdt == true) {
//                        edtSDT.setError("SDT đã tồn tại");
//                        edtSDT.isFocused();
//                    }else {
                        UpdateDL();
                        Toast.makeText(UpdateNhanVien.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateNhanVien.this, MainActivityNhanVien.class);
                        startActivity(intent);
                        finish();
//                    }

                }
            }
        });
    }

    private void UpdateDL() {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(tvMa.getText().toString());
        nhanVien.setTenNV(edtTenNV.getText().toString());

        nhanVien.setSdt(edtSDT.getText().toString());
        nhanVien.setMatkhau(edtMk.getText().toString());
        nhanVien.setNgaySinh(edtNgaySinh.getText().toString());
            if (radNam.isChecked()) {
                nhanVien.setGioiTinh("Nam");
            }
            if (radNu.isChecked()) {
                nhanVien.setGioiTinh("Nữ");
            }
            byte[] image = getByteArrayFromImageView(imgAnhDaiDien);
            nhanVien.setImage(image);
//        String mapb = Integer.toString(spPhongBan.getSelectedItemPosition());

            String maPb = getMapb(spPhongBan,spPhongBan.getSelectedItemPosition());

            nhanVien.setMaPhong(maPb);

            nhanVien.setBacLuong(edtLuong.getText().toString());
            DBNhanVien dbNhanVien = new DBNhanVien(this);
            dbNhanVien.Sua(nhanVien);

            DBAccount dbAccount = new DBAccount(this);
            Accounts accounts = new Accounts();
            accounts.setManv(tvMa.getText().toString());
            accounts.setSdt(edtSDT.getText().toString());
            accounts.setPassword(edtMk.getText().toString());
            dbAccount.suaAccount(accounts);
    }

    public String getMapb(Spinner spinner,int position) {
        String maPb="";
        for (int i=0;i<=spinner.getCount();i++){
            if (i == position) {
                //Lấy tên phòng
                String tenPb = spinner.getItemAtPosition(position).toString();
                //Có tên rồi truy vấn ra mã phòng
                maPb = dbPhongBan.layMaPhong(tenPb);
                //Sau đó rồi set mã
                Log.d("maPB",maPb);
            }
        }
        return maPb;
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }
    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
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
                    bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    imgAnhDaiDien.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                imgAnhDaiDien.setImageBitmap(bitmap);
            }
        }
    }

    private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        bmp=Bitmap.createScaledBitmap(bmp, 80,80, true);
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

    //lấy vị trí phòng cần tìm trong spinner
    private void selectValue(Spinner spinner,Object value){
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(value)){
                spinner.setSelection(i);
                break;
            }
        }
    }
}