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
import com.example.timlamgi.Database.DBPhongBan;
import com.example.timlamgi.Database.DBTamUng;
import com.example.timlamgi.Interface.Advance.MainActivityTamUng;
import com.example.timlamgi.Interface.Advance.UpdateTamUng;
import com.example.timlamgi.Interface.CheckOut.MainActivityChamCong;
import com.example.timlamgi.Interface.Departments.MainActivityPhongBan;
import com.example.timlamgi.Interface.MainActivityHome;
import com.example.timlamgi.Model.NVChamCong;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.Model.PhongBan;
import com.example.timlamgi.Model.TamUng;
import com.example.timlamgi.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterTamUng extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<TamUng> data;
    final DBTamUng dbTamUng= new DBTamUng(getContext());

    ArrayList<NhanVien> nhanVien = new ArrayList<>();
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    public AdapterTamUng(@NonNull Context context, int resource, ArrayList<TamUng> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private static class Holder{
        TextView tvMaNV, tvTenNV, tvNgayUng, tvSoTien, tvSoPhieu;
        ImageButton btnXoa, btnSua;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        AdapterTamUng.Holder holder = null;
        if (view == null) {
            holder = new AdapterTamUng.Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvSoPhieu = view.findViewById(R.id.tvSoPhieu);
            holder.tvMaNV = view.findViewById(R.id.tvMaNV);
            holder.tvTenNV = view.findViewById(R.id.tvHoTen);
            holder.tvNgayUng = view.findViewById(R.id.tvNgayTamUng);
            holder.tvSoTien = view.findViewById(R.id.tvTienTamUng);
            holder.btnXoa = view.findViewById(R.id.btnXoa);
            holder.btnSua = view.findViewById(R.id.btnSua);

            view.setTag(holder);
        }else
            holder = (AdapterTamUng.Holder) view.getTag();

        final TamUng tamUng = data.get(position);
//        holder.tvSoPhieu.setText(tamUng.getSoPhieu());
        holder.tvMaNV.setText(tamUng.getMaNV());
        holder.tvSoTien.setText(currencyVN.format(Integer.parseInt(tamUng.getSoTienUng())));
        holder.tvNgayUng.setText(tamUng.getNgayTamUng());
        DBNhanVien dbNhanVien = new DBNhanVien(getContext());
        nhanVien = dbNhanVien.LayNhanVien(tamUng.getMaNV());
        holder.tvTenNV.setText(nhanVien.get(0).getTenNV());

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbTamUng.xoaTamUng(tamUng);
                Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivityHome.class);
                notifyDataSetChanged();
                context.startActivity(intent);
            }
        });




        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTamUng.class);
                Bundle bundle = new Bundle();
                bundle.putString("manv", tamUng.getMaNV());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;

    }

}
