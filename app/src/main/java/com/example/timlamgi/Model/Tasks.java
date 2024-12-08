package com.example.timlamgi.Model;

public class Tasks {

    String matask, manv, noidung, ngay;

    public String getMatask() {
        return matask;
    }

    public void setMatask(String matask) {
        this.matask = matask;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "matask='" + matask + '\'' +
                ", manv='" + manv + '\'' +
                ", noidung='" + noidung + '\'' +
                ", ngay='" + ngay + '\'' +
                '}';
    }
}
