package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.app.App;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database.DBOperations;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;


import java.io.File;

import cz.msebera.android.httpclient.HttpHost;
import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import videodownload.com.newmusically.BuildConfig;
import videodownload.com.newmusically.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.DashboardFragment.forfacebook;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;

public class FacebookPrivateVideoActivity extends BaseActivity {
    private static final String TAG = "FacebookPrivateVideoAct";
    WebView mWebview;

    private ProgressBar mprogress;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private File checkfileexistsornnot = null;
    private File dirname = null;
    private String downloadfilename = "";
    private Toolbar mToolbar;
    private TextView txt_title;
    private ImageView fb_help;
    int hintIdx = 0;

    public final String[] strHint = {App.getInstance().getResources().getString(R.string.fb_hint_1), App.getInstance().getResources().getString(R.string.fb_hint_2)};
    public static final int[] bitRes = {R.drawable.facebook_step1, R.drawable.facebook_step2};

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_private_video);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_title = (TextView) findViewById(R.id.txt_title);
        fb_help = findViewById(R.id.fb_help);
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

        this.mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        this.mprogress = (ProgressBar) findViewById(R.id.progressBar);
        this.mprogress.setProgress(0);
        this.mprogress.setMax(100);
        this.mWebview = (WebView) findViewById(R.id.webView);
        this.mWebview.setVisibility(View.INVISIBLE);
        this.mWebview.getSettings().setSupportZoom(true);
        this.mWebview.getSettings().setBuiltInZoomControls(true);
        this.mWebview.addJavascriptInterface(this, "mJava");
        this.mWebview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            this.mWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        this.mWebview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (mprogress.getProgress() == 100) {
                            mprogress.setVisibility(View.INVISIBLE);
                            mWebview.setVisibility(View.VISIBLE);
                            mySwipeRefreshLayout.setRefreshing(false);
                        }
                        mWebview.loadUrl("javascript:var e=0; window.onscroll=function() {  var ij=document.querySelectorAll(\"video\");   for(var f=0;f<ij.length;f++)   {    if((ij[f].parentNode.querySelectorAll(\"img\")).length==0)    {     var nextimageWidth=ij[f].nextSibling.style.width;     var nextImageHeight=ij[f].nextSibling.style.height;     var Nxtimgwd=parseInt(nextimageWidth, 10);     var Nxtimghght=parseInt(nextImageHeight, 10);      var DOM_img = document.createElement(\"img\");      DOM_img.height=\"68\";      DOM_img.width=\"68\";      DOM_img.style.top=(Nxtimghght/2-20)+\"px\";      DOM_img.style.left=(Nxtimgwd/2-20)+\"px\";      DOM_img.style.position=\"absolute\";      DOM_img.src = \"https://image.ibb.co/kobwsk/one.png\";       ij[f].parentNode.appendChild(DOM_img);    }      ij[f].remove();   }     e++; };var a = document.querySelectorAll(\"a[href *= 'video_redirect']\"); for (var i = 0; i < a.length; i++) {     var mainUrl = a[i].getAttribute(\"href\");   a[i].removeAttribute(\"href\");  mainUrl=mainUrl.split(\"/video_redirect/?src=\")[1];  mainUrl=mainUrl.split(\"&source\")[0];     var threeparent = a[i].parentNode.parentNode.parentNode;     threeparent.setAttribute(\"src\", mainUrl);     threeparent.onclick = function() {         var mainUrl1 = this.getAttribute(\"src\");          mJava.getData(mainUrl1,\"1\");     }; }var k = document.querySelectorAll(\"div[data-store]\"); for (var j = 0; j < k.length; j++) {     var h = k[j].getAttribute(\"data-store\");     var g = JSON.parse(h); var jp=k[j].getAttribute(\"data-sigil\");     if (g.type === \"video\") { if(jp==\"inlineVideo\"){   k[j].removeAttribute(\"data-sigil\");}         var url = g.src;         k[j].setAttribute(\"src\", g.src);         k[j].onclick = function() {             var mainUrl = this.getAttribute(\"src\");                mJava.getData(mainUrl,\"2\");         };     }  }");
                    }
                }, 3000);
            }

            public void onLoadResource(WebView webView, String str) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Log.e(TAG, "run: -------"+str );

                        mWebview.loadUrl("javascript:var e=document.querySelectorAll(\"span\"); if(e[0]!=undefined){var fbforandroid=e[0].innerText;if(fbforandroid.indexOf(\"Facebook\")!=-1){ var h =e[0].parentNode.parentNode.parentNode.style.display=\"none\";} }var installfb=document.querySelectorAll(\"a\"); for (var hardwares = 0; hardwares < installfb.length; hardwares++)  {  if(installfb[hardwares].text.indexOf(\"Install\")!=-1)  {   var soft=installfb[hardwares].parentNode.style.display=\"none\";   } } ");
                        mWebview.loadUrl("javascript:var e=0; window.onscroll=function() {  var ij=document.querySelectorAll(\"video\");   for(var f=0;f<ij.length;f++)   {    if((ij[f].parentNode.querySelectorAll(\"img\")).length==0)    {     var nextimageWidth=ij[f].nextSibling.style.width;     var nextImageHeight=ij[f].nextSibling.style.height;     var Nxtimgwd=parseInt(nextimageWidth, 10);     var Nxtimghght=parseInt(nextImageHeight, 10);      var DOM_img = document.createElement(\"img\");      DOM_img.height=\"68\";      DOM_img.width=\"68\";      DOM_img.style.top=(Nxtimghght/2-20)+\"px\";      DOM_img.style.left=(Nxtimgwd/2-20)+\"px\";      DOM_img.style.position=\"absolute\";      DOM_img.src = \"https://image.ibb.co/kobwsk/one.png\";       ij[f].parentNode.appendChild(DOM_img);    }      ij[f].remove();   }     e++; };var a = document.querySelectorAll(\"a[href *= 'video_redirect']\"); for (var i = 0; i < a.length; i++) {     var mainUrl = a[i].getAttribute(\"href\");   a[i].removeAttribute(\"href\");  mainUrl=mainUrl.split(\"/video_redirect/?src=\")[1];  mainUrl=mainUrl.split(\"&source\")[0];     var threeparent = a[i].parentNode.parentNode.parentNode;     threeparent.setAttribute(\"src\", mainUrl);     threeparent.onclick = function() {         var mainUrl1 = this.getAttribute(\"src\");          mJava.getData(mainUrl1,\"3\");     }; }var k = document.querySelectorAll(\"div[data-store]\"); for (var j = 0; j < k.length; j++) {     var h = k[j].getAttribute(\"data-store\");     var g = JSON.parse(h);var jp=k[j].getAttribute(\"data-sigil\");     if (g.type === \"video\") { if(jp==\"inlineVideo\"){   k[j].removeAttribute(\"data-sigil\");}         var url = g.src;         k[j].setAttribute(\"src\", g.src);         k[j].onclick = function() {             var mainUrl = this.getAttribute(\"src\");                mJava.getData(mainUrl,\"4\"+document.getElementsByTagName('html')[0].innerHTML);         };     }  }");
                    }
                }, 3000);
            }
        });
        this.mWebview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (mprogress.getProgress() < 100) {
                    String url = mWebview.getUrl();
                    if (url != null && url.contains("/stories.php?aftercursorr")) {
                        mWebview.setVisibility(View.INVISIBLE);
                        mprogress.setVisibility(View.VISIBLE);
                        mWebview.scrollTo(0, 0);
                    }
                }
                mprogress.setProgress(i);
            }
        });

        this.mWebview.loadUrl("https://m.facebook.com/");

        this.mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                mWebview.reload();
            }
        });
        this.mWebview.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 4 && keyEvent.getAction() == 1) {
                    if (mWebview.canGoBack()) {
                        mWebview.goBack();
                    } else {
                        finish();
                    }
                }
                return true;
            }
        });

    }


    @Override
    public void onPause() {
        if (!this.mWebview.isShown()) {
            this.mWebview.stopLoading();
        }
        super.onPause();
    }

    @SuppressLint("StaticFieldLeak")
    @JavascriptInterface
    public void getData(final String str,final String what) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "getData: called" + str);
            Log.e(TAG, "getData: what-->" + what);
            System.out.println(what);
        }
        downloadfilename = forfacebook + "_" + System.currentTimeMillis() + "_" + ".mp4";

        final Dialog dialog = new Dialog(FacebookPrivateVideoActivity.this);
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
        ((TextView) dialog.findViewById(R.id.txttitle)).setText(FacebookPrivateVideoActivity.this.getResources().getString(R.string.downloading));
        progressBar.setProgress(0);
        textView.setText("0%");
     //   dialog.show();
        String videoPath = str;

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
            videoPath = videoPath.replace("https", HttpHost.DEFAULT_SCHEME_NAME);
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
                Toast.makeText(FacebookPrivateVideoActivity.this, getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();
                String stringBuilder = checkfileexistsornnot + "/" + downloadfilename;
                File file2 = new File(stringBuilder);
               // file.renameTo(file2);
                addVideo(file2);
                /*if (dirname.exists()) {
                    dirname.delete();
                }*/
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

    public void addVideo(File file) {
        ContentValues contentValues = new ContentValues(3);
        contentValues.put("title", file.getName());
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("_data", file.getAbsolutePath());
        FacebookPrivateVideoActivity.this.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

    }



    private void loadAdbmobbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        final AdView mAdView = new AdView(FacebookPrivateVideoActivity.this);
        mAdView.setAdSize(SMART_BANNER);
        mAdView.setAdUnitId(FacebookPrivateVideoActivity.this.getString(R.string.banner_ad_units_id1));
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
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(FacebookPrivateVideoActivity.this, FacebookPrivateVideoActivity.this.getString(R.string.fbbanner), AdSize.BANNER_HEIGHT_50);
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

    Dialog howtouse;

    private void showHowToUsedDialog() {

        howtouse = new Dialog(FacebookPrivateVideoActivity.this);
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
                Glide.with(FacebookPrivateVideoActivity.this).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);
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
                Glide.with(FacebookPrivateVideoActivity.this).load(bitRes[hintIdx]).apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_placeholder)).into(id_iv_introimg);
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