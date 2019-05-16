package com.example.sh.androidregisterandlogin.TotalHome.Frags;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalApp.UserAppsActivity;
import com.example.sh.androidregisterandlogin.TotalAudio.TotalMusicActivity;
import com.example.sh.androidregisterandlogin.TotalBattery.BatteryActivity;
import com.example.sh.androidregisterandlogin.TotalHome.Adapters.FragmentMainAdapter;
import com.example.sh.androidregisterandlogin.TotalHome.Datas.Model;
import com.example.sh.androidregisterandlogin.TotalPhoto.TotalFolder.TotalPhotoActivity;
import com.example.sh.androidregisterandlogin.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.Locale;


public class MainFragment extends Fragment {

    FragmentMainBinding fragmentMainBinding;
    FragmentMainAdapter fragmentMainAdapter;
    SearchView searchView;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return fragmentMainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(fragmentMainBinding.toolbar);
        setHasOptionsMenu(true);

        initCollapsingToolbar();
        initRcv();

    }

    private void initRcv() {
        fragmentMainAdapter = new FragmentMainAdapter(getContext(), getModels());
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        fragmentMainBinding.rcvMain.setLayoutManager(staggeredGridLayoutManager);
        fragmentMainBinding.rcvMain.setHasFixedSize(true);
        fragmentMainBinding.rcvMain.setAdapter(fragmentMainAdapter);

    }


    private void initCollapsingToolbar() {
        fragmentMainBinding.collapsingToolbar.setTitle("");
        fragmentMainBinding.appbar.setExpanded(true);
        fragmentMainBinding.collapsingToolbar.setTitle("U Soft Company");
    }


    private ArrayList<Model> getModels() {
        ArrayList<Model> models = new ArrayList<>();
//        Model p = new Model();
        Model p;

//        여기서는 setName 에 다가 이름을 입력
        p = new Model();
        p.setName("폰 정보");
        p.setImg(R.drawable.app);
        models.add(p);

        p = new Model();
        p.setName("Photo");
        p.setImg(R.drawable.battery);
        models.add(p);

        p = new Model();
        p.setName("Music");
        p.setImg(R.drawable.music);
        models.add(p);  //now run the project , im checking in nougat and oreo

        p = new Model();
        p.setName("Battery");
        p.setImg(R.drawable.battery);
        models.add(p);

        p = new Model();
        p.setName("System Apps");
        p.setImg(R.drawable.photo_manage);
        models.add(p);

        p = new Model();
        p.setName("Memory");
        p.setImg(R.drawable.total_photo);
        models.add(p);

        p = new Model();
        p.setName("CPU");
        p.setImg(R.drawable.question);
        models.add(p);

        p = new Model();
        p.setName("PhoneBook");
        p.setImg(R.drawable.battery);
        models.add(p);

        return models;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                키보드의 검색 버튼을 누르면 이 함수가 호출됩니다.
                fragmentMainAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                이 함수는 searchview에 입력 할 때마다 호출됩니다.
                fragmentMainAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        다른 메뉴 항목 클릭을 여기에서 처리하십시오.
        if (id == R.id.action_settings) {
            Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_voice) {
            Toast.makeText(getContext(), "Voice", Toast.LENGTH_SHORT).show();
            promptSpeechInput();
        }
        return super.onOptionsItemSelected(item);
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
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == getActivity().RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    fragmentMainBinding.speechInputTxt.setText(result.get(0));  //분홍색 글씨
                    String voice_result = fragmentMainBinding.speechInputTxt.getText().toString();   // 이렇게적으면 안녕으로 바뀌게 된다 .
                    String text, text1 = "안녕", test2 = "누구야";
                    String pic = "사진", pic2 = "이미지";
                    String app = "어플", battry = "배터리", music = "음악", music2 = "오디오", music3 = "노래";
                    String music_start1 = "음악재생", music_start2 = "노래재생", music_title_start = "틀어줘";
                    String message = "메세지", message2 = "문자", message3 = "메시지";
                    String address = "연락처";
                    /////////////////////////
                    String result1 = voice_result.trim().replaceAll(" ", "");

                    if (result1.replaceAll(" ", "").contains(text1) || result1.contains(test2)) {
                        text = "네 안녕하세요. 저는 혀니 입니다.";
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    } else if (result1.contains(pic) || result1.contains(pic2)) {
                        Intent intent = new Intent(getContext(), TotalPhotoActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(music_title_start)) {
                        Intent intent = new Intent(getContext(), TotalMusicActivity.class);
                        String title = result1.replaceAll("틀어줘", "");
                        String music_title_start_in_music_start = "노래", music_title_start_in_music_start2 = "음악";

                        if (music_title_start_in_music_start.equals(title) || music_title_start_in_music_start2.equals(title)) {
                            Intent music_title_start_in_music_start_intent = new Intent(getContext(), TotalMusicActivity.class);
                            music_title_start_in_music_start_intent.putExtra("music_start", 1);
                            startActivity(intent);
                        }
                        intent.putExtra("music_title", title);
                        startActivity(intent);
                    } else if (result1.equals(music_start1) || result1.equals(music_start2)) {
                        Intent intent = new Intent(getContext(), TotalMusicActivity.class);
                        intent.putExtra("music_start", 1);
                        startActivity(intent);
                    } else if (result1.contains(address)) {
                        Intent intent = new Intent(getContext(), PhoneBookFragment.class);
                        startActivity(intent);
                    } else if (result1.contains(app)) {
                        Intent intent = new Intent(getContext(), UserAppsActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(battry)) {
                        Intent intent = new Intent(getContext(), BatteryActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(music) || result1.contains(music2) || result1.contains(music3)) {
                        Intent intent = new Intent(getContext(), TotalMusicActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(message) || result1.contains(message2) || result1.contains(message3)) {
                        Intent intent = new Intent(getContext(),MessageFragment.class);
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
        (getActivity()).getSupportLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {

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
                return new CursorLoader(getContext(), uri, projection, selection, null, sortOrder);
            }

            //          onLoadFinished 가 계속 불리는건가 ??
            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                data.moveToFirst(); // 만약에 cursor 라는 것이 아무것도 밑에 내려갈것이 없을때는 다시 맨위로 올려버린다.
                if (data != null && data.getCount() > 0) {
                    while (data.moveToNext()) {
                        Log.d("qwe", "Title:" + data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
    }

}
