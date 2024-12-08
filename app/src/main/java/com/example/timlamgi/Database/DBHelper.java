package com.example.timlamgi.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "SQLQuanLyTinhLuong", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPhongBan = "Create table PhongBan (mapb text PRIMARY KEY NOT NULL , tenpb text) ";
        db.execSQL(sqlPhongBan);

        String sqlNhanVien = "Create table NhanVien (manv text PRIMARY KEY NOT NULL, tennv text, sdt text, password text, ngaysinh text, gioitinh text,mapb text, hesoluong text, imagenv Blob, isCheckin INTEGER DEFAULT 0, isCheckout INTEGER DEFAULT 1)";
        db.execSQL(sqlNhanVien);

        String sqlChamCong = "Create table ChamCong (manv text, ngaychamcong text, songaycong text)";
        db.execSQL(sqlChamCong);

        String sqlSoNgayCong = "Create table NVChamCong (manv text,ngaychamcong text, socong text)";
        db.execSQL(sqlSoNgayCong);

        String sqlTamUng = "Create table TamUng (sophieu text PRIMARY KEY NOT NULL, ngaytamung text, sotienung text, manv text)";
        db.execSQL(sqlTamUng);

        String account = "Create table Account (sdt text PRIMARY KEY NOT NULL, password text, manv text)";
        db.execSQL(account);

        String sqlTask = "Create table Task (matask INTEGER , manv text, noidung text, ngay text)";
        db.execSQL(sqlTask);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
