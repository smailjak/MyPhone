package com.example.sh.androidregisterandlogin.Total_Intro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.sh.androidregisterandlogin.R;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import java.util.ArrayList;
import java.util.List;

public class Detail_intro extends AppCompatActivity {

    HorizontalInfiniteCycleViewPager viewPager;
    List<Movie> movieList = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_intro);
        viewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.view_pager);
        adapter = new MyAdapter(this, movieList);
        viewPager.setAdapter(adapter);
        initData();
        Toast.makeText(Detail_intro.this, "상세 페이지입니다.", Toast.LENGTH_LONG).show();
    }

    private void initData() {
        movieList.add(new Movie("사진 화면 입니다.", getString(R.string.long_text), R.drawable.intro1));
        movieList.add(new Movie("연락처 화면 입니다.", getString(R.string.long_text), R.drawable.intro2));
        movieList.add(new Movie("메시지 화면 입니다.", getString(R.string.long_text), R.drawable.intro3));
        movieList.add(new Movie("음악 화면 입니다.", getString(R.string.long_text), R.drawable.intro4));
        movieList.add(new Movie(" 음성 인식 화면 입니다.", getString(R.string.long_text), R.drawable.intro5));
    }
}
