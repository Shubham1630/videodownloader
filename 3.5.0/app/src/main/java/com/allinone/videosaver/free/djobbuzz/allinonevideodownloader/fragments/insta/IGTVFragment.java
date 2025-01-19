package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.insta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta.StoryFeedDetailActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.IGTVAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FileListClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.igtv.IGTVNewModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.igtv.NodeModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler1;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import videodownload.com.newmusically.R;

public class IGTVFragment extends Fragment implements FileListClickInterface {
    private static int LIMITSTART;
    String UserId;
    private StoryFeedDetailActivity activity;

    ArrayList<NodeModel> edgeModelList;
    String end_cursor;
    SwipeRefreshLayout swiperefresh;
    ProgressBar prLoadingBar;
    TextView tvLoadMore;
    TextView tvNoResult;
    RecyclerView rvList;
    private IGTVAdapter igtvAdapter;


    public IGTVFragment() {
        String str = "";
        this.end_cursor = str;
        this.UserId = str;
    }

    public static IGTVFragment newInstance(String str) {
        IGTVFragment iGTVFragment = new IGTVFragment();
        Bundle bundle = new Bundle();
        bundle.putString("m", str);
        iGTVFragment.setArguments(bundle);
        return iGTVFragment;
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
        this.edgeModelList = new ArrayList();
        this.igtvAdapter = new IGTVAdapter(this.activity, this.edgeModelList, this);
        rvList.setAdapter(this.igtvAdapter);
        callIgtvChannelVideosApi();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                end_cursor = "";
                LIMITSTART = 0;
                tvLoadMore.setVisibility(View.GONE);
                callIgtvChannelVideosApi();
                swiperefresh.setRefreshing(false);
            }
        });
        tvLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIgtvChannelVideosApi();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void callIgtvChannelVideosApi() {
        try {
            if (!new Utils(this.activity).isNetworkAvailable()) {
                Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
            } else   {
                swiperefresh.setRefreshing(true);
                ServiceHandler.get(Config.IGTVLIST(this.UserId, this.end_cursor), null, new TextHttpResponseHandler1() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Utils.hideProgress();
                        if (IGTVFragment.LIMITSTART == 0) {
                            tvNoResult.setVisibility(View.VISIBLE);
                        }
                        swiperefresh.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        swiperefresh.setRefreshing(false);
                        try {
                            JsonObject jsonObject=new Gson().fromJson(responseString,JsonObject.class);
                            if (jsonObject.get("status").getAsString().equals("ok")) {
                                JsonObject asJsonObject = jsonObject.getAsJsonObject("data").getAsJsonObject("user").getAsJsonObject("edge_felix_video_timeline");
                                asJsonObject.get("count").getAsInt();
                                JsonObject asJsonObject2 = asJsonObject.getAsJsonObject("page_info");
                                if (asJsonObject2.get("has_next_page").getAsBoolean()) {
                                    tvLoadMore.setVisibility(View.VISIBLE);
                                    IGTVFragment.this.end_cursor = asJsonObject2.get("end_cursor").getAsString();
                                } else {
                                    tvLoadMore.setVisibility(View.GONE);
                                }
                                IGTVNewModel iGTVNewModel = (IGTVNewModel) new Gson().fromJson(asJsonObject, new TypeToken<IGTVNewModel>() {
                                }.getType());
                                if (iGTVNewModel.getEdgeModel().size() > 0) {
                                    if (IGTVFragment.LIMITSTART == 0) {
                                        IGTVFragment.this.edgeModelList.clear();
                                    }
                                    tvNoResult.setVisibility(View.GONE);
                                    IGTVFragment.this.edgeModelList.addAll(iGTVNewModel.getEdgeModel());
                                    IGTVFragment.LIMITSTART = IGTVFragment.LIMITSTART + 1;
                                } else if (IGTVFragment.LIMITSTART == 0) {
                                    tvNoResult.setVisibility(View.VISIBLE);
                                }
                            } else if (IGTVFragment.LIMITSTART == 0) {
                                tvNoResult.setVisibility(View.VISIBLE);
                            }
                            IGTVFragment.this.igtvAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        swiperefresh.setRefreshing(false);
                    }
                }, true, Config.getCookie(requireActivity()), Config.USERAGENT);



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPosition(int i) {
        Intent intent = new Intent(this.activity, FullViewActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((NodeModel) this.edgeModelList.get(i)).getNodeDataModel().getShortcode());
        stringBuilder.append("");
        intent.putExtra("Code", stringBuilder.toString());
        intent.putExtra("Story", "0");
        intent.putExtra("StoryList", this.edgeModelList);
        intent.putExtra("Position", i);
        intent.putExtra("From", "IGTV");
        this.activity.startActivity(intent);
    }
}
