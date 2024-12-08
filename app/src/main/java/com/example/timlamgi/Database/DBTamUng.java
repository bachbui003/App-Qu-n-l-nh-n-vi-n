package com.example.timlamgi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timlamgi.Model.TamUng;

import java.util.ArrayList;

public class DBTamUng {
    DBHelper dbHelper;
    public DBTamUng(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    //Kiểm tra Số phiếu là duy nhất
    public boolean checkSoPhieu(String soPhieu) {
        boolean check = false;
        String sql = "SELECT count(*) FROM TamUng WHERE sophieu LIKE \""+soPhieu+"\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count  = cursor.getInt(0);
        if(count > 0) {
            check = true;
        }
        return check;
    }

    public void xoaTamUng(TamUng tamUng) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("TamUng", "sophieu= '" + tamUng.getSoPhieu() + "'", null);
        db.close();
    }

    //Update tạm ứng
    public void suaTamUng(TamUng tamUng) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sophieu", tamUng.getSoPhieu());
        values.put("ngaytamung", tamUng.getNgayTamUng());
        values.put("sotienung", tamUng.getSoTienUng());
        values.put("manv", tamUng.getMaNV());
        db.update("TamUng", values, "sophieu ='" + tamUng.getSoPhieu() + "'", null);
        db.close();
    }

    //Lấy phiếu để update tạm ứng
    public ArrayList<TamUng> layPhieu(String sophieu) {
        ArrayList<TamUng> data = new ArrayList<>();
        String sql = "select * from TamUng where sophieu ='" + sophieu + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                TamUng tamUng = new TamUng();
                tamUng.setSoPhieu(cursor.getString(0));
                tamUng.setNgayTamUng(cursor.getString(1));
                tamUng.setSoTienUng(cursor.getString(2));
                tamUng.setMaNV(cursor.getString(3));
                data.add(tamUng);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        db.close();
        return data;
    }

    //Kiểm tra thời gian tạm ứng 1 tháng là duy nhất
    public boolean checkTamUng(String timeCham, String manv) {
        boolean check = false;
        String sql = "SELECT count(*) FROM TamUng WHERE SUBSTR(ngaytamung, 4, 10) LIKE SUBSTR(\""+timeCham+"\", 4, 10) and manv LIKE \""+manv+"\" ";
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count  =cursor.getInt(0);
        if(count > 0) {
            check =true;
        }
        return check;
    }

    public ArrayList<TamUng> layDuLieu() {
        ArrayList<TamUng> data = new ArrayList<>();
        String sql = "Select * from TamUng";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                TamUng tamUng = new TamUng();
                tamUng.setSoPhieu(cursor.getString(0));
                tamUng.setNgayTamUng(cursor.getString(1));
                tamUng.setSoTienUng(cursor.getString(2));
                tamUng.setMaNV(cursor.getString(3));
                data.add(tamUng);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public void themTamUng(TamUng tamUng) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sophieu", tamUng.getSoPhieu());
        values.put("ngaytamung", tamUng.getNgayTamUng());
        values.put("sotienung", tamUng.getSoTienUng());
        values.put("manv", tamUng.getMaNV());
        db.insert("TamUng", null, values);
        db.close();
    }
}
