package com.example.sh.androidregisterandlogin.data;

public class AddressData {
    private long photoid;
    private String phonenum;
    private String name;

    public long getPhotoid() {
        return photoid;
    }

    public void setPhotoid(long photoid) {
        this.photoid = photoid;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressData(long photoid, String phonenum, String name) {
        this.photoid = photoid;
        this.phonenum = phonenum;
        this.name = name;
    }

    public AddressData() {
    }
}

