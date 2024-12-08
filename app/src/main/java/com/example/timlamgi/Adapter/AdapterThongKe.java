package com.example.timlamgi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.timlamgi.Interface.MainActivityHome;
import com.example.timlamgi.Model.ThongKe;
import com.example.timlamgi.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterThongKe extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ThongKe> data;

    public AdapterThongKe(@NonNull Context context, int resource, ArrayList<ThongKe> data) {
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
        TextView tvMaNV, tvTenNV, tvNgayChamCong, tvTenPhong, tvThucLanh;
        ImageButton btnXoa;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        AdapterThongKe.Holder holder;
        if (view == null) {
            holder = new AdapterThongKe.Holder();
            view = LayoutInflater.from(context).inflate(resource, null);

            holder.tvMaNV = view.findViewById(R.id.tvMaNV);
            holder.tvTenNV = view.findViewById(R.id.tvTenNV);
            holder.tvNgayChamCong = view.findViewById(R.id.tvNgayChamCong);
            holder.tvTenPhong = view.findViewById(R.id.tvTenPhongBan);
            holder.tvThucLanh = view.findViewById(R.id.tvThucLanh);
            holder.btnXoa = view.findViewById(R.id.btnXoa);

            view.setTag(holder);
        } else {
            holder = (AdapterThongKe.Holder) view.getTag();
        }

        final ThongKe thongKe = data.get(position);
        holder.tvMaNV.setText(thongKe.getMaNV());
        holder.tvTenNV.setText(thongKe.getTenNV());
        holder.tvTenPhong.setText(thongKe.getTenPhong());
        holder.tvNgayChamCong.setText(thongKe.getNgayChamCong());

        int luong = Integer.parseInt(thongKe.getHeSoLuong()) * Integer.parseInt(thongKe.getSoNgayCong());
        thongKe.setLuongCoBan(String.valueOf(luong));

        int thucLanh = luong - (thongKe.getTienTamUng() != null ? Integer.parseInt(thongKe.getTienTamUng()) : 0);
        thongKe.setLuongThucLanh(String.valueOf(thucLanh));

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        holder.tvThucLanh.setText(currencyVN.format(thucLanh));


        return view;
    }}


