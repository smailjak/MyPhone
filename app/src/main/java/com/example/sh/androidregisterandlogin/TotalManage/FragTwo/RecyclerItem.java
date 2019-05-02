package com.example.sh.androidregisterandlogin.TotalManage.FragTwo;


public class RecyclerItem {

    private long imageView;
    private String textView;

    public RecyclerItem(long imageView, String textView) {
        this.imageView = imageView;
        this.textView = textView;
    }

    public long getImageView() {
        return imageView;
    }

    public void setImageView(long imageView) {
        this.imageView = imageView;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }
}
