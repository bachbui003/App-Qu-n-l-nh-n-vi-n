package com.example.timlamgi.Adapter;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.timlamgi.Database.DBPhongBan;
import com.example.timlamgi.Interface.Departments.MainActivityPhongBan;
import com.example.timlamgi.Interface.Departments.UpdatePhongBan;
import com.example.timlamgi.Model.PhongBan;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class AdapterPB extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<PhongBan> data;
    ArrayList<PhongBan> data_DS;

    public AdapterPB(@NonNull Context context, int resource,ArrayList<PhongBan> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.data_DS = new ArrayList<PhongBan>();
        this.data_DS.addAll(data);
    }

    @Override
    public int getCount(){
        return data.size();
    }

    private static class Holder {
        ImageButton btnXoa;
        ImageButton btnSua;
        TextView tvMaPB;
        TextView tvTenPB;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvMaPB = view.findViewById(R.id.tvMaPhong);
            holder.tvTenPB = view.findViewById(R.id.tvTenPhong);
            holder.btnSua = view.findViewById(R.id.btnSua);
            holder.btnXoa = view.findViewById(R.id.btnXoa);
            view.setTag(holder);
        }
        else {
            holder = (Holder) view.getTag();
            notifyDataSetChanged();
        }
        final PhongBan phongBan = data.get(position);
        holder.tvMaPB.setText(phongBan.getMaPhong());
        holder.tvTenPB.setText(phongBan.getTenPhong());
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        XoaDL(phongBan);
                        notifyDataSetChanged();
                        Intent intent = new Intent(getContext(), MainActivityPhongBan.class);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdatePhongBan.class);
                Bundle bundle = new Bundle();
                bundle.putString("ma", phongBan.getMaPhong());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private void XoaDL(PhongBan phongBan) {
        DBPhongBan dbPhongBan = new DBPhongBan(context);
        dbPhongBan.Xoa(phongBan);
        dbPhongBan.LayDL();
    }
}
