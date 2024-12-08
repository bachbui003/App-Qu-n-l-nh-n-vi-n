package com.example.timlamgi.Interface.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timlamgi.Database.DBTask;
import com.example.timlamgi.Model.Tasks;
import com.example.timlamgi.R;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    TextView txtMonth, txtWeek;
    EditText edtNoteTask,edtNgayTask,edtMaTask;
    Calendar calendar;
    int year, month, day;

    Button btnAddTask;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtMonth = findViewById(R.id.txtMonthTask);
        txtWeek = findViewById(R.id.txtWeekTask);
        edtNoteTask = findViewById(R.id.edtNoteTask);
        edtNgayTask = findViewById(R.id.edtNgayTask);
        edtMaTask = findViewById(R.id.edtMaTask);
        btnAddTask = findViewById(R.id.btnAddTask);
        calendar =Calendar.getInstance();
        year =calendar.get(Calendar.YEAR);
        month= calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);

        edtNoteTask.setText("Sáng: 08h-12h \n\n\nChiều: 13h-17h\n\n ");
        edtNgayTask.setText("0/0/2023");
        String manv = getIntent().getExtras().getString("manv");
        int monthNum = month +1;
        txtMonth.setText("Tháng "+monthNum+" năm "+year);
        edtMaTask.requestFocus();

        if (day<=8) {
            txtWeek.setText("Tuần thứ: " + 1);
        } else if (day<=16) {
            txtWeek.setText("Tuần thứ: " + 2);
        }else if (day<=24){
            txtWeek.setText("Tuần thứ: " + 3);
        } else{
            txtWeek.setText("Tuần thứ: " + 4);
        }

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBTask dbTask = new DBTask(getApplicationContext());
                boolean check = dbTask.checkMaTask(edtMaTask.getText().toString());

                if (edtMaTask.getText().toString().isEmpty()) {
                    edtMaTask.setError("Hãy nhập mã");
                    edtMaTask.isFocused();
                }else if (check==true) {
                    edtMaTask.setError("Mã đã tồn tại");
                    edtMaTask.isFocused();
                }else {
                    Tasks task = new Tasks();
                    task.setMatask(edtMaTask.getText().toString());
                    task.setManv(manv);
                    task.setNoidung(edtNoteTask.getText().toString());
                    task.setNgay(edtNgayTask.getText().toString());
                    dbTask.themTask(task);

                    Toast.makeText(AddTask.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivityTask.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("manv", task.getManv());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

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