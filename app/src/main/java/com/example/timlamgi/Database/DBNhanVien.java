package com.example.timlamgi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timlamgi.Model.NhanVien;
import com.example.timlamgi.Model.PhongBan;
import com.example.timlamgi.Model.ThongKe;

import java.util.ArrayList;

public class DBNhanVien {

    DBHelper dbHelper;

    public DBNhanVien(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void Them(NhanVien nhanVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv", nhanVien.getMaNV());
        values.put("tennv", nhanVien.getTenNV());
        values.put("sdt", nhanVien.getSdt());
        values.put("password", nhanVien.getMatkhau());
        values.put("ngaysinh", nhanVien.getNgaySinh());
        values.put("gioitinh", nhanVien.getGioiTinh());
        values.put("mapb", nhanVien.getMaPhong());
        values.put("hesoluong", nhanVien.getBacLuong());
        values.put("imagenv", nhanVien.getImage());
        db.insert("NhanVien", null, values);
        db.close();
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

    public String layTenPhong(String maPhong) {
        String tenPhong = "";
        String sql = "SELECT tenpb FROM PhongBan WHERE mapb LIKE \"%" + maPhong + "%\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                PhongBan phongBan = new PhongBan();
                phongBan.setTenPhong(cursor.getString(0));
                tenPhong = phongBan.getTenPhong();
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tenPhong;
    }

    public boolean checkMaNhanVien(String manv) {
        boolean check = false;
        String sql = "SELECT count(*) FROM NhanVien WHERE manv LIKE \""+manv+"\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count  = cursor.getInt(0);
        if(count > 0) {
            check = true;
        }
        return check;
    }

    //getData by name
    public ArrayList<NhanVien> getDataByID(String input) {
        ArrayList<NhanVien> data = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE manv COLLATE UTF8CI LIKE '%"+input+"%'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNV(cursor.getString(0));
                nhanVien.setTenNV(cursor.getString(1));
                nhanVien.setSdt(cursor.getString(2));
                nhanVien.setMatkhau(cursor.getString(3));
                nhanVien.setNgaySinh(cursor.getString(4));
                nhanVien.setGioiTinh(cursor.getString(5));
                nhanVien.setMaPhong(cursor.getString(6));
                nhanVien.setBacLuong(cursor.getString(7));
                nhanVien.setImage(cursor.getBlob(8));
//                Log.d("nv", nhanVien + "");
                data.add(nhanVien);
            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public ArrayList<NhanVien> LayDSNhanVien() {
        ArrayList<NhanVien> data = new ArrayList<>();
        String sql = "select * from NhanVien";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            cursor.moveToFirst();
            do {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNV(cursor.getString(0));
                nhanVien.setTenNV(cursor.getString(1));
                nhanVien.setSdt(cursor.getString(2));
                nhanVien.setMatkhau(cursor.getString(3));

                nhanVien.setNgaySinh(cursor.getString(4));
                nhanVien.setGioiTinh(cursor.getString(5));
                nhanVien.setMaPhong(cursor.getString(6));
                nhanVien.setBacLuong(cursor.getString(7));
                nhanVien.setImage(cursor.getBlob(8));
                Log.d("nv", nhanVien + "");
                data.add(nhanVien);
            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public boolean checkXoaNhanVienChamCong(String maNV) {
        boolean check = false;
        String sql = "SELECT count(*) FROM ChamCong WHERE manv LIKE \"%" + maNV + "%\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0) {
            check = true;
        }
        return check;
    }

    public boolean checkXoaNhanVienTamUng(String maNV) {
        boolean check = false;
        String sql = "SELECT count(*) FROM TamUng WHERE manv LIKE \"%" + maNV + "%\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0) {
            check = true;
        }
        return check;
    }

    //Lấy checkin bằng sdt
    public int LayCheckin(String sdt) {
        int data=0;
        String sql = "select isCheckin from NhanVien where sdt ='" + sdt + "'";
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

    //lấy checkout bằng sdt
    public int LayCheckout(String sdt) {
        int data=0;
        String sql = "select isCheckout from NhanVien where sdt ='" + sdt + "'";
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

    //Lấy thông tin nhân viên qua sdt
    public ArrayList<NhanVien> LayNVBySDT(String sdt) {
        ArrayList<NhanVien> data = new ArrayList<>();
        String sql = "select * from NhanVien where sdt ='" + sdt + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            cursor.moveToFirst();
            do {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNV(cursor.getString(0));
                nhanVien.setTenNV(cursor.getString(1));
                nhanVien.setSdt(cursor.getString(2));
                nhanVien.setMatkhau(cursor.getString(3));
                nhanVien.setNgaySinh(cursor.getString(4));
                nhanVien.setGioiTinh(cursor.getString(5));
                nhanVien.setMaPhong(cursor.getString(6));
                nhanVien.setBacLuong(cursor.getString(7));
                nhanVien.setImage(cursor.getBlob(8));
                data.add(nhanVien);
            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public ArrayList<NhanVien> LayNhanVien(String manv) {
        ArrayList<NhanVien> data = new ArrayList<>();
        String sql = "select * from NhanVien where manv ='" + manv + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            cursor.moveToFirst();
            do {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNV(cursor.getString(0));
                nhanVien.setTenNV(cursor.getString(1));
                nhanVien.setSdt(cursor.getString(2));
                nhanVien.setMatkhau(cursor.getString(3));
                nhanVien.setNgaySinh(cursor.getString(4));
                nhanVien.setGioiTinh(cursor.getString(5));
                nhanVien.setMaPhong(cursor.getString(6));
                nhanVien.setBacLuong(cursor.getString(7));
                nhanVien.setImage(cursor.getBlob(8));
                data.add(nhanVien);
            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return data;
    }

    public void Sua(NhanVien nhanVien) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("manv", nhanVien.getMaNV());
        values.put("tennv", nhanVien.getTenNV());
        values.put("sdt", nhanVien.getSdt());
        values.put("password", nhanVien.getMatkhau());
        values.put("ngaysinh", nhanVien.getNgaySinh());
        values.put("gioitinh", nhanVien.getGioiTinh());
        values.put("mapb", nhanVien.getMaPhong());
        values.put("hesoluong", nhanVien.getBacLuong());
        values.put("imagenv", nhanVien.getImage());
        db.update("NhanVien", values, "manv ='" + nhanVien.getMaNV() + "'", null);
        db.close();
    }

    //Update isCheck by sdt
    public void updateIsCheck(int num1, int num2, String sdt) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isCheckin", num1 );
        values.put("isCheckout", num2);

        db.update("NhanVien", values, "sdt ='" + sdt + "'", null);
        db.close();
    }

    public void Xoa(NhanVien nhanVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("NhanVien", "manv ='" + nhanVien.getMaNV() + "'", null);
        db.close();
    }

    //Lấy danh sách nhân viên để tổng lương
    public ArrayList<ThongKe> layDSThongKe() {
        ArrayList<ThongKe> data = new ArrayList<>();
        String sql = "select NhanVien.manv, NhanVien.tennv, PhongBan.tenpb, NhanVien.hesoluong, ChamCong.ngaychamcong, ChamCong.songaycong, TamUng.sotienung " +
                "from NhanVien LEFT JOIN  PhongBan on PhongBan.mapb = NhanVien.mapb " +
                "INNER JOIN  ChamCong on NhanVien.manv = ChamCong.manv  " +
                "LEFT JOIN TamUng on NhanVien.manv = TamUng.manv ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            cursor.moveToFirst();
            do {
                ThongKe thongKe = new ThongKe();
                thongKe.setMaNV(cursor.getString(0));
                thongKe.setTenNV(cursor.getString(1));
                thongKe.setTenPhong(cursor.getString(2));
                thongKe.setHeSoLuong(cursor.getString(3));
                thongKe.setNgayChamCong(cursor.getString(4));
                thongKe.setSoNgayCong(cursor.getString(5));
                thongKe.setTienTamUng(cursor.getString(6));
                data.add(thongKe);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }


}
