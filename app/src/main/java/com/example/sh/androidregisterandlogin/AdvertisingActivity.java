package com.example.sh.androidregisterandlogin;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.sh.androidregisterandlogin.databinding.ActivityAdvertisingBinding;

public class AdvertisingActivity extends AppCompatActivity {

    private ActivityAdvertisingBinding binding;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_advertising);

        backPressCloseHandler = new BackPressCloseHandler(this);
        advertisingClick();
    }

    public void advertisingClick() {
        binding.linearAdvertising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdvertisingActivity.this, AndroidActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
