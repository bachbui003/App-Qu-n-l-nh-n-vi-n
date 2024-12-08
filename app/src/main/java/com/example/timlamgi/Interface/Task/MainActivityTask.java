package com.example.timlamgi.Interface.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.timlamgi.Adapter.AdapterTask;
import com.example.timlamgi.Database.DBTask;
import com.example.timlamgi.Interface.MainActivityHome;
import com.example.timlamgi.Model.Tasks;
import com.example.timlamgi.R;

import java.util.ArrayList;

public class MainActivityTask extends AppCompatActivity {
    ListView lvTask;
    ArrayList<Tasks> data_tasks;
    AdapterTask adapterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);
        lvTask = findViewById(R.id.lvTask);

        DBTask dbTask = new DBTask(getApplicationContext());
        data_tasks = dbTask.layDuLieu();
        adapterTask=new AdapterTask(getApplicationContext(),R.layout.custom_task,data_tasks);
        adapterTask.notifyDataSetChanged();
        lvTask.setAdapter(adapterTask);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent intent = new Intent(MainActivityTask.this, MainActivityHome.class);
            startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}