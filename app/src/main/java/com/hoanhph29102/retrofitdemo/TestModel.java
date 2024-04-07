package com.hoanhph29102.retrofitdemo;

public class TestModel {
    private String tensv;
    private String masv;

    public TestModel(String tensv, String masv) {
        this.tensv = tensv;
        this.masv = masv;
    }

    public TestModel() {
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
