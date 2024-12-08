package com.example.timlamgi.Model;

public class NVChamCong {
    String maNV, soCong, ngayChamCong;

    public String getMaNV() {
        return maNV;
    }

    public String getNgayChamCong() {
        return ngayChamCong;
    }

    public void setNgayChamCong(String ngayChamCong) {
        this.ngayChamCong = ngayChamCong;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getSoCong() {
        return soCong;
    }

    public void setSoCong(String soNgayCong) {
        this.soCong = soNgayCong;
    }

    @Override
    public String toString() {
        return "NVChamCong{" +
                "maNV='" + maNV + '\'' +
                ", soCong='" + soCong + '\'' +
                ", ngayChamCong='" + ngayChamCong + '\'' +
                '}';
    }
}
