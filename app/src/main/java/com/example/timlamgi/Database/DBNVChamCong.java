package com.example.timlamgi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timlamgi.Model.NVChamCong;

public class DBNVChamCong {

    DBHelper dbHelper;

    public DBNVChamCong(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void themNgayCong(NVChamCong nvchamCong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv", nvchamCong.getMaNV());
        values.put("ngaychamcong", nvchamCong.getNgayChamCong());
        values.put("socong", nvchamCong.getSoCong());
        db.insert("NVChamCong", null, values);
        db.close();
    }

    public int LaySoCongNV(String manv) {
        int data = 0;
        String sql = "select socong from NVChamCong where manv ='" + manv + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            cursor.moveToFirst();
            do {
                String soCong = cursor.getString(0);
                int cong = Integer.parseInt(soCong);
                data+=cong;
            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public void xoaNVChamCong(NVChamCong nvChamCong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("NVChamCong", "manv= '" + nvChamCong.getMaNV() + "'"  , null);
        db.close();
    }

    public NVChamCong LayNVChamCong(String manv) {
        NVChamCong nvChamCong = new NVChamCong();
        String sql = "select * from NVChamCong where manv ='" + manv + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            cursor.moveToFirst();
            do {
                nvChamCong.setMaNV(cursor.getString(0));
                nvChamCong.setNgayChamCong(cursor.getString(1));
                nvChamCong.setSoCong(cursor.getString(2));
            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return nvChamCong;
    }
}
