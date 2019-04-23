package com.example.sh.androidregisterandlogin.TotalManage;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.Total_Intro.AppintroActivity;


public class ManageActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TextView settings, main_activity_btn, ai_activity_btn, question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        tableLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
//      TextView 부분 == > 상단에 액티비티 이동하기 위한 TextView
        main_activity_btn = (TextView) findViewById(R.id.main_activity_btn);
        ai_activity_btn = (TextView) findViewById(R.id.ai_activity_btn);
        question = (TextView) findViewById(R.id.question);

        main_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageActivity.this, CollectActivity.class);
                startActivity(intent);
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageActivity.this, AppintroActivity.class);
                startActivity(intent);
            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//      Adding Fragments
        adapter.AddFragment(new FragmentAI(), "AI");
        adapter.AddFragment(new FragmentAI2(), "AI2");
//        adapter Setup
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
    }   // onCreate 끝나는 부분
}
