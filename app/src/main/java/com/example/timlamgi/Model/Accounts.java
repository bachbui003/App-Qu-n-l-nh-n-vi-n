package com.example.timlamgi.Model;

public class Accounts {

    String sdt, password, manv;

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    @Override
    public String toString() {
        return "Account{" +
                "sdt='" + sdt + '\'' +
                ", password='" + password + '\'' +
                ", manv='" + manv + '\'' +
                '}';
    }
}
