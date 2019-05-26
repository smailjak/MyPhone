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
import android.widget.EditText;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh.androidregisterandlogin.CompanyIntroActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalApp.UserAppsActivity;
import com.example.sh.androidregisterandlogin.TotalBattery.BatteryActivity;
import com.example.sh.androidregisterandlogin.TotalHome.Adapters.MainAdapter;
import com.example.sh.androidregisterandlogin.TotalMusic.TotalMusicActivity;
import com.example.sh.androidregisterandlogin.data.AdditionalFeature;
import com.example.sh.androidregisterandlogin.databinding.FragmentMainBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private MainAdapter adapter;
    private SearchView searchView;
    ArrayList<AdditionalFeature> additionalFeatures = new ArrayList<>();
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);
        initCollapsingToolbar(binding.collapsingToolbar);
        initRcv(binding.rcvMain);

        binding.imgCompanyIntro.setOnClickListener(a -> {
            Intent intent = new Intent(getContext(), CompanyIntroActivity.class);
            startActivity(intent);
        });
    }

    private void initRcv(RecyclerView rcv) {
        adapter = new MainAdapter(getModels());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adapter);
    }

    private void initCollapsingToolbar(CollapsingToolbarLayout ctl) {
        ctl.setTitle("");
        binding.appbar.setExpanded(true);
        ctl.setTitle("U&Soft Company");
        ctl.setCollapsedTitleTextAppearance(R.style.coll_main_basic_title);
        ctl.setExpandedTitleTextAppearance(R.style.coll_expand_title);
    }

    private ArrayList<AdditionalFeature> getModels() {

        AdditionalFeature additionalFeature;

        additionalFeature = new AdditionalFeature();
        additionalFeature.setName("Music");
        additionalFeature.setImg(R.drawable.ic_main_music);
        additionalFeatures.add(additionalFeature);

        additionalFeature = new AdditionalFeature();
        additionalFeature.setName("Battery");
        additionalFeature.setImg(R.drawable.ic_main_batter);
        additionalFeatures.add(additionalFeature);

        additionalFeature = new AdditionalFeature();
        additionalFeature.setName("App");
        additionalFeature.setImg(R.drawable.appimage);
        additionalFeatures.add(additionalFeature);

        additionalFeature = new AdditionalFeature();
        additionalFeature.setName("info");
        additionalFeature.setImg(R.drawable.measure);
        additionalFeatures.add(additionalFeature);

        return additionalFeatures;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        ((EditText) searchView.findViewById(R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.colorPrimary));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                item.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<AdditionalFeature> filtermodellist = filter(additionalFeatures, newText);
                adapter.setfileter(filtermodellist);
                return false;
            }
        });
    }

    private List<AdditionalFeature> filter(List<AdditionalFeature> p1, String query) {
        query = query.toLowerCase();
        final List<AdditionalFeature> filteredModelList = new ArrayList<>();
        for (AdditionalFeature model : p1) {
            final String text = model.getName().toLowerCase();
            if (text.startsWith(query)) {
                filteredModelList.add(model);

            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_voice) {
            Toast.makeText(getContext(), "Voice", Toast.LENGTH_SHORT).show();
            promptSpeechInput();
        }
        return super.onOptionsItemSelected(item);
    }

    private void promptSpeechInput() {
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

                    binding.speechInputTxt.setText(result.get(0));  //분홍색 글씨
                    String voice_result = binding.speechInputTxt.getText().toString();   // 이렇게적으면 안녕으로 바뀌게 된다 .
                    String text, text1 = "안녕", test2 = "누구야";
                    String app = "어플", battry = "배터리", music = "음악", music2 = "오디오", music3 = "노래";
                    String music_start1 = "음악재생", music_start2 = "노래재생", music_title_start = "틀어줘";
                    String result1 = voice_result.trim().replaceAll(" ", "");
                    if (result1.replaceAll(" ", "").contains(text1) || result1.contains(test2)) {
                        text = "네 안녕하세요. 저는 혀니 입니다.";
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
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
                    } else if (result1.contains(app)) {
                        Intent intent = new Intent(getContext(), UserAppsActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(battry)) {
                        Intent intent = new Intent(getContext(), BatteryActivity.class);
                        startActivity(intent);
                    } else if (result1.contains(music) || result1.contains(music2) || result1.contains(music3)) {
                        Intent intent = new Intent(getContext(), TotalMusicActivity.class);
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
