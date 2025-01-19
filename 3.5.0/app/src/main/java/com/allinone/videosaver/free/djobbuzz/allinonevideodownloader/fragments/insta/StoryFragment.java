package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.insta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.FullViewActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.MainActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.StoryFeedDetailActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.StoryViewAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.CommonAPI;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.FullDetailModelNew;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FileListClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.FullDetailModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.ReelFeedModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.ItemModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler1;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import videodownload.com.newmusically.R;

public class StoryFragment extends Fragment implements FileListClickInterface {
    String UserId = "";
    private StoryFeedDetailActivity activity;
    ArrayList<ItemModel> storyImageList;
    SwipeRefreshLayout swiperefresh;
    ProgressBar prLoadingBar;
    TextView tvLoadMore;
    TextView tvNoResult;
    public String UserAgent = "\"Mozilla/5.0 (Linux; Android 13; Windows NT 10.0; Win64; x64; rv:109.0;Mobile; LG-M255; rv:113.0; SM-A205U; LM-Q720; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.5672.131 Mobile Safari/537.36 Instagram 282.0.0.22.119\"";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();



    RecyclerView rvList;
    private StoryViewAdapter storyViewAdapter;

    public static StoryFragment newInstance(String str) {
        StoryFragment storyFragment = new StoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("m", str);
        storyFragment.setArguments(bundle);
        return storyFragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (StoryFeedDetailActivity) context;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.UserId = getArguments().getString("m");
        }
    }

    public void onResume() {
        super.onResume();
        this.activity = (StoryFeedDetailActivity) getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View v = layoutInflater.inflate(R.layout.fragment_insta_story,
                viewGroup, false);


        initViews(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void initViews(View v) {
        prLoadingBar = v.findViewById(R.id.pr_loading_bar);
        swiperefresh = v.findViewById(R.id.swiperefresh);
        tvLoadMore = v.findViewById(R.id.tvLoadMore);
        rvList = v.findViewById(R.id.rvList);
        tvNoResult = v.findViewById(R.id.tv_NoResult);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.activity, 3);
        rvList.setLayoutManager(gridLayoutManager);
        rvList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getStories(UserId);
//        callStoriesDetailApi();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStories(UserId);
//                callStoriesDetailApi();
                swiperefresh.setRefreshing(false);
            }
        });

    }


//    private void callStoriesDetailApi() {
//        try {
//            if (!new Utils(this.activity).isNetworkAvailable()) {
//                Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
//            } else {
//                swiperefresh.setRefreshing(true);
//                ServiceHandler.get(Config.USERFEEDSTORY(this.UserId, ""), null, new TextHttpResponseHandler1() {
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        Utils.hideProgress();
//                        swiperefresh.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                        swiperefresh.setRefreshing(false);
//
//
//                        try {
//                            FullDetailModel fullDetailModel = new Gson().fromJson(responseString, FullDetailModel.class);
//                            if (fullDetailModel.getReel_feed().getItems().size() > 0) {
//                                tvNoResult.setVisibility(View.GONE);
//                                StoryFragment.this.storyImageList = fullDetailModel.getReel_feed();
//                                StoryFragment.this.storyViewAdapter = new StoryViewAdapter(StoryFragment.this.activity, StoryFragment.this.storyImageList, StoryFragment.this);
//                                rvList.setAdapter(StoryFragment.this.storyViewAdapter);
//                                return;
//                            }
//                            tvNoResult.setVisibility(View.VISIBLE);
//                            swiperefresh.setRefreshing(false);
//                            Utils.hideProgress();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, true, PrefManager.getInstance(activity).getString(PrefManager.COOKIES), UserAgent);
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear(); // Clear disposables when the view is destroyed
    }
    private void getStories(String userid) {
        try {
            DisposableObserver<FullDetailModelNew> disposableObserver = new DisposableObserver<FullDetailModelNew>() {
                @Override
                public void onNext(FullDetailModelNew fullDetailModel) {
                    Log.d("storyFragment", "fullDetailModel: " + new Gson().toJson(fullDetailModel));
                    try {

                        if (fullDetailModel.getReels_media().size() > 0) {
                            tvNoResult.setVisibility(View.GONE);
                            StoryFragment.this.storyImageList = fullDetailModel.getReels_media().get(0).getItems();
                            StoryFragment.this.storyViewAdapter = new StoryViewAdapter(StoryFragment.this.activity, StoryFragment.this.storyImageList, StoryFragment.this);
                            rvList.setAdapter(StoryFragment.this.storyViewAdapter);
                            return;
                        }
                        tvNoResult.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        Utils.hideProgress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("",e.toString());

                }

                @Override
                public void onComplete() {
                    Log.d("storyFragment", "fullDetailModel: ");

                }
            };

            compositeDisposable.add(disposableObserver);
            CommonAPI commonAPI1 = MainActivity.commonAPI;
            commonAPI1.getFullFeed(disposableObserver, userid, Config.getCookieNew(activity));
//            ServiceHandler.getFullFeed(disposableObserver, userid, PrefManager.getInstance(activity).getString(PrefManager.COOKIES),requireActivity());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void getPosition(int i) {

        Intent intent = new Intent(this.activity, FullViewActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((ItemModel) this.storyImageList.get(i)).getCode());
        String str = "";
        stringBuilder.append(str);
        intent.putExtra("Code", stringBuilder.toString());
        intent.putExtra("Story", "1");
        intent.putExtra("StoryList", this.storyImageList);
        intent.putExtra("Position", i);
        intent.putExtra("From", str);
        this.activity.startActivity(intent);
    }
}
