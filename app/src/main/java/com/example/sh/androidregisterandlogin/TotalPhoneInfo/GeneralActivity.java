package com.example.sh.androidregisterandlogin.TotalPhoneInfo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.example.sh.androidregisterandlogin.R;

public class GeneralActivity extends AppCompatActivity {

    public String titles[] = {"폰이름", "제조사", "기계", "Board", "하드웨어", "브랜드"};
    public String descriptions[] = {Build.MODEL, Build.MANUFACTURER, Build.DEVICE, Build.BOARD, Build.HARDWARE, Build.BRAND};
    //        titles = new String[]{"폰이름", "제조사", "기계", "Board", "하드웨어", "브랜드"};
//        descriptions = new String[]{Build.MODEL, Build.MANUFACTURER, Build.DEVICE, Build.BOARD, Build.HARDWARE, Build.BRAND};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
//        Actionbar
        ListView list = findViewById(R.id.generalList);
        GeneralAdapter generalAdapter = new GeneralAdapter(this, titles, descriptions);
//        질문 여기서 this 가 아니라 context 를 넣어서 보내면 오류가 발생한다 왜그런거지 ??
        list.setAdapter(generalAdapter);
        initActionbar();
    }

    public void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("General Info");
//            set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // when backbutton in actionbar is pressed go to previous activity
        return true;
    }
}

