package com.example.sh.androidregisterandlogin.TotalMusic;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityMusicPlayerBinding;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener {

    private ActivityMusicPlayerBinding binding;
    private final static int LOADER_ID = 0x001;
    public static AudioAdapter mAdapter; // 질문 .
    public static Context music_Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_player);

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
        binding.btnPlayPause.setOnClickListener(this);
        findViewById(R.id.btn_rewind).setOnClickListener(this);
        findViewById(R.id.btn_forward).setOnClickListener(this);
        mAdapter = new AudioAdapter(this, null);
        updateUI();
    } // onCreate 끝나는 부분

    public void updateUI() {
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()) {
//            재생중이 아니라면 pause 라는 그림을 보여주게됩니다.
            updatePlay();
            binding.btnPlayPause.setImageResource(R.drawable.pause);
        } else {
//            그게 아니라면 play 그림을 보여줍니다.
            binding.btnPlayPause.setImageResource(R.drawable.play);
        }
        AudioAdapter.AudioItem audioItem = AudioApplication.getInstance().getServiceInterface().getAudioItem();

        if (audioItem != null) {
            Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), audioItem.mAlbumId);
            RequestOptions circleCrop = new RequestOptions().circleCrop();
            Glide.with(this)
                    .load(albumArtUri)
                    .apply(RequestOptions.errorOf(R.drawable.music))
                    .apply(circleCrop)
                    .into(binding.imgAlbumart);
            binding.musicTitle.setText(audioItem.mTitle);
        } else {
            binding.imgAlbumart.setImageResource(R.drawable.music);
            binding.musicTitle.setText("재생중인 음악이 없습니다.");
        }
    }

    public void updatePlay() {
        binding.btnPlayPause.setImageResource(R.drawable.pause);
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
            case R.id.btn_rewind:
                // 이전곡으로 이동
                AudioApplication.getInstance().getServiceInterface().rewind();
                Toast.makeText(MusicPlayer.this, "이전곡", Toast.LENGTH_SHORT).show();
                updateUI();
                binding.btnPlayPause.setImageResource(R.drawable.pause);
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
                binding.btnPlayPause.setImageResource(R.drawable.pause);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MusicPlayer.this, TotalMusicActivity.class);
        startActivity(intent);
    }
}
