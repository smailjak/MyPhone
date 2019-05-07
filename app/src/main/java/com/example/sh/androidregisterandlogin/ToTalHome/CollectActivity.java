package com.example.sh.androidregisterandlogin.ToTalHome;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.AdvertisingActivity;
import com.example.sh.androidregisterandlogin.TotalMemory.MemoryActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalAddress.ContactListActivity;
import com.example.sh.androidregisterandlogin.TotalApp.UserAppsActivity;
import com.example.sh.androidregisterandlogin.TotalAudio.TotalMusicActivity;
import com.example.sh.androidregisterandlogin.TotalBattery.BatteryActivity;
import com.example.sh.androidregisterandlogin.TotalManage.ManageActivity;
import com.example.sh.androidregisterandlogin.TotalMessage.Sms.SmsActivity;
import com.example.sh.androidregisterandlogin.TotalPhoneInfo.GeneralActivity;
import com.example.sh.androidregisterandlogin.TotalPhoto.TotalFolder.TotalPhotoActivity;
import com.example.sh.androidregisterandlogin.databinding.ActivityCollectionBinding;

import java.util.ArrayList;
import java.util.Locale;

public class CollectActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private ActivityCollectionBinding binding;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    // 위에서 말하게 되면 = > 그것을 전달 받아서 적히는 부분이 됨 .
    private TextToSpeech tts;

    private static final int LOADER_ID = 1;
    int music_count = 0;
    Context mContext;
    //side bar 메뉴
    ////////////////////////////////////////// 오디오 관리


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);

        mContext = getApplicationContext();
        tts = new TextToSpeech(this, this);
        binding.myphoneDeviceTxt.setText(Build.MODEL);
        binding.myphoneManufactureTxt.setText(Build.MANUFACTURER);
        sdkVersionPermission();
        aiManagementTxtClick();
        appManageBtnClick();
        batteryManageBtnClick();
        ramManageBtnClick();
        picManageBtnClick();
        messageManageBtnClick();
        musicManageBtnClick();
        phoneBookManageBtnClick();
        mpInfoManageBtnClick();
        speakBtnClick();

    } // onCreate 끝나는 부분

    private void sdkVersionPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CollectActivity.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                getAudioListFromMediaDatabase();
            }
        } else {
            getAudioListFromMediaDatabase();
        }
    }

    private void speakBtnClick() {
        binding.speakBtn.setOnClickListener(new View.OnClickListener() {    // 마이크 버튼을 클릭했을경우
            @Override
            public void onClick(View v) {
                promptSpeechInput();    // 말하는거 시작하는 함수
            }
        });
    }

    private void mpInfoManageBtnClick() {
        binding.mpInfoManageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, GeneralActivity.class);
                startActivity(intent);
            }
        });
    }

    private void phoneBookManageBtnClick() {
        binding.phoneManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void musicManageBtnClick() {
        binding.musicManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, TotalMusicActivity.class);
                startActivity(intent);
            }
        });
    }

    private void messageManageBtnClick() {
        binding.messageManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, SmsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void picManageBtnClick() {
        binding.picManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, TotalPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ramManageBtnClick() {
        binding.ramManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void batteryManageBtnClick() {
        binding.batteryManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, BatteryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void appManageBtnClick() {
        binding.appManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, UserAppsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void aiManagementTxtClick() {
        binding.aiMoveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });
    }


    private void promptSpeechInput() {  //
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // 녹음하는거 실행
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * resultCode
     * Receiving speech input
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    binding.speechInputTxt.setText(result.get(0));  //분홍색 글씨
                    String voice_result = binding.speechInputTxt.getText().toString();   // 이렇게적으면 안녕으로 바뀌게 된다 .
                    String text, text1 = "안녕", test2 = "누구야";
                    String pic = "사진", pic2 = "이미지";
                    String app = "어플", battry = "배터리", music = "음악", music2 = "오디오", music3 = "노래";
                    String music_start1 = "음악재생", music_start2 = "노래재생", music_title_start = "틀어줘";
                    String musicCountVoice = "음악갯수몇개야", musicCountVoice2 = "음악개수몇개야", musicCountVoice3 = "음악몇개야";
                    String message = "메세지", message2 = "문자", message3 = "메시지";
                    String address = "연락처";
                    /////////////////////////
                    String result1 = voice_result.trim().replaceAll(" ", "");

                    if (result1.replaceAll(" ", "").contains(text1) || result1.contains(test2)) {
                        text = "네 안녕하세요. 저는 혀니 입니다.";
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    } else if (result1.contains(pic) || result1.contains(pic2)) {
                        Intent intent = new Intent(CollectActivity.this, TotalPhotoActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(musicCountVoice) || result1.contains(musicCountVoice2) || result1.contains(musicCountVoice3)) {
                        TotalMusicActivity musicCount = new TotalMusicActivity();
//                       음악 몇개야 라고 했을때 여기로 들어오게 되어있음
//                        String musicCountParse = Integer.toString(musicCount.mAdapter.getAudioIds().size());
//                        Toast.makeText(musicCount, musicCountParse + " 개 입니다.", Toast.LENGTH_SHORT).show();
//                        Log.d("musicCountVoice", "qweqweqwe");
//                        여기 부분 오류가 발생한다. == > 액티비티로 넘어가지 않으니깐 거기에 대한 데이터를 못 불러오는것같다.
//                        static 을 해도 안되는것 같은데.. 어떻게 해야할까 .
                    } else if (result1.contains(music_title_start)) {
                        Intent intent = new Intent(CollectActivity.this, TotalMusicActivity.class);
                        String title = result1.replaceAll("틀어줘", "");
                        String music_title_start_in_music_start = "노래", music_title_start_in_music_start2 = "음악";

                        if (music_title_start_in_music_start.equals(title) || music_title_start_in_music_start2.equals(title)) {
                            Intent music_title_start_in_music_start_intent = new Intent(CollectActivity.this, TotalMusicActivity.class);
                            music_title_start_in_music_start_intent.putExtra("music_start", 1);
                            startActivity(intent);
                        }
                        intent.putExtra("music_title", title);
                        startActivity(intent);
                    } else if (result1.equals(music_start1) || result1.equals(music_start2)) {
                        Intent intent = new Intent(CollectActivity.this, TotalMusicActivity.class);
                        intent.putExtra("music_start", 1);
                        startActivity(intent);
                    } else if (result1.contains(address)) {
                        Intent intent = new Intent(CollectActivity.this, ContactListActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(app)) {
                        Intent intent = new Intent(CollectActivity.this, UserAppsActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(battry)) {
                        Intent intent = new Intent(CollectActivity.this, BatteryActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(music) || result1.contains(music2) || result1.contains(music3)) {
                        Intent intent = new Intent(CollectActivity.this, TotalMusicActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(message) || result1.contains(message2) || result1.contains(message3)) {
                        Intent intent = new Intent(CollectActivity.this, SmsActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAudioListFromMediaDatabase();
        }
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

            //          onLoadFinished 가 계속 불리는건가 ??
            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                music_count = 1;
                data.moveToFirst(); // 만약에 cursor 라는 것이 아무것도 밑에 내려갈것이 없을때는 다시 맨위로 올려버린다.
                if (data != null && data.getCount() > 0) {
                    while (data.moveToNext()) {
                        music_count++;
                        Log.d("qwe", "Title:" + data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CollectActivity.this, AdvertisingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onInit(int status) {

    }
}
