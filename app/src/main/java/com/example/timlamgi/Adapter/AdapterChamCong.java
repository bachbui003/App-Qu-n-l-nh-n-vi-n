package com.example.timlamgi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.timlamgi.Database.DBChamCong;
import com.example.timlamgi.Database.DBNVChamCong;
import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Interface.CheckOut.MainActivityChamCong;
import com.example.timlamgi.Interface.CheckOut.UpdateChamCong;
import com.example.timlamgi.Model.ChamCong;
import com.example.timlamgi.Model.NVChamCong;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class AdapterChamCong extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ChamCong> data;
    ArrayList<NhanVien> nhanVien = new ArrayList<>();

    public AdapterChamCong(@NonNull Context context, int resource, ArrayList<ChamCong> data) {
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
        TextView tvMaNV, tvTenNV, tvNgayChamCong, tvSoNgayCong;
        ImageButton btnSua, btnXoa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        AdapterChamCong.Holder holder = null;
        if (view == null) {
            holder = new AdapterChamCong.Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvMaNV = view.findViewById(R.id.tvMaNV);
            holder.tvTenNV = view.findViewById(R.id.tvHoTen);
            holder.tvNgayChamCong = view.findViewById(R.id.tvNgayChamCong);
            holder.tvSoNgayCong = view.findViewById(R.id.tvSoNgayCong);
            holder.btnXoa = view.findViewById(R.id.btnXoa);
            holder.btnSua = view.findViewById(R.id.btnSua);
            view.setTag(holder);
        } else
            holder = (AdapterChamCong.Holder) view.getTag();

        final ChamCong chamCong = data.get(position);
        holder.tvMaNV.setText(chamCong.getMaNV());
        holder.tvNgayChamCong.setText(chamCong.getThangChamCong());
        holder.tvSoNgayCong.setText(chamCong.getSoNgayCong());
        DBNhanVien dbNhanVien = new DBNhanVien(getContext());
        nhanVien = dbNhanVien.LayNhanVien(chamCong.getMaNV());
        holder.tvTenNV.setText(nhanVien.get(0).getTenNV());

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Xóa nhân viên ADmin chấm công
                        DBChamCong dbChamCong = new DBChamCong(getContext());
                        dbChamCong.xoaChamCong(chamCong);
                        //Xóa nhân viên chấm công
                        DBNVChamCong dbnvChamCong = new DBNVChamCong(getContext());
                        NVChamCong nvChamCong = dbnvChamCong.LayNVChamCong(chamCong.getMaNV());
                        dbnvChamCong.xoaNVChamCong(nvChamCong);

                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivityChamCong.class);
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
        });

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateChamCong.class);
                Bundle bundle = new Bundle();
                bundle.putString("manv", chamCong.getMaNV());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }


}
