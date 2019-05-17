package com.example.sh.androidregisterandlogin.TotalHome;

import android.app.AlertDialog;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.speech.tts.TextToSpeech;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sh.androidregisterandlogin.TotalHome.Frags.MessageFragment;
import com.example.sh.androidregisterandlogin.TotalHome.Frags.PhotoFragment;
import com.example.sh.androidregisterandlogin.TotalHome.Frags.MainFragment;
import com.example.sh.androidregisterandlogin.TotalHome.Frags.PhoneBookFragment;
import com.example.sh.androidregisterandlogin.TotalHome.Frags.MusicFragment;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityCollectionBinding;
import com.example.sh.androidregisterandlogin.util.ActivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private ActivityCollectionBinding binding;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;

    private static final int LOADER_ID = 1;
    int music_count = 0;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);
//        sdkVersionPermission();
//        mContext = getApplicationContext();
//        tts = new TextToSpeech(this, this);
//
//        SpannableStringBuilder sp = new SpannableStringBuilder(Build.MODEL);
//        String str = Build.MODEL;
//        sp.setSpan(new ForegroundColorSpan(Color.GREEN), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        aiManagementTxtClick();
//        appManageBtnClick();
//        batteryManageBtnClick();
//        ramManageBtnClick();
//        picManageBtnClick();
//        messageManageBtnClick();
//        musicManageBtnClick();
//        phoneBookManageBtnClick();
//        mpInfoManageBtnClick();
//        speakBtnClick();
        BottomNavigationInit(binding.bnv);
    }

    private void BottomNavigationInit(BottomNavigationView bnv) {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), MainFragment.newInstance(), R.id.fl_main);
        bnv.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.m_home: {
                    changeScreen(itemId, MainFragment.newInstance());
                    return true;
                }
                case R.id.m_photo: {
                    changeScreen(itemId, PhotoFragment.newInstance());
                    return true;
                }
                case R.id.m_phonebook: {
                    changeScreen(itemId, PhoneBookFragment.newInstance());
                    return true;
                }
                case R.id.m_music: {
                    changeScreen(itemId, MusicFragment.newInstance());
                    return true;
                }
                default: {
                    return false;
                }
            }
        });
    }


    public void changeScreen(int itemId, Fragment fragment) {
        if (itemId != binding.bnv.getSelectedItemId()) { //같은 탭을 누르지 않았을 경우만 이동.
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_main);
        }
    }
//    private void sdkVersionPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
//                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
//            } else {
//                getAudioListFromMediaDatabase();
//            }
//        } else {
//            getAudioListFromMediaDatabase();
//        }
//    }

//    private void speakBtnClick() {
//        binding.speakBtn.setOnClickListener(new View.OnClickListener() {    // 마이크 버튼을 클릭했을경우
//            @Override
//            public void onClick(View v) {
//                promptSpeechInput();    // 말하는거 시작하는 함수
//            }
//        });
//    }
//
//    private void mpInfoManageBtnClick() {
//        binding.vTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, GeneralActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void phoneBookManageBtnClick() {
//        binding.phoneManageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ContactListActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void musicManageBtnClick() {
//        binding.musicManageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, TotalMusicActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void messageManageBtnClick() {
//        binding.messageManageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, SmsActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void picManageBtnClick() {
//        binding.picManageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, TotalPhotoActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void ramManageBtnClick() {
//        binding.ramManageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, MemoryActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void batteryManageBtnClick() {
//        binding.batteryManageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, BatteryActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void appManageBtnClick() {
//        binding.appManageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, UserAppsActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void aiManagementTxtClick() {
//        binding.tvAi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ManageActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//
//    private void promptSpeechInput() {  //
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        // 녹음하는거 실행
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                getString(R.string.speech_prompt));
//
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getApplicationContext(),
//                    getString(R.string.speech_not_supported),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * resultCode
//     * Receiving speech input
//     */
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == RESULT_OK && null != data) {
//
//                    ArrayList<String> result = data
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//
//                    binding.speechInputTxt.setText(result.get(0));  //분홍색 글씨
//                    String voice_result = binding.speechInputTxt.getText().toString();   // 이렇게적으면 안녕으로 바뀌게 된다 .
//                    String text, text1 = "안녕", test2 = "누구야";
//                    String pic = "사진", pic2 = "이미지";
//                    String app = "어플", battry = "배터리", music = "음악", music2 = "오디오", music3 = "노래";
//                    String music_start1 = "음악재생", music_start2 = "노래재생", music_title_start = "틀어줘";
//                    String musicCountVoice = "음악갯수몇개야", musicCountVoice2 = "음악개수몇개야", musicCountVoice3 = "음악몇개야";
//                    String message = "메세지", message2 = "문자", message3 = "메시지";
//                    String address = "연락처";
//                    /////////////////////////
//                    String result1 = voice_result.trim().replaceAll(" ", "");
//
//                    if (result1.replaceAll(" ", "").contains(text1) || result1.contains(test2)) {
//                        text = "네 안녕하세요. 저는 혀니 입니다.";
//                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//                    } else if (result1.contains(pic) || result1.contains(pic2)) {
//                        Intent intent = new Intent(HomeActivity.this, TotalPhotoActivity.class);
//                        startActivity(intent);
//                    } else if (result1.contains(musicCountVoice) || result1.contains(musicCountVoice2) || result1.contains(musicCountVoice3)) {
//                        TotalMusicActivity musicCount = new TotalMusicActivity();
////                       음악 몇개야 라고 했을때 여기로 들어오게 되어있음
////                        String musicCountParse = Integer.toString(musicCount.mAdapter.getAudioIds().size());
////                        Toast.makeText(musicCount, musicCountParse + " 개 입니다.", Toast.LENGTH_SHORT).show();
////                        Log.d("musicCountVoice", "qweqweqwe");
////                        여기 부분 오류가 발생한다. == > 액티비티로 넘어가지 않으니깐 거기에 대한 데이터를 못 불러오는것같다.
////                        static 을 해도 안되는것 같은데.. 어떻게 해야할까 .
//                    } else if (result1.contains(music_title_start)) {
//                        Intent intent = new Intent(HomeActivity.this, TotalMusicActivity.class);
//                        String title = result1.replaceAll("틀어줘", "");
//                        String music_title_start_in_music_start = "노래", music_title_start_in_music_start2 = "음악";
//
//                        if (music_title_start_in_music_start.equals(title) || music_title_start_in_music_start2.equals(title)) {
//                            Intent music_title_start_in_music_start_intent = new Intent(HomeActivity.this, TotalMusicActivity.class);
//                            music_title_start_in_music_start_intent.putExtra("music_start", 1);
//                            startActivity(intent);
//                        }
//                        intent.putExtra("music_title", title);
//                        startActivity(intent);
//                    } else if (result1.equals(music_start1) || result1.equals(music_start2)) {
//                        Intent intent = new Intent(HomeActivity.this, TotalMusicActivity.class);
//                        intent.putExtra("music_start", 1);
//                        startActivity(intent);
//                    } else if (result1.contains(address)) {
//                        Intent intent = new Intent(HomeActivity.this, ContactListActivity.class);
//                        startActivity(intent);
//                    } else if (result1.contains(app)) {
//                        Intent intent = new Intent(HomeActivity.this, UserAppsActivity.class);
//                        startActivity(intent);
//                    } else if (result1.contains(battry)) {
//                        Intent intent = new Intent(HomeActivity.this, BatteryActivity.class);
//                        startActivity(intent);
//                    } else if (result1.contains(music) || result1.contains(music2) || result1.contains(music3)) {
//                        Intent intent = new Intent(HomeActivity.this, TotalMusicActivity.class);
//                        startActivity(intent);
//                    } else if (result1.contains(message) || result1.contains(message2) || result1.contains(message3)) {
//                        Intent intent = new Intent(HomeActivity.this, SmsActivity.class);
//                        startActivity(intent);
//                    }
//                }
//                break;
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getAudioListFromMediaDatabase();
//        }
//    }
//
//    private void getAudioListFromMediaDatabase() {
//        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
//
//            @Override
//            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                String[] projection = new String[]{
//                        MediaStore.Audio.Media._ID,
//                        MediaStore.Audio.Media.TITLE,
//                        MediaStore.Audio.Media.ARTIST,
//                        MediaStore.Audio.Media.ALBUM,
//                        MediaStore.Audio.Media.ALBUM_ID,
//                        MediaStore.Audio.Media.DURATION,
//                        MediaStore.Audio.Media.DATA
//                };
//                String selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
//                String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE LOCALIZED ASC";
//                return new CursorLoader(getApplicationContext(), uri, projection, selection, null, sortOrder);
//            }
//
//            //          onLoadFinished 가 계속 불리는건가 ??
//            @Override
//            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//                music_count = 1;
//                data.moveToFirst(); // 만약에 cursor 라는 것이 아무것도 밑에 내려갈것이 없을때는 다시 맨위로 올려버린다.
//                if (data != null && data.getCount() > 0) {
//                    while (data.moveToNext()) {
//                        music_count++;
//                        Log.d("qwe", "Title:" + data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
//                    }
//                }
//            }
//
//            @Override
//            public void onLoaderReset(Loader<Cursor> loader) {
//
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
//       super.onBackPressed(); 를 주석처리하여 뒤로 가기 키를 눌러도 액티비티가 종료되지 않게 하였습니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("종료 확인");
        builder.setMessage("정말로 종료하시겠습니까 ?");
        builder.setPositiveButton("확인", (dialog, which) ->
                finish());
        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
