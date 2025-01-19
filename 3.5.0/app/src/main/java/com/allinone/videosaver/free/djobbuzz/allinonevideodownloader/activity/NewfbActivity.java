package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.app.App;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database.DBOperations;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.VideoHistoryModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.DialogFacebookDownload;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.parseUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebParentLayout;
import com.just.agentweb.WebViewClient;


import org.jsoup.nodes.Document;

import java.io.File;

import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import videodownload.com.newmusically.BuildConfig;
import videodownload.com.newmusically.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment.forfacebook;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;

public class NewfbActivity extends BaseActivity {

    public static String URL_FACEBOOK = "https://m.facebook.com";
    public final String TAG = "FacebookFragment";
    private File checkfileexistsornnot = null;
    private File dirname = null;
    private String downloadfilename = "";
    public LinearLayout ll_back;
    public LinearLayout ll_forward;
    public LinearLayout ll_home;
    public LinearLayout ll_refresh;
    public AgentWeb mAgentWeb;
    public String mszClickedDivInnerHTML;
    public String mszClickedImageUrl;
    public String mszClickedUrl;
    public String mszHtmlSource;
    public Document mJsoup;
    public WebView web;
    private Toolbar mToolbar;
    private TextView txt_title;
    private ImageView fb_help;
    Dialog howtouse;
    private ProgressBar pb_progress;
    int hintIdx = 0;
    public final String[] strHint = {App.getInstance().getResources().getString(R.string.fb_hint_1), App.getInstance().getResources().getString(R.string.fb_hint_2)};
    public static final int[] bitRes = {R.drawable.facebook_step1, R.drawable.facebook_step2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfb);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_title = (TextView) findViewById(R.id.txt_title);
        fb_help = findViewById(R.id.fb_help);
        pb_progress = findViewById(R.id.pb_progress);
        fb_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHowToUsedDialog();
            }
        });
        mToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.facebook_video_downloader));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //What to do on back clicked
            }
        });
        txt_title.setText(getResources().getString(R.string.facebook_video_downloader));
        RelativeLayout adView = findViewById(R.id.adView);


        ll_back = findViewById(R.id.ll_back);
        ll_forward = findViewById(R.id.ll_forward);
        ll_home = findViewById(R.id.ll_home);
        ll_refresh = findViewById(R.id.ll_refresh);
        initAgentWeb();
        this.ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.goBackOrForward(-1);

            }
        });
        this.ll_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.goForward();

            }
        });
        this.ll_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.reload();

            }
        });
        this.ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl(URL_FACEBOOK);

            }
        });


    }


    public WebChromeClient mWebChromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView webView, int i) {
            String stringBuilder = i +
                    "";
            Log.i("FacebookFragment", stringBuilder);
            if (i >= 99) {

                pb_progress.setVisibility(View.GONE);
            } else {
                pb_progress.setVisibility(View.VISIBLE);
            }
            if (i > 20) {
                ((WebParentLayout) mAgentWeb.getWebCreator().getWebParentLayout()).provide().onShowMainFrame();
            }
            pb_progress.setProgress(i);
        }
    };
    public WebViewClient mWebViewClient = new WebViewClient() {
        public void onLoadResource(WebView webView, String str) {
            String str2 = "}";
            String stringBuffer = "javascript:(function prepareVideo() { " +
                    "var el = document.querySelectorAll('div[data-sigil]');" +
                    "for(var i=0;i<el.length; i++)" +
                    "{" +
                    "var sigil = el[i].dataset.sigil;" +
                    "if(sigil.indexOf('inlineVideo') > -1){" +
                    "delete el[i].dataset.sigil;" +
                    "console.log(i);" +
                    "var szData = el[i].dataset.store;" +
                    "console.log(szData);" +
                    "var jsonData = JSON.parse(szData);" +
                    "el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');" +
                    str2 +
                    str2 +
                    "var arrayDivItems = document.querySelectorAll(\"div[data-sigil='photo-stage marea']\");" +
                    "for(var j=0;j<arrayDivItems.length;j++){" +
                    "imagelistener.openImage(arrayDivItems[j].innerHTML);" +
                    str2 +
                    "})()";
            webView.loadUrl(stringBuffer);
            webView.loadUrl("javascript:(window.onload=prepareVideo();)");
            Log.i("Face", str);
        }

        public void onPageFinished(WebView webView, String str) {
            str = Utility.getSafeString(CookieManager.getInstance().getCookie(URL_FACEBOOK));
            Log.i("Cookies", str);
            Utility.COOKIE_FACEBOOK = str;
            webView.loadUrl("javascript:(function() { var el = document.querySelectorAll('div[data-sigil]');for(var i=0;i<el.length; i++){var sigil = el[i].dataset.sigil;if(sigil.indexOf('inlineVideo') > -1){delete el[i].dataset.sigil;var jsonData = JSON.parse(el[i].dataset.store);el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\");');}}var arrayDivItems = document.querySelectorAll(\"div[data-sigil='photo-stage marea']\");for(var j=0;j<arrayDivItems.length;j++){arrayDivItems[j].onclick = function () {imagelistener.openImage(this.innerHTML);}}})()");
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            String safeString = Utility.getSafeString(CookieManager.getInstance().getCookie(URL_FACEBOOK));
            Log.i("Cookies", safeString);
            Utility.COOKIE_FACEBOOK = safeString;
        }

        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
    };


    private void initAgentWeb() {
        AgentWeb go = AgentWeb.with(NewfbActivity.this).setAgentWebParent((LinearLayout) findViewById(R.id.ll_web_container), -1, new LinearLayout.LayoutParams(-1, -1)).useDefaultIndicator(0, 0).setSecurityType(AgentWeb.SecurityType.DEFAULT_CHECK).setMainFrameErrorView(R.layout.agentweb_error_page, -1).setWebChromeClient(mWebChromeClient).setWebViewClient(mWebViewClient).createAgentWeb().ready().go(URL_FACEBOOK);
        mAgentWeb = go;
        web = go.getWebCreator().getWebView();
        registerForContextMenu(this.mAgentWeb.getWebCreator().getWebView());
        this.web.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void processVideo(final String str, final String str2) {

                runOnUiThread(new Runnable() {
                    public void run() {
                        DialogFacebookDownload.showDialog(NewfbActivity.this, str, str2, null);
                    }
                });
            }
        }, "FBDownloader");
        this.web.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void showSource(String str) {
                Log.e(TAG, "showSource: called" + str);
                mszHtmlSource = str;
                mJsoup = parseUtils.parsefburls(str);
            }
        }, "FBDownloadSource");
        this.web.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void openImage(final String str) {
                Log.e(TAG, "openImage called: " + str);
                runOnUiThread(new Runnable() {
                    public void run() {
                        mszClickedDivInnerHTML = str;
                    }
                });
            }
        }, "imagelistener");
    }


    public void navDownload(VideoHistoryModel videoHistoryModel) {
        navDownload(videoHistoryModel, false);
    }



    public void navDownload(VideoHistoryModel videoHistoryModel, boolean z) {
        if (videoHistoryModel != null) {

            if (BuildConfig.DEBUG) {
                Log.e(TAG, "getData: called" + videoHistoryModel.video_url);
            }
            downloadfilename = forfacebook + "_" + System.currentTimeMillis() + "_" + ".mp4";

            final Dialog dialog = new Dialog(NewfbActivity.this);
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.appdialog);
            dialog.setCancelable(false);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = MATCH_PARENT;
            layoutParams.height = WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
            final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressDialog);
            final TextView textView = (TextView) dialog.findViewById(R.id.txtcountstart);
            ((TextView) dialog.findViewById(R.id.txttitle)).setText(NewfbActivity.this.getResources().getString(R.string.downloading));
            progressBar.setProgress(0);
            textView.setText("0%");
            //   dialog.show();
            String videoPath = videoHistoryModel.video_url;

            Log.e("videoUrl", videoPath);
            String path = Utility.STORAGEDIR ;//+ forfacebook;

            checkfileexistsornnot = new File(path);
            if (!checkfileexistsornnot.exists()) {
                checkfileexistsornnot.mkdir();
            }
            path = checkfileexistsornnot.getAbsolutePath() ;//+ "/.temp/";
            dirname = new File(path);
            if (!dirname.exists()) {
                dirname.mkdir();
            }
            String stringBuilder2 = dirname + "/" + downloadfilename;
            File file = new File(stringBuilder2);
            if (videoPath.contains("https")) {
                // videoPath = videoPath.replace("https", HttpHost.DEFAULT_SCHEME_NAME);
            }
            String finalVideoPath = videoPath;
            CommonModel commonModel = new CommonModel();
            commonModel.setSaved_video_url(videoPath);
            commonModel.setSaved_video_date(String.valueOf(System.currentTimeMillis()));
            commonModel.setSaved_video_path(dirname.getAbsolutePath());
            commonModel.setSaved_video_name(downloadfilename);
            Toast.makeText(this, getResources().getString(R.string.downloading), Toast.LENGTH_SHORT).show();
            DBOperations.InsertDownload(this, commonModel, new DownloadListener() {
                @Override
                public void onComplete(int totalBytes) {
                    Toast.makeText(NewfbActivity.this, getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();
                    String stringBuilder = checkfileexistsornnot + "/" + downloadfilename;
                    File file2 = new File(stringBuilder);
                    addVideo(file2);

                }

                @Override
                public void onPause(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

                }

                @Override
                public void onPending(int percent, int totalBytes, int downloadedBytes) {

                }

                @Override
                public void onFail(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

                }

                @Override
                public void onCancel(int totalBytes, int downloadedBytes) {

                }

                @Override
                public void onRunning(int percent, int totalBytes, int downloadedBytes, float downloadSpeed) {

                }
            });
        }


    }

    public void addVideo(File file) {
        /*ContentValues contentValues = new ContentValues(3);
        contentValues.put("title", file.getName());
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("_data", file.getAbsolutePath());
        NewfbActivity.this.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);*/

    }


    private void loadAdbmobbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        final AdView mAdView = new AdView(NewfbActivity.this);
        mAdView.setAdSize(SMART_BANNER);
        mAdView.setAdUnitId(NewfbActivity.this.getString(R.string.banner_ad_units_id1));
        mAdView.loadAd(new AdRequest.Builder().build());

        mAdView.setAdListener(new AdListener() {
            public void onAdClosed() {

                Log.e("Adbmobbanner closed", "onAdClosed");
            }

            public void onAdFailedToLoad(LoadAdError i) {
                topBannerView.setVisibility(View.GONE);

                Log.e("Adbmobbanner failed", String.valueOf(i));
            }

            public void onAdLeftApplication() {
                Log.e("left", "onAdLeftApplication");
            }

            public void onAdLoaded() {
                topBannerView.removeAllViews();
                topBannerView.setVisibility(View.VISIBLE);
                topBannerView.addView(mAdView);
                Log.e("onAdLoaded", "onAdLoaded");
            }

            public void onAdOpened() {
                Log.e("opened", "onAdOpened");
            }
        });

    }


    private void loadfbbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(NewfbActivity.this, NewfbActivity.this.getString(R.string.fbbanner), AdSize.BANNER_HEIGHT_50);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                topBannerView.setVisibility(View.GONE);
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {

                topBannerView.removeAllViews();
                topBannerView.setVisibility(View.VISIBLE);
                topBannerView.addView(adView);
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());

    }


    private void showHowToUsedDialog() {

        howtouse = new Dialog(NewfbActivity.this);
        final View decorView = howtouse
                .getWindow()
                .getDecorView();

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
        scaleDown.setDuration(500);
        scaleDown.start();

        howtouse.setContentView(R.layout.dialog_howtoused);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(howtouse.getWindow().getAttributes());
        layoutParams.width = MATCH_PARENT;
        layoutParams.height = WRAP_CONTENT;
        howtouse.getWindow().setAttributes(layoutParams);

        final ImageView id_iv_introimg = howtouse.findViewById(R.id.id_iv_introimg);
        final ImageView id_iv_introprev = howtouse.findViewById(R.id.id_iv_introprev);
        final ImageView id_iv_intronext = howtouse.findViewById(R.id.id_iv_intronext);
        final ImageView id_iv_introfinish = howtouse.findViewById(R.id.id_iv_introfinish);
        final TextView id_txt_introtext = howtouse.findViewById(R.id.id_txt_introtext);

        hintIdx = 0;
        Glide.with(this).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);
        //   id_iv_introimg.setImageResource(bitRes[hintIdx]);
        id_txt_introtext.setText(strHint[hintIdx]);
        id_iv_introprev.setVisibility(View.INVISIBLE);
        howtouse.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
        howtouse.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));

        id_iv_intronext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintIdx++;
                if (hintIdx >= bitRes.length) {
                    hintIdx--;
                    return;
                }
                Glide.with(NewfbActivity.this).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);
                //id_iv_introimg.setImageResource(bitRes[hintIdx]);
                id_txt_introtext.setText(strHint[hintIdx]);
                if (hintIdx >= bitRes.length - 1) {
                    id_iv_intronext.setVisibility(View.INVISIBLE);
                    id_iv_introfinish.setVisibility(View.VISIBLE);
                } else {
                    id_iv_intronext.setVisibility(View.VISIBLE);
                    id_iv_introfinish.setVisibility(View.INVISIBLE);
                }
                if (hintIdx > 0)
                    id_iv_introprev.setVisibility(View.VISIBLE);
                else
                    id_iv_introprev.setVisibility(View.INVISIBLE);

            }
        });

        id_iv_introfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                howtouse.dismiss();

            }
        });

        id_iv_introprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_iv_introfinish.setVisibility(View.GONE);
                hintIdx--;
                if (hintIdx < 0) {
                    hintIdx = 0;
                    return;
                }
                Glide.with(NewfbActivity.this).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);
                //id_iv_introimg.setImageResource(bitRes[hintIdx]);
                id_txt_introtext.setText(strHint[hintIdx]);
                if (hintIdx >= bitRes.length - 1)
                    id_iv_intronext.setVisibility(View.GONE);
                else
                    id_iv_intronext.setVisibility(View.VISIBLE);
                if (hintIdx > 0)
                    id_iv_introprev.setVisibility(View.VISIBLE);
                else
                    id_iv_introprev.setVisibility(View.GONE);
            }
        });

        howtouse.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (howtouse != null)
            howtouse.dismiss();
    }
}