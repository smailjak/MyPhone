package com.example.sh.androidregisterandlogin;

import android.app.ActivityManager;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MemoryActivity extends AppCompatActivity {

    TextView mTvTotalRam, mTvFreeRam, mTvUsedRam;
    TextView mTvPercRam;
    ProgressBar mPBRam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        mTvFreeRam = findViewById(R.id.freeRam);
        mTvUsedRam = findViewById(R.id.usedRam);
        mTvTotalRam = findViewById(R.id.totalRam);
        mPBRam = findViewById(R.id.pbRam);
        mTvPercRam = findViewById(R.id.tv_perc_ram);

        initActionBar();

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        double totalMem = memoryInfo.totalMem / (1024 * 1024);   //total ram
        double freeMem = memoryInfo.availMem / (1024 * 1024);   //free ram
        double usedMem = totalMem - freeMem; // used ram
        double freeMemPerc = freeMem / totalMem * 100;
        double usedMemPerc = usedMem / totalMem * 100;

        NumberFormat numFormFreePerc = NumberFormat.getNumberInstance();
        numFormFreePerc.setMinimumFractionDigits(1);
        numFormFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numFormFreePerc.format(freeMemPerc);
//        Used RAM percentage decimal point conversion
        NumberFormat numFormUsedPerc = NumberFormat.getNumberInstance();
        numFormUsedPerc.setMinimumFractionDigits(1);
        numFormUsedPerc.setMaximumFractionDigits(1);
        String mUsedMemPerc = numFormFreePerc.format(usedMemPerc);


//        setting RAM info
        mTvFreeRam.setText(" " + freeMem + "MB" + "(" + mFreeMemPerc + "%)");
        mTvUsedRam.setText(" " + usedMem + "MB" + "(" + mUsedMemPerc + "%)");
        mTvTotalRam.setText(" " + totalMem + "MB");

//        getting Java Heap
        Runtime rt = Runtime.getRuntime();


        mTvPercRam.setText(mUsedMemPerc + "% Used");
        mPBRam.setProgress((int) usedMemPerc);

    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Memory");
//            set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go to previous activity on back button click
        return true;
    }   //lets design Our UI using CardView , LinearLayout , TableLayout , TextView , ProgressBar
}
