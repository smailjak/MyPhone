package com.example.sh.androidregisterandlogin.TotalMusic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.TotalHome.HomeActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityTotalMusicBinding;

public class TotalMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int LOADER_ID = 0x001;

    private ActivityTotalMusicBinding binding;
    public static Context mContext;
    public AudioAdapter audioAdapter;
    private int musicSelectPosition = 0;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_total_music);

        mContext = this;
        manifestPermissionCheck();

        audioAdapter = new AudioAdapter(this, null);
        binding.totalMusicRcv.setAdapter(audioAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.totalMusicRcv.setLayoutManager(layoutManager);
        binding.btnPlayPause.setOnClickListener(this);

        findViewById(R.id.img_mini_music).setOnClickListener(this);
        findViewById(R.id.btn_rewind).setOnClickListener(this);
        findViewById(R.id.btn_forward).setOnClickListener(this);

        musicSelectPlay();
        updateUI();
    } // onCreate

    private void manifestPermissionCheck() {
        // OS가 Marshmallow 이상일 경우 권한체크를 해야 합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                // READ_EXTERNAL_STORAGE 에 대한 권한이 있음.
                getAudioListFromMediaDatabase();
            }
        }
        // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
        else {
            getAudioListFromMediaDatabase();
        }
    }

    private void musicSelectPlay() {
        Log.d("start123", "musicSelectPlay: 여기로 들어올려나 ??? ");
        Intent intent = getIntent();
//       노래 제목없이 그냥 음악 실행할 경우
        int start = intent.getIntExtra("music_start", 0);
        Log.d("start123", "musicSelectPlay: "+start);
        if (start == 1) {
            AudioApplication.getInstance().getServiceInterface().voice_togglePlay();
        }
//       노래 제목을 얘기했을 경우
        String music_title = intent.getStringExtra("music_title");
        Log.d("TotalMusicActivity.qwer", "music_title : " + music_title);
        String title = "노래";
        String title2 = "음악";
        Log.d("start123", "musicSelectPlay: 설마 여기로도 들어오나 ?? ");
        if (title.equals(music_title) || title2.equals(music_title)) {
            AudioApplication.getInstance().getServiceInterface().voice_togglePlay();
        }
    }

    public void updateUI() {
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()) {
            binding.btnPlayPause.setImageResource(R.drawable.pause);

        } else {
            binding.btnPlayPause.setImageResource(R.drawable.play);
        }

        AudioAdapter.AudioItem audioItem = AudioApplication.getInstance().getServiceInterface().getAudioItem();

        if (audioItem != null) {
//           null 이 아니라면
            Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), audioItem.mAlbumId);
            RequestOptions circleOptions = new RequestOptions().circleCrop();
            Glide.with(this)
                    .load(albumArtUri)
                    .apply(circleOptions)
                    .into(binding.imgMiniMusic);
            binding.txtTitle.setText(audioItem.mTitle);
        } else {
            binding.imgMiniMusic.setImageResource(R.drawable.music);
            binding.txtTitle.setText("재생중인 음악이 없습니다.");
        }
    }

    //        AudioAdapter 에서 position 을 선택했을때 재생버튼 을 설정하는 부분
    public void updatePlay() {
        binding.btnPlayPause.setImageResource(R.drawable.pause);
//               AudioAdapter 에서 사용 ((TotalMusicActivity) TotalMusicActivity.mContext).updatePlay();
    }

    private void getAudioListFromMediaDatabase() {
//      여기는 데이터베이스처럼 칸을 만들어 주는것인가 ??
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

                audioAdapter.swapCursor(data);
                data.moveToFirst(); // 만약에 cursor 라는 것이 아무것도 밑에 내려갈것이 없을때는 다시 맨위로 올려버린다.
                if (data != null && data.getCount() > 0) {
                    while (data.moveToNext()) {
//                       음악 총 개수를 구하기 위해 더합니다.
                        musicSelectPosition++;
                        String change = data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)).replaceAll(" ", "");
                        Intent intent = getIntent();
                        String music_title = intent.getStringExtra("music_title");
                        if (music_title != null) {
                            if (music_title.equals(change)) {
                                AudioApplication.getInstance().getServiceInterface().setPlayList(audioAdapter.getAudioIds()); // 재생목록등록
                                AudioApplication.getInstance().getServiceInterface().play(musicSelectPosition); // 선택한 오디오재생
                                updateUI();
                                updatePlay();
                            }
                        }
                    }
                }
                binding.musicNumber.setText("음악개수 : " + audioAdapter.getAudioIds().size());
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                audioAdapter.swapCursor(null);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_mini_music:
                Log.d("img_mini_music", "onClick: 클릭되어지아 ??");
                // 플레이어 화면으로 이동할 코드가 들어갈 예정
                Intent intent = new Intent(TotalMusicActivity.this, MusicPlayer.class);
                updateUI();
//                updateUI 라는 함수가 있는 이유는 플레이하고나서 UI를 바꿔주기위해 이 함수가 필요합니다.
                startActivity(intent);
                break;
            case R.id.btn_rewind:
                // 이전곡으로 이동
                AudioApplication.getInstance().getServiceInterface().rewind();
                Toast.makeText(TotalMusicActivity.this, "이전곡", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TotalMusicActivity.this, "다음곡", Toast.LENGTH_SHORT).show();
                updateUI();
                binding.btnPlayPause.setImageResource(R.drawable.pause);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioApplication.getInstance().getServiceInterface().real_pause();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TotalMusicActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

