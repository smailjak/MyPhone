package com.example.sh.androidregisterandlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sh.androidregisterandlogin.databinding.ActivityPhonePriceWebViewBinding;

public class PhonePriceWebView extends AppCompatActivity {
    private ActivityPhonePriceWebViewBinding activityPhonePriceWebViewBinding;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPhonePriceWebViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_phone_price_web_view);

        webSettings = activityPhonePriceWebViewBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        activityPhonePriceWebViewBinding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }
        });
        activityPhonePriceWebViewBinding.webView.loadUrl("https://www.uplussave.com/dev/lawList.mhp");

    }
}
