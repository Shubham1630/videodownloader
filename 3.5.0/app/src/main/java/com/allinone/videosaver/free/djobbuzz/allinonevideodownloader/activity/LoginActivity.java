package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.MainActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import videodownload.com.newmusically.R;


public class LoginActivity extends AppCompatActivity {

    LoginActivity activity;
    private String cookies;
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handledarkmode();
        setContentView(R.layout.activity_login);
        activity = this;
        binddata();
        LoadPage();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadPage();
            }
        });

    }

    private void binddata() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView = findViewById(R.id.webView);
    }

    public void LoadPage() {
         webView.getSettings().setJavaScriptEnabled(true);
         webView.clearCache(true);
         webView.setWebViewClient(new MyBrowser());
        CookieSyncManager.createInstance(activity);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.loadUrl("https://www.instagram.com/accounts/login/");
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                  swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
    }



    public String getCookie(String siteName, String CookieName) {
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        if (cookies != null && !cookies.isEmpty()) {
            String[] temp = cookies.split(";");
            for (String ar1 : temp) {
                if (ar1.contains(CookieName)) {
                    String[] temp1 = ar1.split("=");
                    CookieValue = temp1[1];
                    break;
                }
            }
        }
        return CookieValue;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //getCookieString(url);
            return true;
        }

        @Override
        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        @Override
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            //getCookieString(str);

            cookies = CookieManager.getInstance().getCookie(str);

            try {
                String session_id = getCookie(str, "sessionid");
                String csrftoken = getCookie(str, "csrftoken");
                String userid = getCookie(str, "ds_user_id");
                if (session_id != null && csrftoken != null && userid != null) {
                    PrefManager.getInstance(activity).putString(PrefManager.COOKIES, cookies);
                    PrefManager.getInstance(activity).putString(PrefManager.CSRF, csrftoken);
                    PrefManager.getInstance(activity).putString(PrefManager.SESSIONID, session_id);
                    PrefManager.getInstance(activity).putString(PrefManager.USERID, userid);
                    PrefManager.getInstance(activity).putBoolean(PrefManager.ISINSTALOGIN, true);

                     webView.destroy();
                    Intent intent = new Intent();
                    intent.putExtra("result", "result");
                    setResult(RESULT_OK, intent);
                    startActivity(new Intent(activity, MainActivity.class));
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
    }

    private void handledarkmode() {
        String[] darkModeValues = getResources().getStringArray(R.array.dark_mode_values);
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(getString(R.string.dark_mode),  getString(R.string.dark_mode_def_value));
        if (string != null) {
            if (string.equalsIgnoreCase(darkModeValues[0])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            } else if (string.equalsIgnoreCase(darkModeValues[1])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (string.equalsIgnoreCase(darkModeValues[2])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (string.equalsIgnoreCase(darkModeValues[3])) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
            }
        }

    }
}