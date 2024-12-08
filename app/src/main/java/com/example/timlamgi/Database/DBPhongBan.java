package com.example.timlamgi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timlamgi.Model.PhongBan;

import java.util.ArrayList;

public class DBPhongBan {
    DBHelper dbHelper;

    public DBPhongBan(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<PhongBan> LayDL() {
        ArrayList<PhongBan> data = new ArrayList<>();
        String sql = "Select * from PhongBan";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                PhongBan phongBan = new PhongBan();
                phongBan.setMaPhong(cursor.getString(0));
                phongBan.setTenPhong(cursor.getString(1));
                data.add(phongBan);
            } while (cursor.moveToNext());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        db.close();
        return data;
    }

    public void Xoa(PhongBan phongBan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "Delete from PhongBan where mapb = '" + phongBan.getMaPhong() + "'";
        db.execSQL(sql);
        db.close();
    }

    public boolean checkMaPhong(String maPhong) {
        boolean check = false;
        String sql = "SELECT count(*) FROM PhongBan WHERE mapb LIKE \"" + maPhong + "\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0) {
            check = true;
        }
        return check;
    }

    public void Them(PhongBan phongBan) {
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mapb",phongBan.getMaPhong());
        values.put("tenpb",phongBan.getTenPhong());
        db.insert("PhongBan", null,values);
        db.close();
    }

    public ArrayList<PhongBan> LayDL(String mapb) {
        ArrayList<PhongBan> data = new ArrayList<>();
        String sql = "select * from PhongBan where mapb = '" + mapb + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                PhongBan phongBan = new PhongBan();
                phongBan.setMaPhong(cursor.getString(0));
                phongBan.setTenPhong(cursor.getString(1));
                data.add(phongBan);
            } while (cursor.moveToNext());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    public void Sua(PhongBan phongBan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mapb", phongBan.getMaPhong());
        values.put("tenpb", phongBan.getTenPhong());
        db.update("PhongBan", values, "mapb = '" + phongBan.getMaPhong() + "'", null);
        db.close();
    }

    public ArrayList<String> LayDSPhong() {
        ArrayList<String> ds = new ArrayList<>();
        String sql = "select tenpb from PhongBan";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                PhongBan data = new PhongBan();
                String phong = "";
                data.setTenPhong(cursor.getString(0));
                phong = data.getTenPhong().toString();
                ds.add(phong);
            } while (cursor.moveToNext());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ds;
    }

    public String layMaPhong(String tenPhong) {
        String maPhong = "";
        String sql = "SELECT mapb FROM PhongBan WHERE tenpb LIKE \"%" + tenPhong + "%\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                PhongBan phongBan = new PhongBan();
                phongBan.setMaPhong(cursor.getString(0));
                maPhong = phongBan.getMaPhong();
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maPhong;
    }

}
