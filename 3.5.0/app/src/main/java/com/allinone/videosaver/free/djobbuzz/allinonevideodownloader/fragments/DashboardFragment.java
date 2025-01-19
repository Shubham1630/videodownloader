package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Intent.ACTION_VIEW;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.alreadydownloaded;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.createFile;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.nodatafound;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.patternforsharechat;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.sharetext;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.videoUrl;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.WhatsAppStatusHandler.copyStatusInSavedDirectory;
import static com.google.android.gms.ads.AdSize.SMART_BANNER;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.LoginActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.NewDashboardActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.NewfbActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.PlayvideosActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.WhatsappCleanerActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.MainActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.database.DBOperations;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.api.data.EdgeModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.api.data.EdgeSidecarModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.api.data.ResponseModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.CommonAPI;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.GoEditTextListener;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.GoEditText;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.FullDetailModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.services.ClipboardMonitorService;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.VideoDownload;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler1;
import com.ortiz.touchview.TouchImageView;


import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import io.reactivex.observers.DisposableObserver;
import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import videodownload.com.newmusically.R;


public class DashboardFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DashboardFragment";
    private NewDashboardActivity activity;
    private Context context;
    private File dirname = null;
    private File filepathfordownload = null;
    private ClipboardManager myClipboard;
    private PrefManager prefManager;
//    private VideoView vdo_ContentVideo;
    private CommonModel newMusicModel;
    private GoEditText eturl;
    private ImageView image;
    private View urlcard;
    private View laycard;
    private View cardinstaprivate;
    private View ll_instaautoprivate;
    private View fbcardinside;
    private View fbappcard;
    private View wacleaner;
    private View fbcleaner;
    private View insta_new;
    private ProgressBar progressLoader;
    private NestedScrollView parentscrollview;
    private LinearLayout app_tiktok;
    private LinearLayout app_mx;
    private LinearLayout app_roposo;
    private LinearLayout app_josh;
    private LinearLayout app_sharechat;
    private LinearLayout app_fb;
    private LinearLayout app_wa;
    private LinearLayout app_wab;
    private LinearLayout app_snack;
    private LinearLayout app_moj;
    private LinearLayout app_insta;
    private LinearLayout app_twitter;
    private LinearLayout app_chingari;
    private LinearLayout app_mitron;
    private LinearLayout app_zili;
    private LinearLayout app_likee;
    private Button btndownload;
    private Button btnshare;
    private ImageView vdopause;
//    private TouchImageView imgpreview;
    private TextView txtPaste;
    private String cliptext = "";
    private String whatdownload = "other";
    private SwitchCompat swAutoDownlaod;
    private SwitchCompat swAutoDownlaodinstagramprivate;
    private String currenturls = "";
    private String downloadfilename = "";
    private File checkfileexistsornnot = null;
    private ShimmerFrameLayout shimmer;
    public BottomSheetDialog bottomSheet;
    public androidx.appcompat.app.AlertDialog alertDialog;
    /* for match*/
    public static final String forsharechat = "sharechat";
    public static final String fortiktok = "tiktok";
    public static final String forjosh = "myjosh";
    public static final String formx = "takatak";
    public static final String forSNACK = "sck.io";
    public static final String forroposo = "roposo.com";
    public static final String forinstagram = "instagram.com";
    public static final String forfacebook = "facebook.com";
    public static final String forfacebookwatch = "fb.watch";
    public static final String forfb_com = "fb.com";
    public static final String forTwitter = "twitter.com";
    public static final String forChingari = "chingari";
    public static final String forMitron = "mitron";
    public static final String forzili = "zilivideo";
    public static final String formoj = "mojapp.in";
    public static final String forLikees = "likee";
    public static final String PKG_TIKTOK = "com.zhiliaoapp.musically";
    public static final String PKG_SHARECHAT = "in.mohalla.sharechat";
    public static final String PKG_JOSH = "com.eterno.shortvideos";
    public static final String PKG_MX = "com.next.innovation.takatak";
    public static final String PKG_FB = "com.facebook.katana";
    public static final String PKG_WA = "com.whatsapp";
    public static final String PKG_WAB = "com.whatsapp.w4b";
    public static final String PKG_SNACK = "com.kwai.bulldog";
    public static final String PKG_ROPOSO = "com.roposo.android";
    public static final String PKG_MOJ = "in.mohalla.video";
    public static final String PKG_INSTAGRAM = "com.instagram.android";
    public static final String PKG_TWITTER = "com.twitter.android";
    public static final String PKG_CHINGARI = "io.chingari.app";
    public static final String PKG_MITRON = "com.mitron.tv";
    public static final String PKG_ZILI = "com.funnypuri.client";
    public static final String PKG_LIKEE = "video.like";
    public static String[] APPLIST = new String[]{"Moj", "Tiktok", "MX Takatak", "Roposo", "Josh", "Sharechat", "Snack", "Instagram", "Facebook", "Twitter", "Chingari", "Mitron", "Zili", "Likee"};
    public static String[] FORLIST = new String[]{formoj, fortiktok, formx, forroposo, forjosh, forsharechat, forSNACK, forinstagram, forfacebook, forTwitter, forChingari, forMitron, forzili, forLikees};
    private Intent intent;
    String CopyKey = "";
    String CopyValue = "";

    private boolean isshareselected = false;
    ViewPropertyTransition.Animator animationObject;
    private RequestOptions requestOptions;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Bundle logEventsBundle;
    private String photoUrl;
    private String videoUrl;

    public DashboardFragment(Intent intent) {
        this.intent = intent;
        // Required empty public constructor
    }

    public DashboardFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();

        activity = (NewDashboardActivity) getActivity();
        logEventsBundle = new Bundle();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        View view = inflater.inflate(R.layout.dashboardfragment, container, false);
        bindviews(view);
        initpref();
        iniviews();
        //checkyns();
        return view;
    }

//    private void checkyns() {
//        if (!is_yns) {
//            List<String> list = new ArrayList<String>(Arrays.asList(FORLIST));
//            list.remove(forYoutube);
//            FORLIST = list.toArray(new String[0]);
//
//            List<String> list1 = new ArrayList<String>(Arrays.asList(APPLIST));
//            list1.remove("YouTube");
//            APPLIST = list1.toArray(new String[0]);
//
//
//        }
//    }


    private void initpref() {
        prefManager = new PrefManager(activity);
    }

    private void bindviews(View view) {
        myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//        vdo_ContentVideo = (VideoView) view.findViewById(R.id.vdo_ContentVideo);
        eturl = (GoEditText) view.findViewById(R.id.eturl);
        swAutoDownlaod = (SwitchCompat) view.findViewById(R.id.swAutoDownlaod);
        swAutoDownlaodinstagramprivate = (SwitchCompat) view.findViewById(R.id.swAutoDownlaodinstagramprivate);
        txtPaste = (TextView) view.findViewById(R.id.txtPaste);
        image = (ImageView) view.findViewById(R.id.image);
        vdopause = (ImageView) view.findViewById(R.id.vdopause);
        btndownload = (Button) view.findViewById(R.id.btndownload);
        btnshare = (Button) view.findViewById(R.id.btnshare);
//        imgpreview = view.findViewById(R.id.imgpreview);
//        progressLoader = (ProgressBar) view.findViewById(R.id.progressLoader);
        parentscrollview = view.findViewById(R.id.parentscrollview);
        urlcard = view.findViewById(R.id.urlcard);
        laycard = view.findViewById(R.id.laycard);
        cardinstaprivate = view.findViewById(R.id.cardinstaprivate);
        ll_instaautoprivate = view.findViewById(R.id.ll_instaautoprivate);
        fbcardinside = view.findViewById(R.id.fbcardinside);
        fbappcard = view.findViewById(R.id.fbappcard);
//        wacleaner = view.findViewById(R.id.wacleaner);
        fbcleaner = view.findViewById(R.id.fbcleaner);
        insta_new = view.findViewById(R.id.insta_new);
        shimmer = view.findViewById(R.id.shimmer);
        shimmer.startShimmer();


        app_tiktok = view.findViewById(R.id.app_tiktok);
        app_mx = view.findViewById(R.id.app_mx);
        app_roposo = view.findViewById(R.id.app_roposo);
        app_josh = view.findViewById(R.id.app_josh);
        app_sharechat = view.findViewById(R.id.app_sharechat);
        app_fb = view.findViewById(R.id.app_fb);
        app_wa = view.findViewById(R.id.app_wa);
        app_wab = view.findViewById(R.id.app_wab);
        app_snack = view.findViewById(R.id.app_snack);
        app_moj = view.findViewById(R.id.app_moj);
        app_insta = view.findViewById(R.id.app_insta);
        app_twitter = view.findViewById(R.id.app_twitter);
        app_chingari = view.findViewById(R.id.app_chingari);
        app_mitron = view.findViewById(R.id.app_mitron);
        app_zili = view.findViewById(R.id.app_zili);
        app_likee = view.findViewById(R.id.app_likee);
        app_tiktok.setOnClickListener(this);
        app_mx.setOnClickListener(this);
        app_roposo.setOnClickListener(this);
        app_josh.setOnClickListener(this);
        app_sharechat.setOnClickListener(this);
        app_fb.setOnClickListener(this);
        app_wa.setOnClickListener(this);
        app_wab.setOnClickListener(this);
        app_snack.setOnClickListener(this);
        app_moj.setOnClickListener(this);
        app_insta.setOnClickListener(this);
        app_twitter.setOnClickListener(this);
        app_chingari.setOnClickListener(this);
        app_mitron.setOnClickListener(this);
        app_zili.setOnClickListener(this);
        app_likee.setOnClickListener(this);
        fbcardinside.setOnClickListener(this);
//        wacleaner.setOnClickListener(this);
        fbcleaner.setOnClickListener(this);
        insta_new.setOnClickListener(this);
        RelativeLayout adView = view.findViewById(R.id.adView);

        animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                // if it's a custom view class, cast it here
                // then find subviews and do the animations
                // here, we just use the entire view for the fade animation
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(500);
                fadeAnim.start();
            }
        };
        requestOptions = new RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void iniviews() {
        eturl.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() <= 0) {
                    setdefaultmode();
                    return;
                }
                image.setVisibility(View.VISIBLE);
                txtPaste.setVisibility(View.GONE);
            }
        });
        txtPaste.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handlepaste(false);
            }
        });
        eturl.addListener(new GoEditTextListener() {
            public void onUpdate() {
                if (!(myClipboard == null || myClipboard.getPrimaryClip() == null)) {
                    ClipData primaryClip = myClipboard.getPrimaryClip();
                    if (primaryClip != null && primaryClip.getItemCount() > 0) {
                        Context context;
                        CharSequence charSequence;
                        ClipData.Item itemAt = primaryClip.getItemAt(0);
                        cliptext = itemAt.getText().toString();
                        currenturls = videoUrl(cliptext);
                        if (TextUtils.isEmpty(currenturls)) {
                            context = activity;
                            charSequence = activity.getResources().getString(R.string.urlnotvalid);
                        } else {
                            Log.e("urlpath", currenturls);
                            //URL download
                            logEventsBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "urlpath");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, logEventsBundle);


                            eturl.setText(currenturls);
//                            vdo_ContentVideo.setVisibility(View.VISIBLE);
                            vdopause.setVisibility(View.GONE);
                            getMusicallyVideo();
                            context = activity;
                            charSequence = activity.getResources().getString(R.string.tapdownload);

                        }
                        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        eturl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 3) {
                    return false;
                }
                try {
                    ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                } catch (Exception unused) {
                    if (TextUtils.isEmpty(eturl.getText().toString()) || TextUtils.isEmpty(currenturls)) {
                        Toast.makeText(activity, activity.getResources().getString(R.string.urlnotvalid), Toast.LENGTH_SHORT).show();
                    } else {
                        getMusicallyVideo();
                    }
                    return true;
                }
                return true;
            }
        });
        if (prefManager.getMusiFirstLaunch()) {
            prefManager.setMusiFirstLaunch(false);

        }
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
                    eturl.setText("");
                    parentscrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            parentscrollview.smoothScrollTo(0, laycard.getTop());
                        }
                    });
                }
                if (activity.snackbar != null && activity.snackbar.isShown()) {
                    activity.snackbar.dismiss();
                }
            }
        });

        btndownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = eturl.getText().toString();
                if (obj.equals("")) {
                    Utils.setToast(requireActivity(), getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                    Utils.setToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
                } else {
                    getInstagramData();
                }

//                shareDialog();
//                activity.checkPermission();
//                checkDownloadVideo();
            }
        });


        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isshareselected = true;
                shareDialog();

                activity.checkPermission();
            }
        });
        shareDialog();
//        vdo_ContentVideo.setOnTouchListener((view, motionEvent) -> {
//            if (vdopause.isShown()) {
//                vdopause.setVisibility(View.GONE);
//                return false;
//            }
//            if (vdo_ContentVideo.isPlaying()) {
//                vdopause.setImageResource(R.drawable.ic_pause);
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        if (vdo_ContentVideo.isPlaying()) {
//                            vdopause.setVisibility(View.GONE);
//                        }
//                    }
//                }, 2000);
//            } else {
//                vdopause.setImageResource(R.drawable.ic_play);
//            }
//            vdopause.setVisibility(View.VISIBLE);
//            return false;
//        });
//        vdopause.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
////                imgpreview.setVisibility(View.GONE);
//                if (vdo_ContentVideo.isPlaying()) {
//                    vdo_ContentVideo.pause();
//                    vdopause.setImageResource(R.drawable.ic_play);
//                    return;
//                }
//                vdo_ContentVideo.start();
//                vdopause.setVisibility(View.GONE);
//            }
//        });
        swAutoDownlaod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    activity.checkPermission();
                    prefManager.setServiceStatus(true);
                    activity.startService(new Intent(activity, ClipboardMonitorService.class));
                    return;
                }
                prefManager.setServiceStatus(false);
                activity.stopService(new Intent(activity, ClipboardMonitorService.class));
            }
        });
        swAutoDownlaod.setChecked(prefManager.getServiceStatus());
        if (prefManager.getServiceStatus()) {
            activity.startService(new Intent(activity, ClipboardMonitorService.class));

        }

        ll_instaautoprivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
                    Intent intent = new Intent(activity,
                            LoginActivity.class);
                    startActivityForResult(intent, 100);
                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                    ab.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            PrefManager.getInstance(activity).putBoolean(PrefManager.ISINSTALOGIN, false);
                            PrefManager.getInstance(activity).putString(PrefManager.COOKIES, "");
                            PrefManager.getInstance(activity).putString(PrefManager.CSRF, "");
                            PrefManager.getInstance(activity).putString(PrefManager.SESSIONID, "");
                            PrefManager.getInstance(activity).putString(PrefManager.USERID, "");

                            if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
                                swAutoDownlaodinstagramprivate.setChecked(true);
                            } else {
                                swAutoDownlaodinstagramprivate.setChecked(false);
                            }
                            dialog.cancel();

                        }
                    });
                    ab.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = ab.create();
                    alert.setTitle(getResources().getString(R.string.do_u_want_to_download_media_from_pvt));
                    alert.show();
                }
            }
        });
        setdefaultmode();
        handleintent();

    }

    private void handlepaste(boolean isfromshare) {
        Log.e(TAG, "handlepaste: called 1");

        try {
            if (!(myClipboard == null || myClipboard.getPrimaryClip() == null) && !isfromshare) {
                Log.e(TAG, "handlepaste: called 2");
                ClipData primaryClip = myClipboard.getPrimaryClip();
                if (primaryClip != null && primaryClip.getItemCount() > 0) {
                    Log.e(TAG, "handlepaste: called 3");
                    ClipData.Item itemAt = primaryClip.getItemAt(0);
                    cliptext = itemAt.getText().toString();
                    currenturls = videoUrl(cliptext);
                    handlemultipleurls(true);
                } else {
                    Log.e(TAG, "handlepaste: called 4");
                    Log.e(TAG, "handlepaste: all is blank");
                }
            } else {
                Log.e(TAG, "handlepaste: called 5");
                if (CopyValue != null && !CopyValue.equals("")) {
                    Log.e(TAG, "handlepaste: called 6");
                    cliptext = CopyValue;
                    currenturls = videoUrl(cliptext);
                    handlemultipleurls(false);
                } else Log.e(TAG, "handlepaste: called 7");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlemultipleurls(boolean b) {
        if (currenturls != null && !TextUtils.isEmpty(currenturls)) {
            Log.e(TAG, "onClick: currenturls :" + currenturls);
            if (currenturls.contains(forsharechat)) {
                whatdownload = forsharechat;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(fortiktok)) {
                whatdownload = fortiktok;
                //
            } else if (currenturls.contains(forjosh)) {
                whatdownload = forjosh;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(formx)) {
                whatdownload = formx;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forSNACK)) {
                whatdownload = forSNACK;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forroposo)) {
                whatdownload = forroposo;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forfacebook) || currenturls.contains(forfacebookwatch) || currenturls.contains(forfb_com)) {
                whatdownload = forfacebook;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forTwitter)) {
                whatdownload = forTwitter;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forChingari)) {
                whatdownload = forChingari;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forMitron)) {
                whatdownload = forMitron;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forzili)) {
                whatdownload = forzili;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forLikees)) {
                whatdownload = forLikees;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(formoj)) {
                whatdownload = formoj;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else if (currenturls.contains(forinstagram)) {
                cardinstaprivate.setVisibility(View.VISIBLE);
                whatdownload = forinstagram;
                ArrayList<String> linkforsharechat = patternforsharechat(currenturls);
                if (linkforsharechat.size() > 0) {
                    currenturls = (String) linkforsharechat.get(0);
                }
            } else {
                Toast.makeText(activity, "OOps This url is not supported", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            Log.e(TAG, "handlemultipleurls: currenturls is null or blank");
        }
        if (!(TextUtils.isEmpty(currenturls) || eturl.getText().toString().trim().equals(currenturls))) {
            eturl.setText(currenturls);
//            vdo_ContentVideo.setVisibility(View.VISIBLE);
            vdopause.setVisibility(View.GONE);
            getMusicallyVideo();
            Toast.makeText(activity, b ? activity.getResources().getString(R.string.tapdownload) : activity.getResources().getString(R.string.tapdownloadadded), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Please paste valid url", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "handlemultipleurls: something wnet wrong");
        }
    }

    private void handleintent() {
        if (intent != null && intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                CopyKey = key;
                String value = intent.getExtras().getString(CopyKey);
                if (CopyKey.equals("android.intent.extra.TEXT")) {
                    CopyValue = intent.getExtras().getString(CopyKey);

                    if (value != null && !value.equals("")) {
                        handlepaste(true);
                        eturl.setText(value);
                        getMusicallyVideo();
                    }
                } else {
                    CopyValue = value;
                    Log.e(TAG, "getintentdata: 2 value : " + value);
                    if (CopyValue != null && !CopyValue.equals("")) {
                        handlepaste(true);
                        eturl.setText(CopyValue);
                        getMusicallyVideo();
                    }
                }
            }
        }
    }

    public void getMusicallyVideo() {
        if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
//            progressLoader.setVisibility(View.VISIBLE);
            parentscrollview.post(new Runnable() {
                @Override
                public void run() {
                    if (whatdownload.equals(forinstagram)) {

                        parentscrollview.smoothScrollTo(0, cardinstaprivate.getTop() - 100);
                    } else {
                        parentscrollview.smoothScrollTo(0, urlcard.getTop() - 100);

                    }


                }
            });
            VideoDownload.Instance().getMusicallyUrl(eturl.getText().toString().trim(), new VideoDownload.MusicallyDelegate() {
                public void OnFailure(String str) {
//                    progressLoader.setVisibility(View.GONE);
//                    vdo_ContentVideo.setVisibility(View.GONE);
//                    imgpreview.setVisibility(View.GONE);
                    vdopause.setVisibility(View.GONE);
//                    btndownload.setEnabled(false);
                    btnshare.setEnabled(false);
                    btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
                    btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));

                    btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                    btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                    if ((TextUtils.isEmpty(str) || !str.contains("Internet Connection")) && (TextUtils.isEmpty(str) || !str.contains(nodatafound))) {
                        if (!TextUtils.isEmpty(str) && str.length() > 0) {
                            if (str.contains("Please login with instagram to download private images/videos.")) {
                                showdialog(str);
                            } else {
                                Toast.makeText(activity, str, Toast.LENGTH_LONG).show();

                            }

                            Log.d("msg", str);
                        }
                        return;
                    }
                    //errorLayout.setError();//todo
                }

                public void OnSuccess(CommonModel musicallyModel) {
                    newMusicModel = musicallyModel;
//                    progressLoader.setVisibility(View.GONE);
//                    imgpreview.setVisibility(View.VISIBLE);

//                    Glide.with(activity.getApplicationContext()).load(musicallyModel.getImagePath()).transition(GenericTransitionOptions.with(animationObject)).apply(requestOptions).into(imgpreview);
//                    if (vdo_ContentVideo != null) {
//                        Log.e(TAG, "OnSuccess: musicallyModel.getVideoPath():" + musicallyModel.getVideoPath());
//                        if (!musicallyModel.getVideoPath().isEmpty()) {
//                            if (whatdownload != null
//                                    && whatdownload.contains(forinstagram)
//                                    && musicallyModel.getVideoUniquePath() != null
//                                    && musicallyModel.getVideoUniquePath().contains(".jpg")) {
//                                // its insta image
//                                vdo_ContentVideo.setOnClickListener(null);
//                                vdo_ContentVideo.setOnTouchListener(null);
//
//                            } else {
//                                Log.e(TAG, "OnSuccess: videourl is not empty");
//                                Uri parse = Uri.parse(musicallyModel.getVideoPath());
//                                MediaController mediaController = new MediaController(activity);
//                                mediaController.setAnchorView(vdo_ContentVideo);
//                                mediaController.setMediaPlayer(vdo_ContentVideo);
//                                vdo_ContentVideo.setMediaController(null);
//                                vdo_ContentVideo.setVideoURI(parse);
//                                vdo_ContentVideo.requestFocus();
//                                vdo_ContentVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                    public void onPrepared(MediaPlayer mediaPlayer) {
//                                        vdopause.setImageResource(R.drawable.ic_play);
//                                        vdopause.setVisibility(View.VISIBLE);
//                                    }
//                                });
//                            }
//
//                        } else {
//                            /* its image*/
//                            Log.e(TAG, "OnSuccess: videourl is  empty");
//                            vdo_ContentVideo.setOnClickListener(null);
//                            vdo_ContentVideo.setOnTouchListener(null);
//
//                        }
//
////                        btndownload.setVisibility(View.VISIBLE);
////                        btnshare.setVisibility(View.VISIBLE);
//
//                        btndownload.setEnabled(true);
//                        btnshare.setEnabled(true);
//                        btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.white));
//                        btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.white));
//
//                        btndownload.setBackgroundResource(R.drawable.main_gradi_btn);
//                        btnshare.setBackgroundResource(R.drawable.main_gradi_btn);
//                        btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.white));
//                        btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.white));
//                    }
                }
            });
        }
    }


    private void setdefaultmode() {
        if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
            swAutoDownlaodinstagramprivate.setChecked(true);
        } else {
            swAutoDownlaodinstagramprivate.setChecked(false);
        }
        txtPaste.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);
//        imgpreview.setVisibility(View.GONE);
        vdopause.setVisibility(View.GONE);
//        vdo_ContentVideo.setVisibility(View.GONE);
//        btndownload.setEnabled(false);
        btnshare.setEnabled(false);
//        btndownload.setVisibility(View.GONE);
        btnshare.setVisibility(View.GONE);
        cardinstaprivate.setVisibility(View.GONE);
        btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
        btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
        btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
        btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
    }

    private void showdialog(String msg) {

        logEventsBundle.putString("INSTA_PRIVATE_VDO", "private vdo");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, logEventsBundle);

        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Login",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertDialog.dismiss();
                        startActivity(new Intent(activity, com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.LoginActivity.class).putExtra("isSearch", false));
//                        activity.finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    public static boolean isPackageInstalled(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }


    public void checkDownloadVideo() {
        if (newMusicModel != null) {
            String title = newMusicModel.getTitle();
            String videoUniquePath = newMusicModel.getVideoUniquePath();
            downloadfilename = whatdownload + "_" + title + videoUniquePath;
            Log.e("downloadUniquePath :", downloadfilename);
            if (!TextUtils.isEmpty(downloadfilename)) {
                //   String path = Utility.STORAGEDIR + whatdownload + "/" + downloadfilename;
                String path = Utility.STORAGEDIR + "/" + downloadfilename;
                filepathfordownload = new File(path);
                if (filepathfordownload.exists()) {
                    activity.snackbar = Snackbar.make(activity.constraint, alreadydownloaded, BaseTransientBottomBar.LENGTH_INDEFINITE).setAction((CharSequence) "OPEN", new View.OnClickListener() {
                        public void onClick(View view) {
                            if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
                                eturl.setText("");
//                                vdo_ContentVideo.setVisibility(View.GONE);
//                                imgpreview.setVisibility(View.GONE);
                                vdopause.setVisibility(View.GONE);
                                txtPaste.setVisibility(View.VISIBLE);
                                image.setVisibility(View.GONE);
//                                btndownload.setEnabled(false);
                                btnshare.setEnabled(false);
                                btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
                                btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
                                btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                                btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
                            }
                            new BackGroundTask().execute();
                        }
                    });
                    ((TextView) activity.snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(-1);
                    activity.snackbar.show();
                } else {
                    if (dirname != null) {
                        path = dirname + "/" + downloadfilename;
                        if (new File(path).exists()) {
                            Toast.makeText(activity, activity.getResources().getString(R.string.alreadydownloading), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    downloadMusiVideo();
                }
            }
        } else {
            Log.e(TAG, "checkDownloadVideo: newMusicModel is null");
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void downloadMusiVideo() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.appdialog);
        dialog.setCancelable(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        dialog.getWindow().setAttributes(layoutParams);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressDialog);
        final TextView textView = (TextView) dialog.findViewById(R.id.txtcountstart);
        ((TextView) dialog.findViewById(R.id.txttitle)).setText(activity.getResources().getString(R.string.downloading));
        progressBar.setProgress(0);
        textView.setText("0%");
        //dialog.show();
        String videoPath = newMusicModel.getVideoPath();
        if (videoPath == null || videoPath.isEmpty())
            videoPath = newMusicModel.getImagePath();
        Log.e("videoUrl", videoPath);
        String path = Utility.STORAGEDIR;//+ whatdownload;

        checkfileexistsornnot = new File(path);
        if (!checkfileexistsornnot.exists()) {
            checkfileexistsornnot.mkdir();
        }
        //path = checkfileexistsornnot.getAbsolutePath() + "/.temp/";
        path = checkfileexistsornnot.getAbsolutePath() + "/";
        dirname = new File(path);
        if (!dirname.exists()) {
            dirname.mkdir();
        }

        if (videoPath.contains("https") && !whatdownload.equals(forMitron) && !whatdownload.equals(fortiktok)) {
            videoPath = videoPath.replace("https", "http");
        }
        String finalVideoPath = videoPath;
        /* insert into db now*/
        Log.e(TAG, "downloadMusiVideo:file: " + dirname.getAbsolutePath());
        Log.e(TAG, "downloadMusiVideo:downloadfilename: " + downloadfilename);
        CommonModel commonModel = new CommonModel();
        commonModel.setSaved_video_url(videoPath);
        commonModel.setSaved_video_date(String.valueOf(System.currentTimeMillis()));
        commonModel.setSaved_video_path(dirname.getAbsolutePath());
        commonModel.setSaved_video_name(downloadfilename);
        if (!TextUtils.isEmpty(eturl.getText().toString().trim())) {
            eturl.setText("");
//            vdo_ContentVideo.setVisibility(View.GONE);
//            imgpreview.setVisibility(View.GONE);
            vdopause.setVisibility(View.GONE);
            txtPaste.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
//            btndownload.setEnabled(false);
            btnshare.setEnabled(false);
            btndownload.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));
            btnshare.setTextColor(activity.getApplication().getResources().getColor(R.color.appbgcolor));

            btndownload.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
            btnshare.setBackgroundResource(R.drawable.main_gradi_btn_disabled);
        }
        Toast.makeText(activity, activity.getResources().getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        DBOperations.InsertDownload(activity, commonModel, new DownloadListener() {
            @Override
            public void onComplete(int totalBytes) {
                Toast.makeText(activity, activity.getResources().getString(R.string.download_complate), Toast.LENGTH_SHORT).show();

                String stringBuilder = checkfileexistsornnot + "/" + downloadfilename;
                File file2 = new File(stringBuilder);
                //file.renameTo(file2);
                addVideo(file2);
                /*if (dirname.exists()) {
                    dirname.delete();
                }*/
                if (prefManager.getSaveFirstLaunch()) {
                    prefManager.setSaveFirstLaunch(false);

                }
            }

            @Override
            public void onPause(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onPending(int percent, int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onFail(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {
                Log.e(TAG, "onFail: called" + reason.getValue());
            }

            @Override
            public void onCancel(int totalBytes, int downloadedBytes) {

            }

            @Override
            public void onRunning(int percent, int totalBytes, int downloadedBytes, float downloadSpeed) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_mx:
                handleclick(PKG_MX);
                break;
            case R.id.app_tiktok:
                handleclick(PKG_TIKTOK);
                break;
            case R.id.app_roposo:
                handleclick(PKG_ROPOSO);
                break;
            case R.id.app_josh:
                handleclick(PKG_JOSH);
                break;
            case R.id.app_sharechat:
                handleclick(PKG_SHARECHAT);
                break;
            case R.id.app_fb:
                handleclick(PKG_FB);
                break;
            case R.id.app_wa:
                handleclick(PKG_WA);
                break;
            case R.id.app_wab:
                handleclick(PKG_WAB);
                break;
            case R.id.app_snack:
                handleclick(PKG_SNACK);
                break;
            case R.id.app_moj:
                handleclick(PKG_MOJ);
                break;
            case R.id.app_insta:
                handleclick(PKG_INSTAGRAM);
                break;
            case R.id.app_twitter:
                handleclick(PKG_TWITTER);
                break;
            case R.id.app_chingari:
                handleclick(PKG_CHINGARI);
                break;
            case R.id.app_mitron:
                handleclick(PKG_MITRON);
                break;
            case R.id.app_zili:
                handleclick(PKG_ZILI);
                break;
            case R.id.app_likee:
                handleclick(PKG_LIKEE);
                break;




            case R.id.fbcleaner:

                startActivity(new Intent(activity, NewfbActivity.class));
                break;
            case R.id.insta_new:

                if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
                    callStoriesDetailApi();
                    startActivity(new Intent(activity, MainActivity.class));
                    // finish();
                    return;
                }
                startActivity(new Intent(activity, com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.LoginActivity.class).putExtra("isSearch", false));
                break;
//                startActivity(new Intent(activity, InstaActivity.class));
//                break;

        }
    }


    private void callStoriesDetailApi() {
        try {

            if (!new Utils(this.activity).isNetworkAvailable()) {
                Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
            } else {

                ServiceHandler.get(Config.USERFEEDSTORY(PrefManager.getInstance(this.activity).getString(PrefManager.USERID), ""), null, new TextHttpResponseHandler1() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Utils.hideProgress();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            FullDetailModel fullDetailModel = new Gson().fromJson(responseString, FullDetailModel.class);
                            PrefManager instance = PrefManager.getInstance(activity);
                            instance.putString(PrefManager.FULLNAME, fullDetailModel.getUser_detail().getUser().getFull_name());
                            instance.putString(PrefManager.USERNAME, fullDetailModel.getUser_detail().getUser().getUsername());
                            instance.putString(PrefManager.PROFILEIMAGEURL, fullDetailModel.getUser_detail().getUser().getHdProfileModel().getUrl());
                            Utils.hideProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, true, Config.getCookie(requireActivity()), Config.USERAGENT);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void opencleanerdialog() {
        startActivity(new Intent(activity, WhatsappCleanerActivity.class));
    }


    private void handleclick(String str) {

        Intent launchIntent1 = activity.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntent1 != null) {
            activity.startActivity(launchIntent1);
        } else {
            String stringBuilder = "market://details?id=" + str;
            Intent intent = new Intent(ACTION_VIEW, Uri.parse(stringBuilder));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }


    }

    private class BackGroundTask extends AsyncTask<Void, Void, ArrayList<String>> {

        protected ArrayList<String> doInBackground(Void... voidArr) {
            String stringBuilder = Utility.STORAGEDIR;
            File file = new File(stringBuilder);
            ArrayList<String> arrayList = new ArrayList();
            if (file != null) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    Arrays.sort(listFiles, new Comparator() {
                        public int compare(Object obj, Object obj2) {
                            File file = (File) obj;
                            File file2 = (File) obj2;
                            return file.lastModified() > file2.lastModified() ? -1 : file.lastModified() < file2.lastModified() ? 1 : 0;
                        }
                    });
                    for (int i = 0; i < listFiles.length; i++) {
                        if (!listFiles[i].getAbsolutePath().contains(".temp")) {
                            arrayList.add(listFiles[i].getAbsolutePath());
                            Log.e("videoPath", listFiles[i].getAbsolutePath());
                        }
                    }
                }
            }
            return arrayList;
        }

        protected void onPostExecute(ArrayList<String> arrayList) {
            if (activity.snackbar != null && activity.snackbar.isShown()) {
                activity.snackbar.dismiss();
            }
            if (arrayList != null) {
                Intent intent = new Intent(activity, PlayvideosActivity.class);
                int i = 0;
                if (!arrayList.isEmpty()) {
                    i = arrayList.indexOf(filepathfordownload.getAbsolutePath());
                    Log.e("particularVideoPath", filepathfordownload.getAbsolutePath());
                }
                intent.putExtra("item", i);
                intent.putExtra("videoPath", arrayList);
                startActivity(intent);
            }
            super.onPostExecute(arrayList);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public void addVideo(File file) {
        if (isshareselected) {
            isshareselected = false;
            copyStatusInSavedDirectory(file.getAbsolutePath(), "", "s", activity);
            //shareVideoToOtherApps(activity,);
        }
        ContentValues contentValues = new ContentValues(3);
        contentValues.put("title", file.getName());
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("_data", file.getAbsolutePath());
        activity.getApplicationContext().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

    }

    public void shareDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_back);
        dialog.setContentView(R.layout.appdialog_share);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -2;
        layoutParams.height = -2;

        dialog.getWindow().setAttributes(layoutParams);
        if (prefManager.getshare() % 20 == 0) {
            dialog.show();
        }
        prefManager.setSHARE(prefManager.getshare() + 1);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                if (prefManager.getLater() >= 2) {
                    prefManager.setNever(true);
                } else {
                    prefManager.setLater(prefManager.getLater() + 1);
                }
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.tvShare)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", sharetext);
                startActivity(Intent.createChooser(intent, "Share Text"));
                prefManager.setNever(true);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult: called fragment");
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 100 && resultCode == RESULT_OK) {
                String requiredValue = data.getStringExtra("key");
                if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
                    swAutoDownlaodinstagramprivate.setChecked(true);
                } else {
                    swAutoDownlaodinstagramprivate.setChecked(false);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }



    private void loadAdbmobbanner(final RelativeLayout topBannerView) {
        if (topBannerView.getChildCount() > 0) {
            topBannerView.removeAllViews();
        }
        topBannerView.setVisibility(View.GONE);
        final AdView mAdView = new AdView(context);
        mAdView.setAdSize(SMART_BANNER);
        mAdView.setAdUnitId(context.getString(R.string.banner_ad_units_id1));
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
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, context.getString(R.string.fbbanner), AdSize.BANNER_HEIGHT_50);
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

    @Override
    public void onResume() {
        super.onResume();
        shimmer.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmer.stopShimmer();
    }

    private void getInstagramData() {
        try {
            createFile();

            URL url = new URL(eturl.getText().toString());
            String host = url.getHost();
            Log.e("initViews: ", host);
            if (host.equals("www.instagram.com")) {
                downloadUrl(eturl.getText().toString());
            } else {
                Utils.setToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private DisposableObserver<JsonObject> disposableObserver = new DisposableObserver<JsonObject>() {
        public void onNext(JsonObject jsonObject) {
//            Utils.hideProgress(requireActivity());
            try {
                Log.e("onNext: ", jsonObject.toString());
                ResponseModel responseModel = (ResponseModel) new Gson().fromJson(jsonObject.toString(), new TypeToken<ResponseModel>() {
                }.getType());
                EdgeSidecarModel edgeSidecarToChildren = responseModel.getGraphql().getShortcodeMedia().getEdgeSidecarToChildren();
                if (edgeSidecarToChildren != null) {
                    List<EdgeModel> edges = edgeSidecarToChildren.getEdges();
                    for (int i = 0; i < edges.size(); i++) {
                        if (edges.get(i).getNode().isVideo()) {
                            videoUrl = edges.get(i).getNode().getVideoUrl();
                            String str = videoUrl;

                            Utils.startDownloadNew(str, "/In Insta/", requireActivity(), "Instagram_" + System.currentTimeMillis() + ".mp4");
//                            binding.editTextPasteUrl.setText("");
                            videoUrl = "";
                        } else {
                            photoUrl = edges.get(i).getNode().getDisplayResources().get(edges.get(i).getNode().getDisplayResources().size() - 1).getSrc();
                            String str2 = photoUrl;

                            Utils.startDownloadNew(str2, "/In Insta/", requireActivity(), "Instagram_" + System.currentTimeMillis() + ".png");
                            photoUrl = "";
//                            binding.editTextPasteUrl.setText("");
                        }
                    }
                } else if (responseModel.getGraphql().getShortcodeMedia().isVideo()) {
                    videoUrl = responseModel.getGraphql().getShortcodeMedia().getVideoUrl();
                    String str3 = videoUrl;

                    Utils.startDownloadNew(str3, "/In Insta/", requireActivity(), "Instagram_" + System.currentTimeMillis() + ".mp4");
                    videoUrl = "";
//                    binding.editTextPasteUrl.setText("");
                } else {
                    photoUrl = responseModel.getGraphql().getShortcodeMedia().getDisplayResources().get(responseModel.getGraphql().getShortcodeMedia().getDisplayResources().size() - 1).getSrc();
                    String str4 = photoUrl;

                    Utils.startDownloadNew(str4, "/In Insta/", requireActivity(), "Instagram_" + System.currentTimeMillis() + ".png");
                    photoUrl = "";
//                    binding.editTextPasteUrl.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable th) {
//            Utils.hideProgress(requireActivity());
            th.printStackTrace();
        }

        @Override
        public void onComplete() {
//            Utils.hideProgress(requireActivity());

        }
    };



    private void downloadUrl(String Url) {
        String UrlWithoutQP = getUrlWithoutParameters(Url);
        UrlWithoutQP = UrlWithoutQP + "?__a=1&__d=dis";
        try {
            Utils utils = new Utils(requireActivity());
            if (utils.isNetworkAvailable()) {

//                    Utils.showProgress(requireActivity());
                    new CommonAPI().Result(disposableObserver, UrlWithoutQP, OrgCookies());

            } else {
                Utils.setToast(requireActivity(), getResources().getString(R.string.no_net_conn));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    private String getUrlWithoutParameters(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.setToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
            return "";
        }
    }



    public final String[] tempCookies = new String[]{
            "mid=ZN2CxAABAAHzMIv07waly4FXKqK5; ig_did=ACB4224A-B140-41E7-A997-D70B39DEF4D7; ig_nrcb=1; csrftoken=2DrgWxAo8cElhRvzAfStl6vtVFLrFLNi",
            "mid=ZN2LfwABAAHuZKYpRlMpeosaCh8F; ig_did=D5A933FC-34A1-4279-8A2A-DE4A44A0C5E9; ig_nrcb=1; csrftoken=4QSWZ2AIRXo0C033A1ba82Pw47SXZmWX",
            "mid=ZN2T_QABAAGw6zGlN-S7TkDdWs6w; ig_did=7224E0E4-B549-4F0F-91E4-D91ADFBE0FA7; ig_nrcb=1; csrftoken=zTfR4I2PsePGaRcm4CelEkT8NDvbmYCE",
            "mid= ZaOLiwALAAGPBkMCZ6Rq06Wbzdsx; datr=evifZdUhQn6sGdNuvuOtNOvU; ig_did=F3C79A5C-4A71-46FF-9E13-26FA2815FEBA; dpr=1.25; csrftoken=x35xVMDI5uATq7iW4dTn-o"
            // add your own cookies
    };

    public String getTempCookiesOld() {
        int random = new Random().nextInt(4);
        return tempCookies[random];
    }

    public String OrgCookies() {
        String userId = PrefManager.getInstance(activity).getString(PrefManager.USERID);
        if (!Objects.equals(userId, "")) {
            return userId;
        } else {
            return getTempCookiesOld();
        }
    }
}

