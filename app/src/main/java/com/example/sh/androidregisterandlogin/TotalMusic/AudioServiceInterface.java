package com.example.sh.androidregisterandlogin.TotalMusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class AudioServiceInterface {
    public ServiceConnection mServiceConnection;
    public  AudioService mService;

    public AudioServiceInterface(Context context) {
        mServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((AudioService.AudioServiceBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mServiceConnection = null;
                mService = null;
            }
        };
        context.bindService(new Intent(context, AudioService.class).setPackage(context.getPackageName()), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void setPlayList(ArrayList<Long> audioIds) {
        Log.d("AudioAdapter.qwe", "(처음 셋플레이리스트 : " + mService);
        Log.d("AudioAdapter.qwe", "(처음 셋플레이리스트 : " + audioIds.size());
        if (mService != null) {
            mService.setPlayList(audioIds);
        }
    }

    public void play(int position) {
        if (mService != null) {
            mService.play(position);
        }
    }

    public void play() {
        if (mService != null) {
            mService.play();
        }
    }

    public void togglePlay() {
        if (isPlaying()) {
            mService.pause();
        } else {
            mService.play();
        }
    }

    public void voice_togglePlay(){
        if(isPlaying()){
            mService.play();
        } else {
            play();
        }
    }

    public boolean isPlaying() {
        if (mService != null) {
            return mService.isPlaying();
        }
        return false;
    }

    public AudioAdapter.AudioItem getAudioItem() {
        if (mService != null) {
            return mService.getAudioItem();
        }
        return null;
    }

    public void pause() {
        if (mService != null) {
//            다른 액티비티로 갔을때 음악을 꺼지게 하고싶다면 이부분을 pause 하면되고
//            다른 액티비티로 갔을때도 , 음악이 작동되게 할려면 pause 부분을 play 로 바꾸면 됩니다.
            mService.play();
        }
    }

    public void real_pause() {
        if (mService != null ) {
//            다른 액티비티로 갔을때 음악을 꺼지게 하고싶다면 이부분을 pause 하면되고
//            다른 액티비티로 갔을때도 , 음악이 작동되게 할려면 pause 부분을 play 로 바꾸면 됩니다.
            mService.pause();

        }
    }



    public void forward() {
        if (mService != null) {
            mService.forward();
        }
    }

    public void rewind() {
        if (mService != null) {
            mService.rewind();
        }
    }
}

