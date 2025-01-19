package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;


import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.handledarkmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.BaseActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.MultiUserModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.FullDetailModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler1;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import videodownload.com.newmusically.R;

public class LoginWebviewActivity extends BaseActivity {
    LoginWebviewActivity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;
    private String cookies;
    MultiUserModel multiUserModel;
    ArrayList<MultiUserModel> multiUserModelArrayList;


    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }


        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        public void onPageFinished(WebView webView1, String str) {
            super.onPageFinished(webView1, str);
            LoginWebviewActivity.this.cookies = CookieManager.getInstance().getCookie(str);
            try {
                String cookie = LoginWebviewActivity.this.getCookie(str, "sessionid");
                String cookie2 = LoginWebviewActivity.this.getCookie(str, "csrftoken");
                str = LoginWebviewActivity.this.getCookie(str, "ds_user_id");
                if (cookie != null && cookie2 != null && str != null) {
                    PrefManager.getInstance(LoginWebviewActivity.this.activity).putString(PrefManager.COOKIES, LoginWebviewActivity.this.cookies);
                    PrefManager.getInstance(LoginWebviewActivity.this.activity).putString(PrefManager.CSRF, cookie2);
                    PrefManager.getInstance(LoginWebviewActivity.this.activity).putString(PrefManager.SESSIONID, cookie);
                    PrefManager.getInstance(LoginWebviewActivity.this.activity).putString(PrefManager.USERID, str);
                    PrefManager.getInstance(LoginWebviewActivity.this.activity).putBoolean(PrefManager.ISINSTALOGIN, Boolean.valueOf(true));
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(cookie);
                    stringBuilder.append("--");
                    stringBuilder.append(str);
                    Log.e("SessionIdLogin: ", stringBuilder.toString());
                    webView.destroy();
                    LoginWebviewActivity.this.multiUserModel = new MultiUserModel();
                    LoginWebviewActivity.this.multiUserModel.setCookies(LoginWebviewActivity.this.cookies);
                    LoginWebviewActivity.this.multiUserModel.setCsrf(cookie2);
                    LoginWebviewActivity.this.multiUserModel.setSession_id(cookie);
                    LoginWebviewActivity.this.multiUserModel.setUser_id(str);
                    LoginWebviewActivity.this.callStoriesDetailApi();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
        }

        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handledarkmode(this);
        setContentView(R.layout.activity_webview_login);
        bindMe();
        this.activity = this;

        LoadPage();
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                LoginWebviewActivity.this.LoadPage();
            }
        });
    }

    private void bindMe() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView = findViewById(R.id.webView);
    }

    public void LoadPage() {
        this.multiUserModelArrayList = new ArrayList<MultiUserModel>();
        if (!(PrefManager.getInstance(this.activity).getString(PrefManager.MULTIUSERLIST) == null || PrefManager.getInstance(this.activity).getString(PrefManager.MULTIUSERLIST).equals(""))) {
            this.multiUserModelArrayList = new Gson().fromJson(PrefManager.getInstance(this.activity).getString(PrefManager.MULTIUSERLIST), new TypeToken<ArrayList<MultiUserModel>>() {
            }.getType());
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new MyBrowser());
        CookieSyncManager.createInstance(this.activity);
        CookieManager.getInstance().removeAllCookie();
        webView.loadUrl(Config.LOGINURL);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
    }

    public String getCookie(String str, String str2) {
        str = CookieManager.getInstance().getCookie(str);
        if (!(str == null || str.isEmpty())) {
            for (String str3 : str.split(";")) {
                if (str3.contains(str2)) {
                    return str3.split("=")[1];
                }
            }
        }
        return null;
    }

    private void callStoriesDetailApi() {
        try {
            if (!new Utils(this.activity).isNetworkAvailable()) {
                // Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
                Utils.setToast(this.activity, "no_internet_connection");
            } else {
                Utils.showProgress(this.activity);
                ServiceHandler.get(Config.USERFEEDSTORY(PrefManager.getInstance(this.activity).getString(PrefManager.USERID), ""), null, new TextHttpResponseHandler1() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Utils.hideProgress();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            Utils.hideProgress();
                            FullDetailModel fullDetailModel = new Gson().fromJson(responseString, FullDetailModel.class);
                            PrefManager instance = PrefManager.getInstance(LoginWebviewActivity.this.activity);
                            instance.putString(PrefManager.FULLNAME, fullDetailModel.getUser_detail().getUser().getFull_name());
                            instance.putString(PrefManager.USERNAME, fullDetailModel.getUser_detail().getUser().getUsername());
                            instance.putString(PrefManager.PROFILEIMAGEURL, fullDetailModel.getUser_detail().getUser().getHdProfileModel().getUrl());
                            LoginWebviewActivity.this.multiUserModel.setFullname(fullDetailModel.getUser_detail().getUser().getFull_name());
                            LoginWebviewActivity.this.multiUserModel.setUsername(fullDetailModel.getUser_detail().getUser().getUsername());
                            LoginWebviewActivity.this.multiUserModel.setProfileImageUrl(fullDetailModel.getUser_detail().getUser().getHdProfileModel().getUrl());
                            for (int i = 0; i < LoginWebviewActivity.this.multiUserModelArrayList.size(); i++) {
                                if (LoginWebviewActivity.this.multiUserModel.getUser_id().equals(((MultiUserModel) LoginWebviewActivity.this.multiUserModelArrayList.get(i)).getUser_id())) {
                                    LoginWebviewActivity.this.multiUserModelArrayList.remove(i);
                                }
                            }
                            LoginWebviewActivity.this.multiUserModelArrayList.add(LoginWebviewActivity.this.multiUserModel);
                            PrefManager.getInstance(LoginWebviewActivity.this.activity).putString(PrefManager.MULTIUSERLIST, ((JsonArray) new Gson().toJsonTree(LoginWebviewActivity.this.multiUserModelArrayList, new TypeToken<ArrayList<MultiUserModel>>() {
                            }.getType())).toString());
                            Utils.hideProgress();
                            if (getIntent().getBooleanExtra("isSearch", false)) {
//                                Intent intent = new Intent(LoginWebviewActivity.this.activity, InstaDpSaverActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                LoginWebviewActivity.this.webView.destroy();
//                                LoginWebviewActivity.this.startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoginWebviewActivity.this.activity, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                LoginWebviewActivity.this.webView.destroy();
                                LoginWebviewActivity.this.startActivity(intent);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, true, Config.getCookie(LoginWebviewActivity.this), Config.USERAGENT);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
