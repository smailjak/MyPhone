package com.example.sh.androidregisterandlogin.TotalManage;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.R;
import com.example.sh.androidregisterandlogin.databinding.ActivityManageBinding;


public class ManageActivity extends AppCompatActivity {

    private ActivityManageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage);


        binding.mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageActivity.this, CollectActivity.class);
                startActivity(intent);
            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//      Adding Fragments
        adapter.AddFragment(new FragmentOne(), "one");
        adapter.AddFragment(new FragmentTwo(), "두번째");
//        adapter Setup
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }   // onCreate 끝나는 부분
}
