package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.FollowingAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FavUserClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FollowingUserClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.SearchUserModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.UserModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler1;

import cz.msebera.android.httpclient.Header;
import videodownload.com.newmusically.R;

import java.util.ArrayList;

public class SearchUserActivity extends AppCompatActivity implements FollowingUserClickInterface, FavUserClickInterface {
    SearchUserActivity activity;

    private FollowingAdapter followingAdapter;
    RelativeLayout RLEdittextLayout;
    EditText etSearchText;
    ImageView imSearch;
    ImageView im_back;
    TextView tv_NoResult;
    RecyclerView rvUserSearchLis;



    ArrayList<UserModel> userSearchList;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_user);
        this.activity = this;
        initView();

    }

    private void initView() {
        etSearchText = findViewById(R.id.etSearchText);
        imSearch = findViewById(R.id.imSearch);
        tv_NoResult = findViewById(R.id.tv_NoResult);
        rvUserSearchLis = findViewById(R.id.rvUserSearchLis);
        im_back = findViewById(R.id.im_back);

        this.userSearchList = new ArrayList();
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.activity);
        rvUserSearchLis.setLayoutManager(linearLayoutManager);
        rvUserSearchLis.setNestedScrollingEnabled(false);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String obj = etSearchText.getText().toString();
                if (obj.equals("")) {
                    Utils.setToast(SearchUserActivity.this, getResources().getString(R.string.please_enter_user_name));
                    return;
                }
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                callSearchUser(obj);
            }
        });
        etSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                if (i != 3) {
                    return false;
                }
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                String obj = etSearchText.getText().toString();
                if (obj.equals("")) {

                    Utils.setToast(SearchUserActivity.this, getResources().getString(R.string.please_enter_user_name));
                } else {
                    callSearchUser(obj);
                }
                return true;
            }
        });
    }


    private void callSearchUser(String str) {
        this.userSearchList.clear();
        try {
            if (!new Utils(this.activity).isNetworkAvailable()) {
                Utils.setToast(this.activity, this.activity.getResources().getString(R.string.no_internet_connection));
            } else {
                Utils.showProgress(this.activity);

                ServiceHandler.get(Config.USERSEARCH +
                        str, null, new TextHttpResponseHandler1() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Utils.hideProgress();
                        try {
                            SearchUserModel searchUserModel = new Gson().fromJson(responseString, SearchUserModel.class);
                            if (searchUserModel != null) {
                                try {
                                    if (searchUserModel.getUserList().size() > 0) {
                                        SearchUserActivity.this.userSearchList = searchUserModel.getUserList();
                                        tv_NoResult.setVisibility(View.GONE);
                                        SearchUserActivity.this.followingAdapter = new FollowingAdapter(SearchUserActivity.this.activity, searchUserModel.getUserList(), SearchUserActivity.this.activity, SearchUserActivity.this.activity,false);
                                        rvUserSearchLis.setAdapter(SearchUserActivity.this.followingAdapter);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (followingAdapter != null) {
                            followingAdapter.notifyDataSetChanged();
                        }


                    }
                }, true, Config.getCookie(SearchUserActivity.this), Config.USERAGENT);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FavClicked(int i, UserModel userModel) {
        /*if (this.databaseHandler.checkFavUserData(String.valueOf(userModel.getPk()))) {
            this.databaseHandler.deleteFavInstaUser(String.valueOf(userModel.getPk()));
            ((UserModel) this.userSearchList.get(i)).setIsFav(0);
            this.followingAdapter.notifyDataSetChanged();
            return;
        }
        this.databaseHandler.insertFavInstaImage(String.valueOf(userModel.getPk()), userModel.getUsername(), userModel.getFull_name(), userModel.getProfile_pic_id(), userModel.getProfile_pic_url());
        ((UserModel) this.userSearchList.get(i)).setIsFav(1);
        this.followingAdapter.notifyDataSetChanged();*/
    }

    public void UserClick(int i, UserModel userModel) {

        Intent intent = new Intent(this.activity, StoryFeedDetailActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userModel.getPk());
        String str = "";
        stringBuilder.append(str);
        intent.putExtra("UserId", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(userModel.getFull_name());
        stringBuilder.append(str);
        intent.putExtra("Name", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(userModel.getUsername());
        stringBuilder.append(str);
        intent.putExtra("UserName", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(userModel.getProfile_pic_url());
        stringBuilder.append(str);
        intent.putExtra("ProfileImage", stringBuilder.toString());
        startActivity(intent);
    }

    @Override
    public void DpDownload(int i, UserModel userModel) {

    }
}
