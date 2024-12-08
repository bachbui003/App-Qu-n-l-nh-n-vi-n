package com.example.timlamgi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timlamgi.Model.ChamCong;

import java.util.ArrayList;

public class DBChamCong {

    DBHelper dbHelper;

    public DBChamCong(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //Kiểm tra admin chỉ chấm công một lần trong tháng
    public boolean checkChamCong(String timeCham, String manv) {
        boolean check = false;
        String sql = "SELECT count(*) FROM ChamCong WHERE SUBSTR(ngaychamcong, 4, 10) LIKE SUBSTR(\""+timeCham+"\", 4, 10) and manv LIKE \""+manv+"\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count  = cursor.getInt(0);
        if(count > 0) {
            check = true;
        }
        return check;
    }

//    public boolean checkChamCong(String timeCham, String manv) {
//        boolean check = false;
//        String sql = "SELECT count(*) FROM ChamCong WHERE ngaychamcong LIKE \""+timeCham+"\" and manv LIKE \""+manv+"\" ";
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, null);
//        cursor.moveToFirst();
//        int count  = cursor.getInt(0);
//        if(count > 0) {
//            check = true;
//        }
//        return check;
//    }

    public void themChamCong(ChamCong chamCong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv", chamCong.getMaNV());
        values.put("ngaychamcong", chamCong.getThangChamCong());
        values.put("songaycong", chamCong.getSoNgayCong());
        db.insert("ChamCong", null, values);
        db.close();
    }

    public void suaChamCong(ChamCong chamCong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv", chamCong.getMaNV());
        values.put("ngaychamcong", chamCong.getThangChamCong());
        values.put("songaycong", chamCong.getSoNgayCong());
        db.update("ChamCong", values, "manv ='" + chamCong.getMaNV() + "'", null);
        db.close();
    }
    public ArrayList<ChamCong> layChamCong(String manv) {
        ArrayList<ChamCong> data = new ArrayList<>();
        String sql = "select * from ChamCong where manv ='" + manv + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                ChamCong chamCong = new ChamCong();
                chamCong.setMaNV(cursor.getString(0));
                chamCong.setThangChamCong(cursor.getString(1));
                chamCong.setSoNgayCong(cursor.getString(2));
                data.add(chamCong);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        db.close();
        return data;
    }

    public void xoaChamCong(ChamCong chamCong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("ChamCong", "manv= '" + chamCong.getMaNV() + "'"  , null);
        db.close();
    }

    public ArrayList<ChamCong> layDuLieuCC() {
        ArrayList<ChamCong> data = new ArrayList<>();
        String sql = "Select * from ChamCong ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                ChamCong chamCong = new ChamCong();
                chamCong.setMaNV(cursor.getString(0));
                chamCong.setThangChamCong(cursor.getString(1));
                chamCong.setSoNgayCong(cursor.getString(2));
                data.add(chamCong);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        db.close();
        return data;
    }

}
