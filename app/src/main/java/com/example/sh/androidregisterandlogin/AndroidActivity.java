package com.example.sh.androidregisterandlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sh.androidregisterandlogin.databinding.ActivityAndroidBinding;

public class AndroidActivity extends AppCompatActivity {
    private ActivityAndroidBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_android);


        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        binding.webview.loadUrl("https://developer.android.com/?hl=ko");
    }
}
