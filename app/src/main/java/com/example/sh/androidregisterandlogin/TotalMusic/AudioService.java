package com.example.sh.androidregisterandlogin.TotalMusic;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class AudioService extends Service {
    public  IBinder mBinder = new AudioServiceBinder();
    private ArrayList<Long> mAudioIds = new ArrayList<>();
    public static MediaPlayer mMediaPlayer;
    private boolean isPrepared;
    private int mCurrentPosition;
    private AudioAdapter.AudioItem mAudioItem;

    public class AudioServiceBinder extends Binder {
        AudioService getService() { // 서비스 객체 리턴
            return AudioService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                mp.start();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                forward();
                ((TotalMusicActivity) TotalMusicActivity.mContext).updateUI();
                ((TotalMusicActivity) TotalMusicActivity.mContext).updatePlay();
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                isPrepared = false;
                return false;
            }
        });
        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
//        액티비티에서 bindService() 를 실행하면 호출됨 .
//        리턴한 IBinder 객체는 서비스와 클라이언트 사이의 인터페이스 정의한다.
        return mBinder; // 서비스 객체 리턴
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    // 현재 재생할 오디오의 정보를 불러오는 queryAudioItem
    private void queryAudioItem(int position) {
        mCurrentPosition = position;
        long audioId = mAudioIds.get(position);
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
//                  재생목록 기준으로 사용자로부터 재생할 목록의 position값을 받아서
//                  현재 재생할 음악에 대한 정보를 불러와 AudioItem에 저장 합니다.
        };
        String selection = MediaStore.Audio.Media._ID + " = ?";
        String[] selectionArgs = {String.valueOf(audioId)};
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                mAudioItem = AudioAdapter.AudioItem.bindCursor(cursor);
            }
            cursor.close();
        }
    }

    //  MediaPlayer를 재생가능한 상태로 만들어주는 prepare
//    오디오 스트림 타입은 STREAM_MUSIC 으로 지정합니다.
    private void prepare() {
        try {
            mMediaPlayer.setDataSource(mAudioItem.mDataPath);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public void setPlayList(ArrayList<Long> audioIds) {
        Log.d("AudioAdapter.qwe", "mAudioIds사이즈 : " + mAudioIds.size());
        Log.d("AudioAdapter.qwe", "audioIds.size() 사이즈 :  " + mAudioIds.size());
        if (mAudioIds.size() != audioIds.size()) {
            if (!mAudioIds.equals(audioIds)) {
                mAudioIds.clear();
                mAudioIds.addAll(audioIds);
            }
        }
    }

    public AudioAdapter.AudioItem getAudioItem() {
        return mAudioItem;
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }


    //    음악을 재생할 수 있는 play
//    position 인자값을 갖는 play.
    public void play(int position) {
        queryAudioItem(position);
//        query / Audio / Item ==> 질문 한다 오디오에 아이템을
        stop();
        prepare();
    }

    public void play() {
        if (isPrepared) { // true 일때
            mMediaPlayer.start();
        }
    }

    //   음악을 일시정지하는 pause
    public void pause() {
        if (isPrepared) {
            mMediaPlayer.pause();
        }
    }

    //    다음곡으로 이동하는 함수 forward
    public void forward() {
        if (mAudioIds.size() - 1 > mCurrentPosition) {
            mCurrentPosition++; // 다음 포지션으로 이동.
        } else {
            mCurrentPosition = 0; // 처음 포지션으로 이동.
        }
        play(mCurrentPosition);
    }

    //    이전곡으로 이동하는 함수 rewind
    public void rewind() {
        if (mCurrentPosition > 0) {
            mCurrentPosition--; // 이전 포지션으로 이동.
        } else {
            mCurrentPosition = mAudioIds.size() - 1; // 마지막 포지션으로 이동.
        }
        play(mCurrentPosition);
    }
}

