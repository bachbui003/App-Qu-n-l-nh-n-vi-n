package com.example.timlamgi.Interface.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timlamgi.Adapter.AdapterNVTask;
import com.example.timlamgi.Database.DBTask;
import com.example.timlamgi.Model.Tasks;
import com.example.timlamgi.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ListTaskNV extends AppCompatActivity {

    TextView txtMonthTaskNV, txtWeekTaskNV;
    Calendar calendar;
    int year, month, day;
    ListView lvTaskNV;
    ArrayList<Tasks> data_tasks;
    AdapterNVTask adapterNVTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task_nv);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtMonthTaskNV = findViewById(R.id.txtMonthTaskNV);
        txtWeekTaskNV = findViewById(R.id.txtWeekTaskNV);
        lvTaskNV = findViewById(R.id.lvTaskNV);

        calendar =Calendar.getInstance();
        year =calendar.get(Calendar.YEAR);
        month= calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);

        int monthNum = month +1;
        txtMonthTaskNV.setText("Tháng "+monthNum+" năm "+year);
        if (day<=8) {
            txtWeekTaskNV.setText("Tuần thứ: " + 1);
        } else if (day<=16) {
            txtWeekTaskNV.setText("Tuần thứ: " + 2);
        }else if (day<=24){
            txtWeekTaskNV.setText("Tuần thứ: " + 3);
        } else{
            txtWeekTaskNV.setText("Tuần thứ: " + 4);
        }

        String manv = getIntent().getExtras().getString("manv");

        DBTask dbTask = new DBTask(getApplicationContext());
        data_tasks = dbTask.getDataByManv(manv);
        adapterNVTask=new AdapterNVTask(getApplicationContext(),R.layout.custom_task_nv,data_tasks);
        adapterNVTask.notifyDataSetChanged();
        lvTaskNV.setAdapter(adapterNVTask);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}