package com.example.sh.androidregisterandlogin.TotalPhoto.DetailFile;


import java.util.ArrayList;

public class File_images {

    ArrayList<String> alFilePath;
    String photoName;

    public File_images(ArrayList<String> alFilePath, String photoName) {
        this.alFilePath = alFilePath;
        this.photoName = photoName;
    }

    public ArrayList<String> getAlFilePath() {
        return alFilePath;
    }

    public void setAlFilePath(ArrayList<String> alFilePath) {
        this.alFilePath = alFilePath;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String phtoName) {
        this.photoName = phtoName;
    }
}
