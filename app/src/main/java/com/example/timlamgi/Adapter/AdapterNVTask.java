package com.example.timlamgi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.timlamgi.Database.DBTask;
import com.example.timlamgi.Model.Tasks;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class AdapterNVTask extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Tasks> data;
    DBTask dbTask = new DBTask(getContext());

    public AdapterNVTask(@NonNull Context context, int resource, ArrayList<Tasks> data) {
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
        TextView tvMaTaskNV;
        EditText edtNgayThangTaskNV,edtNoiDungTaskNV;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        AdapterNVTask.Holder holder = null;
        if (view == null) {
            holder = new AdapterNVTask.Holder();
            view = LayoutInflater.from(context).inflate(resource, null);
            holder.tvMaTaskNV = view.findViewById(R.id.tvMaTaskNV);
            holder.edtNgayThangTaskNV = view.findViewById(R.id.edtNgayThangTaskNV);
            holder.edtNoiDungTaskNV = view.findViewById(R.id.edtNoiDungTaskNV);

            view.setTag(holder);
        }else
            holder = (AdapterNVTask.Holder) view.getTag();

        final Tasks tasks = data.get(position);
        holder.tvMaTaskNV.setText(tasks.getMatask());
        holder.edtNoiDungTaskNV.setText(tasks.getNoidung());
        holder.edtNgayThangTaskNV.setText(tasks.getNgay());

        return view;

    }

}
