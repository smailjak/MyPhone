package com.example.sh.androidregisterandlogin.TotalApp;

import android.graphics.drawable.Drawable;

//    creating AppLIst class
public class AppList {
    String name; //name of app
    Drawable icon; // icon of app
    String packages; // package of app
    String version; //version of app

    //    constructure
    AppList(String name, Drawable icon, String packages, String version) {
        this.name = name;
        this.icon = icon;
        this.packages = packages;
        this.version = version;
    }

    //        press Alt+Insert to insert getters and setters
    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPackages() {
        return packages;
    }

    public String getVersion() {
        return version;
    }
}
