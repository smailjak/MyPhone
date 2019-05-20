package com.example.sh.androidregisterandlogin.TotalMusic;

import android.app.Application;

public class AudioApplication extends Application {
    public static AudioApplication mInstance;
    public AudioServiceInterface mInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mInterface = new AudioServiceInterface(getApplicationContext());

    }

    public static AudioApplication getInstance() {
        return mInstance;
    }

    public  AudioServiceInterface getServiceInterface() {
        return mInterface;

    }
}

