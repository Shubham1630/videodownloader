package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.BaseActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.TimelineAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.UserListAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.CommonAPI;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.TimelineClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.UserListInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.DisplayResource;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.Edge;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.EdgeSidecarToChildren;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.ResponseModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.StoryModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.TrayModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline.TimelineItemModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline.TimelineModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler1;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import videodownload.com.newmusically.R;


public class MainActivity extends BaseActivity implements UserListInterface, TimelineClickInterface {
    public static CommonAPI commonAPI;
    MainActivity mainActivity;
    private static final String TAG = "MainActivity";
    private static int LIMITSTART;
    String Code;
    String MaxId;
    String ShareRepostDownload;
    MainActivity activity;
    ImageView im_back;
    ImageView imInfo;
    ImageView ivLogout;
    CircleImageView imHeadImage;
    TextView tvHeadName;
    TextView tvHeadUserName;
    TextView tv_HeaderTitle;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView RVTimelineList;
    RecyclerView RVUserList;
    ProgressBar pr_loading_bar;
    AlertDialog alertDialog;
    // AppLangSessionManager appLangSessionManager;

    boolean doubleBackToExitPressedOnce = false;
    boolean isMoreAvailable;

    private int pastVisiblesItems;
    String[] permissions;
    PrefManager sharePrefs;
    TimelineAdapter timelineAdapter;
    ArrayList<TimelineItemModel> timelineListAll;
    private int totalItemCount;
    UserListAdapter userListAdapter;
    private int visibleItemCount;
    private View RLHeadProfile;



    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mainActivity = this;
        setContentView(R.layout.activity_insta_main);
        this.activity = this;
        this.sharePrefs = PrefManager.getInstance(this.activity);
        String str = "";
        this.MaxId = str;
        this.isMoreAvailable = false;
        this.permissions = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
        this.Code = str;
        this.ShareRepostDownload = str;
        commonAPI = CommonAPI.getInstance(mainActivity);

        initViews();

    }


    private void initViews() {
        RLHeadProfile = findViewById(R.id.RLHeadProfile);
        im_back = findViewById(R.id.im_back);
        imInfo = findViewById(R.id.imInfo);
        ivLogout = findViewById(R.id.ivLogout);
        imHeadImage = findViewById(R.id.imHeadImage);
        tvHeadUserName = findViewById(R.id.tvHeadUserName);
        tvHeadName = findViewById(R.id.tvHeadName);
        RVTimelineList = findViewById(R.id.RVTimelineList);
        RVUserList = findViewById(R.id.RVUserList);
        pr_loading_bar = findViewById(R.id.pr_loading_bar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        tv_HeaderTitle = findViewById(R.id.tv_HeaderTitle);
        im_back.setImageResource(R.drawable.ic_baseline_arrow_back_24);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RLHeadProfile.setVisibility(View.GONE);
        tv_HeaderTitle.setVisibility(View.VISIBLE);
        imInfo.setVisibility(View.VISIBLE);
        ivLogout.setVisibility(View.VISIBLE);
        imInfo.setImageResource(R.drawable.ic_round_search_24);
        imInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.activity, SearchUserActivity.class));
            }
        });
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
        tv_HeaderTitle.setText("Instagram All Saver");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        RVUserList.setLayoutManager(gridLayoutManager);
        RVUserList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        this.timelineListAll = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        RVTimelineList.setLayoutManager(linearLayoutManager);
        RVTimelineList.setNestedScrollingEnabled(false);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.timelineAdapter = new TimelineAdapter(this.activity, this.timelineListAll, this);
        RVTimelineList.setAdapter(this.timelineAdapter);
        swipeRefreshLayout.setRefreshing(true);
        callStoriesApi();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                LIMITSTART = 0;

                isMoreAvailable = false;
                MaxId = "";
                callStoriesApi();
            }
        });

        RVTimelineList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }

            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (i2 > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (isMoreAvailable && visibleItemCount + pastVisiblesItems >= totalItemCount) {

                        isMoreAvailable = false;
                        pr_loading_bar.setVisibility(View.VISIBLE);
                        callTimelineApi();
                    }
                }
            }
        });

        Utils.createFileFolder();

    }

    private void showdialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Are you sure you want to logout from Instagram?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertDialog.dismiss();
                        PrefManager.getInstance(activity).clearSharePrefs();
                        startActivity(new Intent(activity, com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.LoginActivity.class).putExtra("isSearch",false));
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void callStoriesApi() {
        try {
            if (!new Utils(this.activity).isNetworkAvailable()) {
                Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
            } else {
                ServiceHandler.get(Config.STORYUSERLIST, null, new TextHttpResponseHandler1() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            StoryModel storyModel = new Gson().fromJson(responseString, StoryModel.class);
                            ArrayList<TrayModel> arrayList = new ArrayList<>();
                            for (int i = 0; i < storyModel.getTray().size(); i++) {
                                try {
                                    if (((TrayModel) storyModel.getTray().get(i)).getUser().getFull_name() != null) {
                                        arrayList.add(storyModel.getTray().get(i));
                                    }
                                } catch (Exception unused) {
                                    unused.printStackTrace();
                                }
                            }
                            userListAdapter = new UserListAdapter(activity, arrayList, activity);
                            RVUserList.setAdapter(userListAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callTimelineApi();
                    }
                }, true, Config.getCookie(MainActivity.this), Config.USERAGENT);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callTimelineApi() {
        try {
            if (!new Utils(this.activity).isNetworkAvailable()) {
                Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
            } else {
                ServiceHandler.get(Config.USERTIMELINE + this.MaxId, null, new TextHttpResponseHandler1() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        swipeRefreshLayout.setRefreshing(false);
                        pr_loading_bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            TimelineModel timelineModel = new Gson().fromJson(responseString, TimelineModel.class);
                            int i = 0;
                            swipeRefreshLayout.setRefreshing(false);
                            pr_loading_bar.setVisibility(View.GONE);
                            if (timelineModel != null) {
                                try {
                                    if (timelineModel.getItems().size() > 0) {
                                        if (LIMITSTART == 0) {
                                            timelineListAll.clear();
                                        }
                                        while (i < timelineModel.getItems().size()) {
                                            if (!(timelineModel.getItems().get(i).getPk() == 0 || timelineModel.getItems().get(i).getId() == null)) {
                                                timelineListAll.add(timelineModel.getItems().get(i));
                                            }
                                            i++;
                                        }
                                        LIMITSTART = LIMITSTART + 1;
                                        isMoreAvailable = timelineModel.isMore_available();
                                        if (timelineModel.getNext_max_id() != null) {
                                            MaxId = timelineModel.getNext_max_id();
                                        }
                                    } else {
                                        isMoreAvailable = false;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        timelineAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        pr_loading_bar.setVisibility(View.GONE);
                    }
                }, true, Config.getCookie(MainActivity.this), Config.USERAGENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();

    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void userListClick(int i, TrayModel trayModel) {

        Intent intent = new Intent(this.activity, StoryFeedDetailActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(trayModel.getUser().getPk());
        String str = "";
        stringBuilder.append(str);
        intent.putExtra("UserId", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(trayModel.getUser().getFull_name());
        stringBuilder.append(str);
        intent.putExtra("Name", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(trayModel.getUser().getUsername());
        stringBuilder.append(str);
        intent.putExtra("UserName", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(trayModel.getUser().getProfile_pic_url());
        stringBuilder.append(str);
        intent.putExtra("ProfileImage", stringBuilder.toString());
        startActivity(intent);
    }


    private boolean checkPermissions(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this.activity, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            if (i == 101) {
                callDownloadImageApi(this.Code);
            }
            return true;
        }
        ActivityCompat.requestPermissions(this.activity, arrayList.toArray(new String[arrayList.size()]), i);
        return false;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 100) {
            if (iArr.length > 0) {
                i = iArr[0];
            }
            return;
        }
        if (i == 101 && iArr.length > 0 && iArr[0] == 0) {
            callDownloadImageApi(this.Code);
        }
    }

    public void UserProfileClick(int i, TimelineItemModel timelineItemModel) {

        Intent intent = new Intent(this.activity, StoryFeedDetailActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(timelineItemModel.getUser().getPk());
        String str = "";
        stringBuilder.append(str);
        intent.putExtra("UserId", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(timelineItemModel.getUser().getFull_name());
        stringBuilder.append(str);
        intent.putExtra("Name", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(timelineItemModel.getUser().getUsername());
        stringBuilder.append(str);
        intent.putExtra("UserName", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(timelineItemModel.getUser().getProfile_pic_url());
        stringBuilder.append(str);
        intent.putExtra("ProfileImage", stringBuilder.toString());
        startActivity(intent);
    }

    public void TimelineImageClick(int i, TimelineItemModel timelineItemModel) {

        Log.e(TAG, "TimelineImageClick: called");
        Intent intent = new Intent(this.activity, FullViewActivity.class);
        String str = "";
        String stringBuilder = timelineItemModel.getCode() +
                str;
        intent.putExtra("Code", stringBuilder);
        intent.putExtra("Story", "2");
        intent.putExtra("TimelineItemModel", timelineItemModel);
//        intent.putExtra("StoryList", this.timelineListAll);
//        intent.putExtra("StoryUrl", str);
        intent.putExtra("Position", i);
        intent.putExtra("From", str);
        startActivity(intent);


    }

    public void DownloadClick(int i, TimelineItemModel timelineItemModel) {

        String str = "insta_";
        String videoUrl;

        StringBuilder stringBuilder;
        if ((timelineItemModel.getMedia_type() == 2)) {
            videoUrl = timelineItemModel.getVideo_versions().get(0).getUrl();

            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(timelineItemModel.getId());
            stringBuilder.append(".mp4");
        } else {
            videoUrl = (timelineItemModel.getImage_versions2().getCandidates().get(0)).getUrl();

            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(timelineItemModel.getId());
            stringBuilder.append(".png");
        }
        Utils.startDownload(videoUrl, this, stringBuilder.toString(), "NoShare");

    }

    public void RepostClick(int i, TimelineItemModel timelineItemModel) {

        this.Code = timelineItemModel.getCode();
        this.ShareRepostDownload = "Repost";
        callDownloadImageApi(timelineItemModel.getCode());
    }

    public void ShareClick(int i, TimelineItemModel timelineItemModel) {

        String str = "insta_";
        String videoUrl;

        StringBuilder stringBuilder;
        if ((timelineItemModel.getMedia_type() == 2)) {
            videoUrl = timelineItemModel.getVideo_versions().get(0).getUrl();

            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(timelineItemModel.getId());
            stringBuilder.append(".mp4");
        } else {
            videoUrl = (timelineItemModel.getImage_versions2().getCandidates().get(0)).getUrl();

            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(timelineItemModel.getId());
            stringBuilder.append(".png");
        }
        Utils.startDownload(videoUrl, this, stringBuilder.toString(), "Share");

//        this.Code = timelineItemModel.getCode();
//        this.ShareRepostDownload = "Share";
//        callDownloadImageApi(timelineItemModel.getCode());
    }

    private void callDownloadImageApi(String str) {
        Log.e(TAG, "callDownloadImageApi: called:" + str);
        str = Config.POSTDETAILLINK + str + "?__a=1";
        try {
            Utils.showProgress(this.activity);
            String str2 = Config.USERAGENT;
            if (str.contains("/tv/")) {
                str2 = Config.USERAGENTIGTV;
            }
            ServiceHandler.get(str, null, new TextHttpResponseHandler1() {

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    Log.e(TAG, "onNext: called:" + responseString);
                    Utils.hideProgress();
                    try {
                        ResponseModel responseModel = new Gson().fromJson(responseString, new TypeToken<ResponseModel>() {
                        }.getType());
                        EdgeSidecarToChildren edge_sidecar_to_children = responseModel.getGraphql().getShortcode_media().getEdge_sidecar_to_children();
                        String str = ".png";
                        String str2 = ".mp4";
                        String str3 = "insta_";
                        String video_url;
                        if (edge_sidecar_to_children != null) {
                            List<Edge> edges = edge_sidecar_to_children.getEdges();
                            int i = 0;

                            if (ShareRepostDownload.equals("Download")) {
                                while (i < edges.size()) {

                                    if (((Edge) edges.get(i)).getNode().isIs_video()) {
                                        video_url = ((Edge) edges.get(i)).getNode().getVideo_url();


                                        String stringBuilderx = str3 +
                                                ((Edge) edges.get(i)).getNode().getId() +
                                                str2;
                                        Utils.startDownload(video_url, MainActivity.this, stringBuilderx, ShareRepostDownload);
                                    } else {
                                        video_url = ((DisplayResource) ((Edge) edges.get(i)).getNode().getDisplay_resources().get(((Edge) edges.get(i)).getNode().getDisplay_resources().size() - 1)).getSrc();


                                        String stringBuildery = str3 + ((Edge) edges.get(i)).getNode().getId() + str;
                                        Utils.startDownload(video_url, MainActivity.this, stringBuildery, ShareRepostDownload);
                                    }
                                    i++;
                                }
                            } else if (((Edge) edges.get(0)).getNode().isIs_video()) {
                                video_url = ((Edge) edges.get(0)).getNode().getVideo_url();

                                String stringBuilder21 = str3 +
                                        ((Edge) edges.get(0)).getNode().getId() +
                                        str2;
                                Utils.startDownload(video_url, MainActivity.this, stringBuilder21, ShareRepostDownload);
                            } else {
                                video_url = ((DisplayResource) ((Edge) edges.get(0)).getNode().getDisplay_resources().get(((Edge) edges.get(0)).getNode().getDisplay_resources().size() - 1)).getSrc();

                                String stringBuilder25 = str3 +
                                        ((Edge) edges.get(0)).getNode().getId() +
                                        str;
                                Utils.startDownload(video_url, MainActivity.this, stringBuilder25, ShareRepostDownload);
                            }
                        } else if (responseModel.getGraphql().getShortcode_media().isIs_video()) {
                            video_url = responseModel.getGraphql().getShortcode_media().getVideo_url();


                            String stringBuilder1 = str3 +
                                    responseModel.getGraphql().getShortcode_media().getId() +
                                    str2;
                            Utils.startDownload(video_url, MainActivity.this, stringBuilder1, ShareRepostDownload);
                        } else {
                            video_url = ((DisplayResource) responseModel.getGraphql().getShortcode_media().getDisplay_resources().get(responseModel.getGraphql().getShortcode_media().getDisplay_resources().size() - 1)).getSrc();

                            String stringBuilder9 = str3 +
                                    responseModel.getGraphql().getShortcode_media().getId() +
                                    str;
                            Utils.startDownload(video_url, MainActivity.this, stringBuilder9, ShareRepostDownload);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Utils.hideProgress();
                }
            }, true, Config.getCookie(MainActivity.this), str2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getImageFilenameFromURL(String str, String str2) {
        try {
            return new File(new URL(str).getPath().toString()).getName();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            StringBuilder stringBuilder;
            if (str2.equals("video")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(System.currentTimeMillis());
                stringBuilder.append(".mp4");
                return stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(System.currentTimeMillis());
            stringBuilder.append(".png");
            return stringBuilder.toString();
        }
    }


}
