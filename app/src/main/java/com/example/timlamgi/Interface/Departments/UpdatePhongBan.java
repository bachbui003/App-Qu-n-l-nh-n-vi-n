package com.example.timlamgi.Interface.Departments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timlamgi.CheckInfor;
import com.example.timlamgi.Database.DBPhongBan;
import com.example.timlamgi.Model.PhongBan;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class UpdatePhongBan extends AppCompatActivity {

    EditText txtTenPB;
    TextView tvMaPB;
    Button btnSua;
    ArrayList<PhongBan> data = new ArrayList<>();
    CheckInfor checkError = new CheckInfor(UpdatePhongBan.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phong_ban);
        txtTenPB=findViewById(R.id.txtTenPhongS);
        tvMaPB = findViewById(R.id.tvMaPhongS);
        btnSua=findViewById(R.id.btnSuaPB);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String maPB = getIntent().getExtras().getString("ma");
        DBPhongBan dbPhongBan = new DBPhongBan(this);
        data = dbPhongBan.LayDL(maPB);
        tvMaPB.setText(data.get(0).getMaPhong());
        txtTenPB.setText(data.get(0).getTenPhong());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaDL();
            }
        });
    }

    private void SuaDL() {
        if (txtTenPB.getText().toString().isEmpty()) {
            checkError.checkEmpty(txtTenPB, "Hãy nhập tên phòng");
        } else {
            PhongBan phongBan = new PhongBan();
            phongBan.setMaPhong(tvMaPB.getText().toString());
            phongBan.setTenPhong(txtTenPB.getText().toString());
            DBPhongBan dbPhongBan = new DBPhongBan(getApplicationContext());
            dbPhongBan.Sua(phongBan);
            Toast.makeText(UpdatePhongBan.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdatePhongBan.this, MainActivityPhongBan.class);
            startActivity(intent);
            finish();
        }
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