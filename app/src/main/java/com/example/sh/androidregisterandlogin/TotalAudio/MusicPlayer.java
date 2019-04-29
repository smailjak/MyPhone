package com.example.sh.androidregisterandlogin.TotalAudio;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener {

    private final static int LOADER_ID = 0x001;
    public static AudioAdapter mAdapter; // 질문 .
    public static Context music_Context;
    private ImageButton mBtnPlayPause;

    TextView music_title, music_subtitle;
    ImageView img_albumart;
    private ImageButton btn_play_pause, btn_forward, btn_rewind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        music_Context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                getAudioListFromMediaDatabase();
            }
        }
        // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
        else {
            getAudioListFromMediaDatabase();
        }
        mBtnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);

        findViewById(R.id.lin_miniplayer).setOnClickListener(this);
        mBtnPlayPause.setOnClickListener(this);
        findViewById(R.id.btn_rewind).setOnClickListener(this);
        findViewById(R.id.btn_forward).setOnClickListener(this);

        mAdapter = new AudioAdapter(this, null);
        // 제목 과  sub 타이틀
        music_title = findViewById(R.id.music_title);
        music_subtitle = findViewById(R.id.music_subtitle);
        // 플레이 사진
        img_albumart = findViewById(R.id.img_albumart);
        //플레이 리스트 이전 시작 다음곡
        btn_play_pause = findViewById(R.id.btn_play_pause);
        btn_forward = findViewById(R.id.btn_forward);
        btn_rewind = findViewById(R.id.btn_rewind);

        updateUI();
    } // onCreate 끝나는 부분

    public void updateUI() {
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()) {
//            재생중이 아니라면 pause 라는 그림을 보여주게됩니다.
            mBtnPlayPause.setImageResource(R.drawable.pause);
        } else {
//            그게 아니라면 play 그림을 보여줍니다.
            mBtnPlayPause.setImageResource(R.drawable.play);
        }
        AudioAdapter.AudioItem audioItem = AudioApplication.getInstance().getServiceInterface().getAudioItem();

        if (audioItem != null) {
            Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), audioItem.mAlbumId);
//            Glide.with(this )
//                    .load(albumArtUri)
//                    .into(img_albumart);
            RequestOptions circleCrop = new RequestOptions().circleCrop();
            Glide.with(this)
                    .load(albumArtUri)
                    .apply(RequestOptions.errorOf(R.drawable.music))
                    .apply(circleCrop)
                    .into(img_albumart);
            music_title.setText(audioItem.mTitle);
        } else {
            img_albumart.setImageResource(R.drawable.music);
            music_title.setText("재생중인 음악이 없습니다.");
        }
    }

    public void updatePlay() {
        mBtnPlayPause.setImageResource(R.drawable.pause);
    }

    public void updateForward() {
        AudioApplication.getInstance().getServiceInterface().forward();
    }

    private void getAudioListFromMediaDatabase() {
        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projection = new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA
                };
                String selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
                String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE LOCALIZED ASC";
                return new CursorLoader(getApplicationContext(), uri, projection, selection, null, sortOrder);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                mAdapter.swapCursor(data);
                Log.d("MusicPlater.qwe", "mAdapter.swapCursor(data) : " + mAdapter.swapCursor(data));

                if (data != null && data.getCount() > 0) {
                    while (data.moveToNext()) {
                        Log.d("qwe", "Title:" + data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // READ_EXTERNAL_STORAGE 에 대한 권한 획득.
            getAudioListFromMediaDatabase();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_miniplayer:
                // 플레이어 화면으로 이동할 코드가 들어갈 예정
                break;
            case R.id.btn_rewind:
                // 이전곡으로 이동
                AudioApplication.getInstance().getServiceInterface().rewind();
                Toast.makeText(MusicPlayer.this, "이전곡", Toast.LENGTH_SHORT).show();
                updateUI();
                mBtnPlayPause.setImageResource(R.drawable.pause);
                break;
            case R.id.btn_play_pause:
                // 재생 또는 일시정지
                AudioApplication.getInstance().getServiceInterface().togglePlay();
                updateUI();
                break;
            case R.id.btn_forward:
                // 다음곡으로 이동
                AudioApplication.getInstance().getServiceInterface().forward();
                Toast.makeText(MusicPlayer.this, "다음곡", Toast.LENGTH_SHORT).show();
                updateUI();
                mBtnPlayPause.setImageResource(R.drawable.pause);
                break;
        }
    }
}
