package com.example.timlamgi.Interface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.timlamgi.Adapter.Adapter;
import com.example.timlamgi.Database.DBHelper;
import com.example.timlamgi.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivityLogin extends AppCompatActivity {

    CardView cardView;
    TabLayout tabLayout;
    ViewPager viewPager;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        dbHelper = new DBHelper(getApplicationContext());
        setControl();
    }

    private void setControl() {
    cardView =findViewById(R.id.cardview);
    tabLayout=findViewById(R.id.tablayout);
    viewPager=findViewById(R.id.viewpager);
    Adapter pageAdapter = new Adapter(getSupportFragmentManager());
    viewPager.setAdapter(pageAdapter);
    tabLayout.setupWithViewPager(viewPager);
    cardView.setTranslationX(300);
    cardView.animate().translationX(0).setDuration(1000).setStartDelay(400).start();
    }
}