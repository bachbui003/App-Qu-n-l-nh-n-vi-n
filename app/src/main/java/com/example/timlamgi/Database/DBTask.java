package com.example.timlamgi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timlamgi.Model.Tasks;

import java.util.ArrayList;

public class DBTask {
    DBHelper dbHelper;
    public DBTask(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //Lấy mã task
    public int LayMaTask(int matask) {
        int data=0;
        String sql = "select matask from Task where matask ='" + matask + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            cursor.moveToFirst();
            do {

                data = Integer.parseInt(cursor.getString(0)) ;

            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public void xoaTask(int matask) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Task", "matask= '" + matask + "'", null);
        db.close();
    }

    //Kiểm tra Mã task là duy nhất
    public boolean checkMaTask(String matask) {
        boolean check = false;
        String sql = "SELECT count(*) FROM Task WHERE matask LIKE \""+matask+"\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count  = cursor.getInt(0);
        if(count > 0) {
            check = true;
        }
        return check;
    }

    public ArrayList<Tasks> layDuLieu() {
        ArrayList<Tasks> data = new ArrayList<>();
        String sql = "Select * from Task";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                Tasks tasks = new Tasks();
                tasks.setMatask(cursor.getString(0));
                tasks.setManv(cursor.getString(1));
                tasks.setNoidung(cursor.getString(2));
                tasks.setNgay(cursor.getString(3));
                data.add(tasks);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    //Lấy dữ liệu by manv
    public ArrayList<Tasks> getDataByManv(String manv) {
        ArrayList<Tasks> data = new ArrayList<>();
        String sql = "Select * from Task where manv = '" + manv + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                Tasks tasks = new Tasks();
                tasks.setMatask(cursor.getString(0));
                tasks.setManv(cursor.getString(1));
                tasks.setNoidung(cursor.getString(2));
                tasks.setNgay(cursor.getString(3));
                data.add(tasks);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public void themTask(Tasks task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matask", task.getMatask());
        values.put("manv", task.getManv());
        values.put("noidung", task.getNoidung());
        values.put("ngay", task.getNgay());
        db.insert("Task", null, values);
        db.close();
    }
}
