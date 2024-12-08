package com.example.timlamgi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timlamgi.Model.Accounts;
import com.example.timlamgi.Model.NhanVien;

public class DBAccount {
    DBHelper dbHelper;

    public DBAccount(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public void themAccount(String sdt, String password, String manv, onClickListenerRs onClickListener) {
        String sql = "Select * from Account where sdt = '" + sdt + "'";
        if (dbHelper.getReadableDatabase().rawQuery(sql, null).getCount() == 0) {
            String sqlInsert = "Insert into Account values ('" + sdt + "','" + password + "','" + manv + "')";
            dbHelper.getWritableDatabase().execSQL(sqlInsert);
            onClickListener.success();
        } else {
            onClickListener.fail();
        }
    }

    public void suaAccount(Accounts account) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sdt", account.getSdt());
        values.put("password", account.getPassword());
        values.put("manv", account.getManv() );

        db.update("Account", values, "manv ='" + account.getManv() + "'", null);
        db.close();
    }

    //Check login admin
    //Kiểm tra user đã tồn tại hay chưa
    public Cursor checkAdminExist(String taikhoan){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String sql = "Select * from Account where sdt = '" + taikhoan + "'";
        Cursor cursor =db.rawQuery(sql,null);
        return cursor;
    }

    //Check sdt tồn tại hay chưa
    public boolean checkSdtNhanvien(String sdt) {
        boolean check = false;
        String sql = "SELECT count(*) FROM Account WHERE sdt LIKE \""+sdt+"\" ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count  = cursor.getInt(0);
        if(count > 0) {
            check = true;
        }
        return check;
    }

    public void checkLogin(String sdt, onClickListener password) {
        String sql = "Select password from Account where sdt = '" + sdt + "'";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            password.fail();
        } else {
            cursor.moveToFirst();
            password.success(cursor.getString(0));
        }
    }

    //Xóa nhân viên thì phải xóa luôn account
    public void XoaAccount(NhanVien nhanVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Account", "manv ='" + nhanVien.getMaNV() + "'", null);
        db.close();
    }

    //Quên mật khẩu
    public void checkManv(String manv, onClickListenerForgot onClickListener){
        String sql = "Select password from Account where manv = '" + manv + "'";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql,null);
        if (cursor.getCount() == 0) {
            onClickListener.fail();
            Log.d("loi","Loi");
        }else {
            cursor.moveToFirst();
            onClickListener.success(cursor.getString(0));
            Log.d("ok","ok");
        }
    }

    public Accounts layAdmin(String sdt){
        Accounts accounts = new Accounts();

        String sql = "Select * from Account where sdt = '" + sdt + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            cursor.moveToFirst();
            do {
                accounts.setSdt(cursor.getString(0));
                accounts.setPassword(cursor.getString(1));
                accounts.setManv(cursor.getString(2));
            }
            while (cursor.moveToNext());
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return accounts;
    }

    public interface onClickListenerRs {
        void success();
        void fail();
    }

    public interface onClickListener {
        void success(String pass);
        void fail();
    }

    public interface onClickListenerForgot {
        void success(String pass);
        void fail();
    }
}
