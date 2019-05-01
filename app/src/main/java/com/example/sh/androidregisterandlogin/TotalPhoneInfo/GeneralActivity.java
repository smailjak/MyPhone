package com.example.sh.androidregisterandlogin.TotalPhoneInfo;

import androidx.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityGeneralBinding;

import java.util.ArrayList;

public class GeneralActivity extends AppCompatActivity {

    private ActivityGeneralBinding binding;
    GeneralAdapter generalAdapter;
    ArrayList<GeneralItem> generalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_general);
        initRv(binding.rcvGeneral);
        initActionbar();
    }

    private void initRv(RecyclerView rv) {
        generalAdapter = new GeneralAdapter(getGeneralItem(), this);
        rv.setAdapter(generalAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
    }

    private ArrayList<GeneralItem> getGeneralItem() {

        generalList = new ArrayList<>();
        String titles[] = {"폰이름", "제조사", "기계", "Board", "하드웨어", "브랜드"};
        String descriptions[] = {Build.MODEL, Build.MANUFACTURER, Build.DEVICE, Build.BOARD, Build.HARDWARE, Build.BRAND};

        for (int i = 0; i < titles.length; i++) {
            generalList.add(new GeneralItem(titles[i], descriptions[i]));
        }
        return generalList;
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

