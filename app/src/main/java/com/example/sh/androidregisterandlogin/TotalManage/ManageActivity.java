package com.example.sh.androidregisterandlogin.TotalManage;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.sh.androidregisterandlogin.TotalHome.HomeActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.TotalManage.FragOne.FragmentOne;
import com.example.sh.androidregisterandlogin.TotalManage.FragTwo.FragmentTwo;
import com.example.sh.androidregisterandlogin.databinding.ActivityManageBinding;
import com.google.android.material.tabs.TabLayout;

public class ManageActivity extends AppCompatActivity {

    private ActivityManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage);

        binding.tvMain.setOnClickListener(view -> {
            Intent intent = new Intent(ManageActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//      Adding Fragments
        adapter.AddFragment(new FragmentOne(), "PhoneInfo");
        adapter.AddFragment(new FragmentTwo(), "Artificial");
//        adapter Setup
        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);
        binding.vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tl));
        binding.tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }   // onCreate 끝나는 부분
}
