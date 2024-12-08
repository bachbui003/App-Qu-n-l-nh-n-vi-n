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
import android.widget.TextView;
import android.widget.Toast;

import com.example.timlamgi.Database.DBChamCong;
import com.example.timlamgi.Database.DBNVChamCong;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Interface.Task.ListTaskNV;
import com.example.timlamgi.Model.NVChamCong;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivityAccounts extends AppCompatActivity {

    Button btnCheckin, btnCheckout, btnNVTask;

    TextView txtTennv, txtManv, txtPhongban, txtNgaythang;
    ArrayList<NhanVien> dataNV = new ArrayList<>();

    Calendar calendar;
    int year, month, day;
    int hour, minute, second;
    int soCong = 0;
    Calendar currentTime;
    DBChamCong dbChamCong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_accounts);
        btnCheckin = findViewById(R.id.btnCheckin);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnNVTask = findViewById(R.id.btnNVTask);

        txtTennv = findViewById(R.id.tvTennv);
        txtManv = findViewById(R.id.tvManv);
        txtPhongban = findViewById(R.id.tvTenPhongBannv);
        txtNgaythang = findViewById(R.id.tvNgayChamCongnv);
        calendar =Calendar.getInstance();
        year =calendar.get(Calendar.YEAR);
        month= calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        dbChamCong = new DBChamCong(this);
        showDate(year, month + 1, day);
        String timeNow = getTimeNow(year, month + 1, day);
//        Log.d("timeNow",timeNow);

        String sdt = getIntent().getExtras().getString("sdt");
//        Log.d("sdt", sdt);
        DBNhanVien dbNhanVien  =new DBNhanVien(this);
        dataNV = dbNhanVien.LayNVBySDT(sdt);
        //Lưu trạng thái
        int isCheckin = dbNhanVien.LayCheckin(sdt);
        int isCheckout = dbNhanVien.LayCheckout(sdt);
        Log.d("check", isCheckin+" va "+ isCheckout);

        txtTennv.setText(dataNV.get(0).getTenNV());
        String manv = dataNV.get(0).getMaNV();
        txtManv.setText(manv);
        String tenPb = dbNhanVien.layTenPhong(dataNV.get(0).getMaPhong());
        txtPhongban.setText(tenPb);

        if (isCheckin==0){
            btnCheckin.setEnabled(true);
            btnCheckout.setEnabled(false);
        }else {
            btnCheckin.setEnabled(false);
            btnCheckout.setEnabled(true);
        }

        btnNVTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListTaskNV.class);
                Bundle bundle = new Bundle();
                bundle.putString("manv", manv);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checkout: khi bấm differen >=0 là đúng giờ, tính công
                //Nếu đi muộn thì không checkout được nữa - disble
                //Lúc demo bấm checkin checkout là chấm một công
                //Phải cho hơn 1s thì mới >= được vì tính giây nữa
                String startTime = "18:33:00";

                long difference = getTimeToCheck(startTime);

                Log.d("gio", difference+"");

                //Code đúng chuẩn-----------
//                    if (difference>=0){
//                        Toast.makeText(getApplicationContext(), "Bạn đã đi muộn", Toast.LENGTH_SHORT).show();
//                        btnCheckout.setEnabled(false);
//                    }else {
//
//                        //isCheckin đang là 0, isCheckout đang là 1
//                        //update isCheckin = 1, isCheckout = 0
//                        dbNhanVien.updateIsCheck(isCheckout,isCheckin,sdt);
//                        btnCheckin.setEnabled(false);
//                        btnCheckout.setEnabled(true);
//                        count +=1;
//                    }
                //--------------

                //Code demo
                Toast.makeText(getApplicationContext(), "Xin cảm ơn!", Toast.LENGTH_SHORT).show();
                dbNhanVien.updateIsCheck(isCheckout,isCheckin,sdt);
                btnCheckin.setEnabled(false);
                btnCheckout.setEnabled(true);

                finish();
                startActivity(getIntent());

            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBNVChamCong dbNgayCong = new DBNVChamCong(getApplicationContext());
                NVChamCong nvChamCong = new NVChamCong();

                String endTime = "18:34:00";
                long difference = getTimeToCheck(endTime);

                //Code đúng chuẩn
//                if (difference<=0) {
//                    Toast.makeText(getApplicationContext(), "Chưa đủ công", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(), "Xin cảm ơn!", Toast.LENGTH_SHORT).show();
//                    count+=1;
//
//                    dbNhanVien.updateIsCheck(isCheckout,isCheckin,sdt);
//                    btnCheckout.setEnabled(false);
//
//                    soCong+=1;
//                    Log.d("cong",count+" Một công");
//                    String cong = Integer.toString(soCong);
//                    nvChamCong.setMaNV(manv);
//                    nvChamCong.setNgayChamCong(timeNow);
//                    nvChamCong.setSoCong(cong);
//                    Log.d("nvChamCong",nvChamCong.toString());
//
//                    dbNgayCong.themNgayCong(nvChamCong);
//                }
                //--------------------

                Toast.makeText(getApplicationContext(), "Xin cảm ơn!", Toast.LENGTH_SHORT).show();

                dbNhanVien.updateIsCheck(isCheckout,isCheckin,sdt);
                btnCheckout.setEnabled(false);

                soCong+=1;
                String cong = Integer.toString(soCong);
                nvChamCong.setMaNV(manv);
                nvChamCong.setNgayChamCong(timeNow);
                nvChamCong.setSoCong(cong);

                dbNgayCong.themNgayCong(nvChamCong);

            }
        });
    }

    //Kiểm tra thời gian nếu ra âm thì đúng giờ, dương thì đi muộn
    public Long getTimeToCheck(String timeDefault){
        //Nên tạo một hàm trả về biến differen, tham số truyền vào là thời gian mặc định startTime, endTime
        currentTime = Calendar.getInstance();
        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);
        second = currentTime.get(Calendar.SECOND);
        Log.d("time",hour+":"+ minute+":"+second);
        String timeNow = hour+":"+ minute+":"+second;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        try {
            date1 = format.parse(timeDefault);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date date2 = null;
        try {
            //thời gian hiện tại khi bấm checkin
            date2 = format.parse(timeNow);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long difference = date2.getTime() - date1.getTime();
        return  difference;
    }

//    public void content(){
//        if (isCheckin) {
//            currentTime = Calendar.getInstance();
//            hour = currentTime.get(Calendar.HOUR_OF_DAY);
//            minute = currentTime.get(Calendar.MINUTE);
//            second = currentTime.get(Calendar.SECOND);
//            Log.d("time",hour+":"+ minute+":"+second);
//
//            refresh(1000); //500 = 0.5s
//        }
//        else {
//            currentTime = Calendar.getInstance();
//            hour = currentTime.get(Calendar.HOUR_OF_DAY);
//            minute = currentTime.get(Calendar.MINUTE);
//            second = currentTime.get(Calendar.SECOND);
////            Log.d("time","Hello");
//            refresh(1000);
//        }
//    }

//    public void refresh(int miliseconds){
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                content();
//            }
//        };
//        handler.postDelayed(runnable,miliseconds);
//    }

    private String getTimeNow(int year, int month, int day){
       String timeNow =  new StringBuilder().append(day > 9 ? day : "0"+day).append("/").append(month > 9 ?
                month : "0" + month).append("/").append(year)+"";
        return timeNow;
    }
    private void showDate(int year, int month, int day) {
        txtNgaythang.setText(new StringBuilder().append(day > 9 ? day : "0"+day).append("/").append(month > 9 ?
                month : "0" + month).append("/").append(year));
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityAccounts.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát không ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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