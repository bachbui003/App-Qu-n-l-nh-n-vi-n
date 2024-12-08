package com.example.timlamgi.Model;

public class TamUng {
    String soPhieu, maNV, ngayTamUng, soTienUng;

    public String getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(String soPhieu) {
        this.soPhieu = soPhieu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayTamUng() {
        return ngayTamUng;
    }

    public void setNgayTamUng(String ngayTamUng) {
        this.ngayTamUng = ngayTamUng;
    }

    public String getSoTienUng() {
        return soTienUng;
    }

    public void setSoTienUng(String soTienUng) {
        this.soTienUng = soTienUng;
    }

    @Override
    public String toString() {
        return "TamUng{" +
                "soPhieu='" + soPhieu + '\'' +
                ", maNV='" + maNV + '\'' +
                ", ngayTamUng='" + ngayTamUng + '\'' +
                ", soTienUng='" + soTienUng + '\'' +
                '}';
    }
}
