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
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.FullViewActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.MainActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.StoryFeedDetailActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.StoryViewAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.CommonAPI;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.FullDetailModelNew;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.PostFeedModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FileListClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.FullDetailModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.ItemModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler1;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import videodownload.com.newmusically.R;

public class FeedFragment extends Fragment implements FileListClickInterface {
    private static int LIMITSTART;
    String MaxId;
    String UserId;
    private StoryFeedDetailActivity activity;
    ArrayList<ItemModel> feedImageList;
    boolean isMoreAvailable = false;
    private int pastVisiblesItems;
    private StoryViewAdapter storyViewAdapter;
    private int totalItemCount;
    private int visibleItemCount;
    SwipeRefreshLayout swiperefresh;
    ProgressBar prLoadingBar;
    TextView tvLoadMore;
    TextView tvNoResult;
    RecyclerView rvList;

    public FeedFragment() {
        String str = "";
        this.MaxId = str;
        this.UserId = str;

    }

    public static FeedFragment newInstance(String str) {
        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("m", str);
        feedFragment.setArguments(bundle);
        return feedFragment;
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

        this.feedImageList = new ArrayList();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this.activity, 3);
        rvList.setLayoutManager(gridLayoutManager);
        rvList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.storyViewAdapter = new StoryViewAdapter(this.activity, this.feedImageList, this);
        rvList.setAdapter(this.storyViewAdapter);
        swiperefresh.setRefreshing(true);
        getPost(this.UserId);
//        callStoriesDetailApi();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LIMITSTART = 0;
                isMoreAvailable = false;
                MaxId = "";
//                callStoriesDetailApi();
            }
        });
        rvList.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }

            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (i2 > 0) {
                    FeedFragment.this.visibleItemCount = gridLayoutManager.getChildCount();
                    FeedFragment.this.totalItemCount = gridLayoutManager.getItemCount();
                    FeedFragment.this.pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();
                    if (FeedFragment.this.isMoreAvailable && FeedFragment.this.visibleItemCount + FeedFragment.this.pastVisiblesItems >= FeedFragment.this.totalItemCount) {
                        FeedFragment feedFragment = FeedFragment.this;
                        feedFragment.isMoreAvailable = false;
                        feedFragment.prLoadingBar.setVisibility(View.VISIBLE);
//                        FeedFragment.this.callStoriesDetailApi();
                    }
                }
            }
        });
    }


    private void getPost(String userid) {
        try {
            DisposableObserver<ResponseBody> disposableObserver = new DisposableObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody fullDetailModel) {
                    Log.d("storyFragment", "fullDetailModel: " + new Gson().toJson(fullDetailModel));
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("", e.toString());

                }

                @Override
                public void onComplete() {
                    Log.d("storyFragment", "fullDetailModel: ");

                }
            };

//
            CommonAPI commonAPI1 = MainActivity.commonAPI;
            commonAPI1.getUserPosts(disposableObserver, userid, Config.getCookieNew(activity));
//            ServiceHandler.getFullFeed(disposableObserver, userid, PrefManager.getInstance(activity).getString(PrefManager.COOKIES),requireActivity());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callStoriesDetailApi() {
        try {
            if (!new Utils(this.activity).isNetworkAvailable()) {
                Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
            } else {


//                ServiceHandler.get(Config.USERFEEDSTORY(this.UserId,  this.MaxId), null, new TextHttpResponseHandler1() {
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        Utils.hideProgress();
//                        swiperefresh.setRefreshing(false);
//                        prLoadingBar.setVisibility(View.GONE);
//                        if (FeedFragment.LIMITSTART == 0) {
//                            tvNoResult.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//                        swiperefresh.setRefreshing(false);
//                        prLoadingBar.setVisibility(View.GONE);
//
//                        FeedFragment.this.storyViewAdapter.notifyDataSetChanged();
//
//                        try {
//                            FullDetailModel fullDetailModel = new Gson().fromJson(responseString, FullDetailModel.class);
//                            if (fullDetailModel != null) {
//                                try {
//                                    if (fullDetailModel.getFeed().getItems().size() > 0) {
//                                        tvNoResult.setVisibility(View.GONE);
//                                        if (FeedFragment.LIMITSTART == 0) {
//                                            FeedFragment.this.feedImageList.clear();
//                                        }
//                                        FeedFragment.this.feedImageList.addAll(fullDetailModel.getFeed().getItems());
//                                        FeedFragment.LIMITSTART = FeedFragment.LIMITSTART + 1;
//                                        FeedFragment.this.isMoreAvailable = fullDetailModel.getFeed().isMore_available();
//                                        if (fullDetailModel.getFeed().getNext_max_id() != null) {
//                                            FeedFragment.this.MaxId = fullDetailModel.getFeed().getNext_max_id();
//                                        }
//                                    } else {
//                                        FeedFragment.this.isMoreAvailable = false;
//                                        if (FeedFragment.LIMITSTART == 0) {
//                                            tvNoResult.setVisibility(View.VISIBLE);
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    return;
//                                }
//                            }
//                            Utils.hideProgress();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        prLoadingBar.setVisibility(View.GONE);
//                        swiperefresh.setRefreshing(false);
//                    }
//
//                }, true, Config.getCookie(requireActivity()), Config.USERAGENT);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPosition(int i) {

        Intent intent = new Intent(this.activity, FullViewActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((ItemModel) this.feedImageList.get(i)).getCode());
        String str = "";
        stringBuilder.append(str);
        intent.putExtra("Code", stringBuilder.toString());
        intent.putExtra("Story", "1");
        intent.putExtra("StoryList", this.feedImageList);
        intent.putExtra("Position", i);
        intent.putExtra("From", str);
        this.activity.startActivity(intent);
    }
}
