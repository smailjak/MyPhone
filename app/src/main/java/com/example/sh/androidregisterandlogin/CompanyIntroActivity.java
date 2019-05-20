package com.example.sh.androidregisterandlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sh.androidregisterandlogin.databinding.ActivityCompanyIntroBinding;

public class CompanyIntroActivity extends AppCompatActivity {
    private ActivityCompanyIntroBinding activityCompanyIntroBinding;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCompanyIntroBinding = DataBindingUtil.setContentView(this, R.layout.activity_company_intro);

        webSettings = activityCompanyIntroBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        activityCompanyIntroBinding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }
        });
        activityCompanyIntroBinding.webView.loadUrl("https://map.naver.com/local/siteview.nhn?code=81266309&_ts=1558269967403");
    }
}
