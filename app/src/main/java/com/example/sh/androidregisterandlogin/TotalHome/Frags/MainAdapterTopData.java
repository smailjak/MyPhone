package com.example.sh.androidregisterandlogin.TotalHome.Frags;

public class MainAdapterTopData {

    private String folderName;
    private String photoCount;
    private long photoImg;

    public MainAdapterTopData(String folderName, String photoCount, long photoImg) {
        this.folderName = folderName;
        this.photoCount = photoCount;
        this.photoImg = photoImg;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(String photoCount) {
        this.photoCount = photoCount;
    }

    public long getPhotoImg() {
        return photoImg;
    }

    public void setPhotoImg(long photoImg) {
        this.photoImg = photoImg;
    }
}
