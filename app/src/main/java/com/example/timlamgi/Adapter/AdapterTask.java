package com.example.timlamgi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.timlamgi.Database.DBNhanVien;
import com.example.timlamgi.Database.DBTask;
import com.example.timlamgi.Interface.Task.MainActivityTask;
import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.Model.Tasks;
import com.example.timlamgi.R;

import java.util.ArrayList;


public class AdapterTask extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Tasks> data;
    DBTask dbTask = new DBTask(getContext());
    ArrayList<NhanVien> nhanVien = new ArrayList<>();

    public AdapterTask(@NonNull Context context, int resource, ArrayList<Tasks> data) {
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
        TextView tvMaNVTask, tvTenNVTask, tvMaTask;
        EditText edtNoiDungTask, edtNgayThangTask;
        ImageButton btnXoaTask;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        AdapterTask.Holder holder = null;
        if (view == null) {
            holder = new AdapterTask.Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvMaNVTask = view.findViewById(R.id.tvMaNVTask);
            holder.tvTenNVTask = view.findViewById(R.id.tvTenNVTask);
            holder.edtNoiDungTask = view.findViewById(R.id.edtNoiDungTask);
            holder.edtNgayThangTask = view.findViewById(R.id.edtNgayThangTask);
            holder.tvMaTask = view.findViewById(R.id.tvMaTask);
            holder.btnXoaTask = view.findViewById(R.id.btnXoaTask);

            view.setTag(holder);
        }else
            holder = (AdapterTask.Holder) view.getTag();

        final Tasks tasks = data.get(position);
        holder.tvMaTask.setText(tasks.getMatask());
        holder.tvMaNVTask.setText(tasks.getManv());
        holder.edtNoiDungTask.setText(tasks.getNoidung());
        holder.edtNgayThangTask.setText(tasks.getNgay());

        DBNhanVien dbNhanVien = new DBNhanVien(getContext());
        nhanVien = dbNhanVien.LayNhanVien(tasks.getManv());
        holder.tvTenNVTask.setText(nhanVien.get(0).getTenNV());

        holder.btnXoaTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int matask = Integer.parseInt(tasks.getMatask());
                int num = dbTask.LayMaTask(matask);
                dbTask.xoaTask(num);
                Log.d("vt",num+"");
                Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivityTask.class);
                notifyDataSetChanged();
                context.startActivity(intent);
            }
        });
        return view;

    }
}
