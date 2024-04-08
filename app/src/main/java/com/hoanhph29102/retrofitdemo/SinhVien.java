package com.hoanhph29102.retrofitdemo;

import com.google.gson.annotations.SerializedName;

public class SinhVien {
    private String _id;
    @SerializedName("tensv")
    private String tensv;
    @SerializedName("masv")
    private String masv;

    public SinhVien(String _id, String tensv, String masv) {
        this._id = _id;
        this.tensv = tensv;
        this.masv = masv;
    }

    public SinhVien(String masv, String tensv) {
        this.tensv = tensv;
        this.masv = masv;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public SinhVien() {
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }
}
