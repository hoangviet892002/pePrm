package com.example.sqlite;

public class Nganh {
    private int id;
    private String tenNganh;

    public Nganh(int id, String tenNganh) {
        this.id = id;
        this.tenNganh = tenNganh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }
}
