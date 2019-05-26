package com.example.sh.androidregisterandlogin.TotalHome.Datas;

public class PhonePriceDataItem {
    private String title;
    private String img_url;
    private String my_gosi;
    private String my_birthday;
    private String sell_money;
    private String my_shipment;
    private String my_sell_money;

    public PhonePriceDataItem(String title , String url , String my_gosi , String my_birthday , String sell_money , String my_shipment , String my_sell_money){
        this.title = title;
        this.img_url = url;
        this.my_gosi = my_gosi;
        this.my_birthday = my_birthday;
        this.sell_money = sell_money;
        this.my_shipment = my_shipment;
        this.my_sell_money = my_sell_money;

    }

    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getRelease() {
        return my_gosi;
    }

    public String getDirector() {
        return my_birthday;
    }

    public String getSell_money() {
        return sell_money;
    }

    public String getMy_sell_money() {
        return my_sell_money;
    }

    public String getMy_shipment() {
        return my_shipment;
    }
}

