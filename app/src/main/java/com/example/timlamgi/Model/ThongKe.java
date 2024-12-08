package com.example.timlamgi.Model;

public class ThongKe {
    String maNV, tenNV, tenPhong, heSoLuong, luongCoBan, ngayChamCong, soNgayCong, tienTamUng, luongThucLanh;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(String heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public String getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(String luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public String getNgayChamCong() {
        return ngayChamCong;
    }

    public void setNgayChamCong(String ngayChamCong) {
        this.ngayChamCong = ngayChamCong;
    }

    public String getSoNgayCong() {
        return soNgayCong;
    }

    public void setSoNgayCong(String soNgayCong) {
        this.soNgayCong = soNgayCong;
    }

    public String getTienTamUng() {
        return tienTamUng;
    }

    public void setTienTamUng(String tienTamUng) {
        this.tienTamUng = tienTamUng;
    }

    public String getLuongThucLanh() {
        return luongThucLanh;
    }

    public void setLuongThucLanh(String luongThucLanh) {
        this.luongThucLanh = luongThucLanh;
    }

    @Override
    public String toString() {
        return "ThongKe{" +
                "maNV='" + maNV + '\'' +
                ", tenNV='" + tenNV + '\'' +
                ", tenPhong='" + tenPhong + '\'' +
                ", heSoLuong='" + heSoLuong + '\'' +
                ", luongCoBan='" + luongCoBan + '\'' +
                ", ngayChamCong='" + ngayChamCong + '\'' +
                ", soNgayCong='" + soNgayCong + '\'' +
                ", tienTamUng='" + tienTamUng + '\'' +
                ", luongThucLanh='" + luongThucLanh + '\'' +
                '}';
    }
}
