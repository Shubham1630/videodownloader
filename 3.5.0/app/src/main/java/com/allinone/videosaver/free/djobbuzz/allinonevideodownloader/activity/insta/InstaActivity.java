//package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;
//
//
//import android.content.ClipboardManager;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.view.animation.LayoutAnimationController;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.cardview.widget.CardView;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.allinone.videosaver.free.videodownloader.statussaver.activity.FullStoryViewActivity;
//import com.allinone.videosaver.free.videodownloader.statussaver.adapter.InstaStoryAdapter;
//import com.allinone.videosaver.free.videodownloader.statussaver.helper.ServiceHandler;
//import com.allinone.videosaver.free.videodownloader.statussaver.interfaces.UserListInterface;
//import com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.instagramstory.ModelMyStories;
//import com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.instagramstory.ModelTrail;
//import com.allinone.videosaver.free.videodownloader.statussaver.utility.LocaleManager;
//import com.allinone.videosaver.free.videodownloader.statussaver.utility.PrefManager;
//import com.facebook.shimmer.ShimmerFrameLayout;
//import com.google.gson.Gson;
//import com.loopj.android.http.TextHttpResponseHandler1;
//
//import cz.msebera.android.httpclient.Header;
//import videodownload.com.newmusically.R;
//
//
//public class InstaActivity extends AppCompatActivity implements View.OnClickListener {
//    private static final String TAG = "InstaActivity";
//    private InstaActivity activity;
//    private CardView cardinstalogin;
//    private CardView cardinstastory;
//
//    private ImageView instalogout;
//
//    private RecyclerView rvuserlist;
//    private ProgressBar pr_loading_bar;
//    private ClipboardManager clipBoard;
//    private TextView opentiktok;
//    private ShimmerFrameLayout shimmer;
//    InstaStoryAdapter userListAdapter;
//    AlertDialog alertDialog;
//    private Toolbar mToolbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        handledarkmode(this);
//        activity = this;
//        LocaleManager.setLocale(activity);
//        setContentView(R.layout.fragment_story);
//        binddata();
//        initview();
//    }
//
//    private void binddata() {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setVisibility(View.VISIBLE);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24));
//
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                //What to do on back clicked
//            }
//        });
//        cardinstalogin = findViewById(R.id.cardinstalogin);
//        shimmer = findViewById(R.id.shimmer);
//        cardinstastory = findViewById(R.id.cardinstastory);
//        pr_loading_bar = findViewById(R.id.pr_loading_bar);
//        instalogout = findViewById(R.id.instalogout);
//        opentiktok = findViewById(R.id.opentiktok);
//        opentiktok.setOnClickListener(this);
//        rvuserlist = findViewById(R.id.RVUserList);
//        rvuserlist.setLayoutManager(new LinearLayoutManager(activity));
//        rvuserlist.setNestedScrollingEnabled(true);
//        instalogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showdialog();
//            }
//        });
//
//
//    }
//
//    private void initview() {
//        clipBoard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//        if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
//            layoutCondition(true);
//            callStoriesApi();
//
//        } else {
//            layoutCondition(false);
//        }
//    }
//
//    private void callStoriesApi() {
//        pr_loading_bar.setIndeterminate(false);
//        pr_loading_bar.setVisibility(View.VISIBLE);
//        String Coockie = "ds_user_id=" + PrefManager.getInstance(activity).getString(PrefManager.USERID)
//                + "; sessionid=" + PrefManager.getInstance(activity).getString(PrefManager.SESSIONID);
//        String ua = "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"";
//        Log.e(TAG, "callStoriesApi: Coockie:" + Coockie);
//        ServiceHandler.get("https://i.instagram.com/api/v1/feed/reels_tray/", null, new TextHttpResponseHandler1() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                Log.e(TAG, "onSuccess: " + responseString);
//                ModelMyStories modelMyStories = new Gson().fromJson(responseString, ModelMyStories.class);
//                Log.e(TAG, "onSuccess: storyModel:" + modelMyStories.toString());
//                rvuserlist.setVisibility(View.VISIBLE);
//                pr_loading_bar.setVisibility(View.GONE);
//                try {
//                    userListAdapter = new InstaStoryAdapter(activity, modelMyStories.getTray(), new UserListInterface() {
//                        @Override
//                        public void userListClick(int position, ModelTrail modelTrail) {
//                            startActivity(new Intent(activity, FullStoryViewActivity.class).putExtra("user", String.valueOf(modelTrail.getUser().getPk())));
//
//                        }
//                    });
//                    rvuserlist.setAdapter(userListAdapter);
//                    LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_fall_down);
//                    rvuserlist.setLayoutAnimation(animation);
//                    userListAdapter.notifyDataSetChanged();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, true, Coockie, ua);
//    }
//
//    private void layoutCondition(boolean b) {
//        cardinstalogin.setVisibility(b ? View.GONE : View.VISIBLE);
//        cardinstastory.setVisibility(b ? View.VISIBLE : View.GONE);
//        if (!b) {
//            shimmer.startShimmer();
//        }
//    }
//
//    private void showdialog() {
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
//        alertDialogBuilder.setMessage("Are you sure you want to logout from Instagram?");
//        alertDialogBuilder.setPositiveButton("Yes",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        alertDialog.dismiss();
//                        PrefManager.getInstance(activity).clearSharePrefs();
//                        initview();
//                    }
//                });
//
//        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//
//            public void onClick(DialogInterface dialog, int which) {
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.opentiktok:
//                Intent intent = new Intent(activity,
//                        LoginActivity.class);
//                startActivityForResult(intent, 100);
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Log.e(TAG, "onActivityResult: called fragment");
//        try {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            if (requestCode == 100 && resultCode == RESULT_OK) {
//                if (PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
//                    layoutCondition(true);
//                    callStoriesApi();
//
//                } else {
//                    layoutCondition(false);
//                }
//
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (alertDialog != null) {
//            alertDialog.dismiss();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (shimmer.isShimmerStarted()) {
//            shimmer.stopShimmer();
//        }
//    }
//}