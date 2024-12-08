package com.example.timlamgi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.timlamgi.Database.DBAccount;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Interface.CheckOut.AddChamCong;
import com.example.timlamgi.Interface.Employee.MainActivityNhanVien;
import com.example.timlamgi.Interface.Employee.UpdateNhanVien;
import com.example.timlamgi.Interface.Advance.AddTamUng;
import com.example.timlamgi.Interface.Task.AddTask;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterNhanVien extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<NhanVien> data;
    final DBNhanVien dbNhanVien = new DBNhanVien(getContext());
    DBAccount dbAccount = new DBAccount(getContext());
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);


    public AdapterNhanVien(@NonNull Context context, int resource, ArrayList<NhanVien> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private static class Holder {
        TextView tvMaNV,  tvTenNV, tvNgaySinh, tvGioiTinh, tvPhongBan, tvLuong, tvSdt, tvMk;
        ImageView imgAnhDaiDien;
        ImageButton imageTask;
        Button btnXoa, btnSua;
        Button btnChamCong, btnTamUng;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder = null;
        if(view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvMaNV = view.findViewById(R.id.tvMaNV);
            holder.tvTenNV = view.findViewById(R.id.tvTenNV);
            holder.tvSdt = view.findViewById(R.id.tvSDT);
            holder.tvMk = view.findViewById(R.id.tvPassword);
            holder.tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
            holder.tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
            holder.tvPhongBan = view.findViewById(R.id.tvPhongBan);
            holder.tvLuong = view.findViewById(R.id.tvLuong);
            holder.imgAnhDaiDien = view.findViewById(R.id.imgHinhDaiDien);
            holder.btnXoa = view.findViewById(R.id.btnXoa);
            holder.btnSua = view.findViewById(R.id.btnSua);
            holder.btnChamCong = view.findViewById(R.id.btnChamCong);
            holder.btnTamUng = view.findViewById(R.id.btnTamUng);
            holder.imageTask = view.findViewById(R.id.btnTask);

            view.setTag(holder);
        }
        else
            holder=(Holder)view.getTag();

        final NhanVien nhanVien = data.get(position);

        Log.d("nhanvien",nhanVien+"");

        holder.tvMaNV.setText(nhanVien.getMaNV());
        holder.tvTenNV.setText(nhanVien.getTenNV());
        holder.tvSdt.setText(nhanVien.getSdt());
        holder.tvMk.setText(nhanVien.getMatkhau());

        holder.tvNgaySinh.setText(nhanVien.getNgaySinh());
        if (nhanVien.getGioiTinh().equals("Nam")) {
            holder.tvGioiTinh.setText(nhanVien.getGioiTinh());
        }
        if (nhanVien.getGioiTinh().equals("Nữ")) {
            holder.tvGioiTinh.setText(nhanVien.getGioiTinh());
        }

//        holder.tvPhongBan.setText(nhanVien.getMaPhong() );
        holder.tvPhongBan.setText(dbNhanVien.layTenPhong(nhanVien.getMaPhong()));

        holder.tvLuong.setText(currencyVN.format(Integer.parseInt(nhanVien.getBacLuong())));
        if(nhanVien.getImage() == null){
            holder.imgAnhDaiDien.setImageResource(R.drawable.man);
        }
        else {
            Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nhanVien.getImage(), 0, nhanVien.getImage().length);
            bmHinhDaiDien = Bitmap.createScaledBitmap(bmHinhDaiDien, 80, 80, true);
            holder.imgAnhDaiDien.setImageBitmap(bmHinhDaiDien);
        }

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNhanVien.class);
                Bundle bundle = new Bundle();
                bundle.putString("manv",nhanVien.getMaNV());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.btnChamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddChamCong.class);
                Bundle bundle = new Bundle();
                bundle.putString("ma",nhanVien.getMaNV());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkChamCong = false;
                boolean checkTamUng = false;
                checkChamCong = dbNhanVien.checkXoaNhanVienChamCong(nhanVien.getMaNV());
                checkTamUng = dbNhanVien.checkXoaNhanVienTamUng(nhanVien.getMaNV());
                if (checkChamCong == true && checkTamUng == false) {
                    Toast.makeText(context, "Cần xóa dữ liệu chấm công của Nhân viên trước", Toast.LENGTH_LONG).show();
                }
                if (checkTamUng == true && checkChamCong == false) {
                    Toast.makeText(context, "Cần xóa dữ liệu tạm ứng của Nhân viên trước", Toast.LENGTH_LONG).show();
                }
                if (checkTamUng == true && checkChamCong == true) {
                    Toast.makeText(context, "Cần xóa dữ liệu chấm công và tạm ứng của Nhân viên trước", Toast.LENGTH_LONG).show();
                }
                if (checkTamUng == false && checkChamCong == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa không");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbNhanVien.Xoa(nhanVien);
                            dbAccount.XoaAccount(nhanVien);
                            Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), MainActivityNhanVien.class);
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }

        });

        holder.btnTamUng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTamUng.class);
                Bundle bundle = new Bundle();
                bundle.putString("ma",nhanVien.getMaNV());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.imageTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTask.class);
                Bundle bundle = new Bundle();
                bundle.putString("manv",nhanVien.getMaNV());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
