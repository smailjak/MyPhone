package com.example.sh.androidregisterandlogin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AdvertisingActivity extends AppCompatActivity {

    LinearLayout Linear_advertising;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);

        Linear_advertising = findViewById(R.id.Linear_advertising);
        backPressCloseHandler = new BackPressCloseHandler(this);
        advertisingClick();
    }

    public void advertisingClick() {
        Linear_advertising.setOnClickListener(new View.OnClickListener() {
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
