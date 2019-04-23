package com.example.sh.androidregisterandlogin.Total_Intro;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sh.androidregisterandlogin.R;

public class AppintroActivity extends AppCompatActivity {

    ViewPager viewPager;

    TextView intro_pic, intro_address, intro_message, intro_music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appintro);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.isClickable();
        intro_pic = findViewById(R.id.intro_pic);
        intro_address = findViewById(R.id.intro_address);
        intro_message = findViewById(R.id.intro_message);
        intro_music = findViewById(R.id.intro_music);

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppintroActivity.this, Detail_intro.class);
                startActivity(intent);
            }
        });
    }
}
