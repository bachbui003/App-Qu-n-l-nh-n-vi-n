package com.example.timlamgi.Model;

public class ChamCong {
    String maNV, thangChamCong, soNgayCong;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getThangChamCong() {
        return thangChamCong;
    }

    public void setThangChamCong(String thangChamCong) {
        this.thangChamCong = thangChamCong;
    }

    public String getSoNgayCong() {
        return soNgayCong;
    }

    public void setSoNgayCong(String soNgayCong) {
        this.soNgayCong = soNgayCong;
    }

    @Override
    public String toString() {
        return "ChamCong{" +
                "maNV='" + maNV + '\'' +
                ", thangChamCong='" + thangChamCong + '\'' +
                ", soNgayCong='" + soNgayCong + '\'' +
                '}';
    }
}
