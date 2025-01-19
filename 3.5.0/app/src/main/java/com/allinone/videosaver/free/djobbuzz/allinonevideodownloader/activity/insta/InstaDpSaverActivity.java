//package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;
//
//import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.setToast;
//import static com.allinone.videosaver.free.videodownloader.statussaver.utility.Utility.videoUrl;
//
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.allinone.videosaver.free.videodownloader.statussaver.adapter.insta.FollowingAdapter;

//import com.allinone.videosaver.free.videodownloader.statussaver.app.App;
//import com.allinone.videosaver.free.videodownloader.statussaver.helper.ServiceHandler;
//import com.allinone.videosaver.free.videodownloader.statussaver.interfaces.GoEditTextListener;
//import com.allinone.videosaver.free.videodownloader.statussaver.interfaces.insta.FavUserClickInterface;
//import com.allinone.videosaver.free.videodownloader.statussaver.interfaces.insta.FollowingUserClickInterface;
//import com.allinone.videosaver.free.videodownloader.statussaver.model.GoEditText;
//import com.allinone.videosaver.free.videodownloader.statussaver.model.instaDp.InstaDpModel;
//import com.allinone.videosaver.free.videodownloader.statussaver.model.instagram.ModelResponse;
//import com.allinone.videosaver.free.videodownloader.statussaver.model.instaio.story.UserModel;
//import com.allinone.videosaver.free.videodownloader.statussaver.utility.Utils;
//import com.allinone.videosaver.free.videodownloader.statussaver.utility.insta.Config;
//import com.bumptech.glide.Glide;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.loopj.android.http.TextHttpResponseHandler1;
//
//import java.lang.reflect.Type;
//import java.net.URI;
//import java.util.ArrayList;
//
//import cz.msebera.android.httpclient.Header;
//import videodownload.com.newmusically.R;
//
//public class InstaDpSaverActivity extends AppCompatActivity implements FollowingUserClickInterface, FavUserClickInterface {
//    private static final String TAG = "InstaDpSaverActivity";
//    InstaDpSaverActivity activity;
//
//    private FollowingAdapter followingAdapter;
//    RelativeLayout RLEdittextLayout;
//    GoEditText etUrl;
//    ImageView imSearch;
//    ImageView image;
//    ImageView im_back;
//    ImageView ivUser;
//    TextView tv_NoResult;
//    TextView txtSave;
//    TextView txtPaste;
//    TextView tv_HeaderTitle;
//    RecyclerView rvUserSearchLis;

//    View rlSaver;
//
//    private String cliptext = "";
//    private String currenturls = "";
//    private ClipboardManager myClipboard;
//    private ProgressBar progressLoader;
//    private String userId = "";
//
//
//    ArrayList<UserModel> userSearchList;
//
//    protected void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        setContentView(R.layout.activity_insta_dp_save);
//        this.activity = this;
//        initView();


//    }
//
//    private void initView() {
//        myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//        etUrl = findViewById(R.id.eturl);
//        progressLoader = findViewById(R.id.progressLoader);
//        txtPaste = findViewById(R.id.txtPaste);
//        imSearch = findViewById(R.id.imSearch);
//        image = findViewById(R.id.image);
//        tv_NoResult = findViewById(R.id.tv_NoResult);
//        txtSave = findViewById(R.id.txtSave);
//        rvUserSearchLis = findViewById(R.id.rvUserSearchLis);
//        rlSaver = findViewById(R.id.rlSaver);
//        rlSaver.setVisibility(View.GONE);
//        im_back = findViewById(R.id.im_back);
//        ivUser = findViewById(R.id.ivUser);
//        tv_HeaderTitle = findViewById(R.id.tv_HeaderTitle);
//        tv_HeaderTitle.setText("Instagram DP Saver");
//        tv_HeaderTitle.setVisibility(View.VISIBLE);
//
//        this.userSearchList = new ArrayList();
//        im_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.activity);
//        rvUserSearchLis.setLayoutManager(linearLayoutManager);
//        rvUserSearchLis.setNestedScrollingEnabled(false);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//
//        etUrl.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable editable) {
//            }
//
//            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            }
//
//            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                if (charSequence.length() <= 0) {
//                    setdefaultmode();
//                    return;
//                }
//                image.setVisibility(View.VISIBLE);
//                txtPaste.setVisibility(View.GONE);
//            }
//        });
//        etUrl.addListener(new GoEditTextListener() {
//            public void onUpdate() {
//                if (!(myClipboard == null || myClipboard.getPrimaryClip() == null)) {
//                    ClipData primaryClip = myClipboard.getPrimaryClip();
//                    if (primaryClip != null && primaryClip.getItemCount() > 0) {
//                        Context context;
//                        CharSequence charSequence;
//                        ClipData.Item itemAt = primaryClip.getItemAt(0);
//                        cliptext = itemAt.getText().toString();
//                        currenturls = videoUrl(cliptext);
//                        if (TextUtils.isEmpty(currenturls)) {
//                            context = activity;
//                            charSequence = activity.getResources().getString(R.string.urlnotvalid);
//                        } else {
//                            Log.e("urlpath", currenturls);
//                            etUrl.setText(currenturls);
//                            callNewApi(currenturls);
//                            context = activity;
//                            charSequence = activity.getResources().getString(R.string.tapdownload);
//
//                        }
//                        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//        etUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i != 3) {
//                    return false;
//                }
//                try {
//                    ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//                } catch (Exception unused) {
//                    if (TextUtils.isEmpty(etUrl.getText().toString()) || TextUtils.isEmpty(currenturls)) {
//                        Toast.makeText(activity, activity.getResources().getString(R.string.urlnotvalid), Toast.LENGTH_SHORT).show();
//                    } else {
//                        callNewApi(currenturls);
//                    }
//                    return true;
//                }
//                return true;
//            }
//        });
//
//
//        txtPaste.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handlePaste();
//            }
//        });
//    }
//
//    private void setdefaultmode() {
//
//        txtPaste.setVisibility(View.VISIBLE);
//        image.setVisibility(View.GONE);
//        rlSaver.setVisibility(View.GONE);
//
//
//    }
//
//    private void handlePaste() {
//        if (!(myClipboard == null || myClipboard.getPrimaryClip() == null)) {
//            Log.e(TAG, "handlePaste: called 2");
//            ClipData primaryClip = myClipboard.getPrimaryClip();
//            if (primaryClip != null && primaryClip.getItemCount() > 0) {
//                Log.e(TAG, "handlePaste: called 3");
//                ClipData.Item itemAt = primaryClip.getItemAt(0);
//                cliptext = itemAt.getText().toString();
//                currenturls = videoUrl(cliptext);
//                handleMultipleUrls(true);
//            } else {
//                Log.e(TAG, "handlePaste: called 4");
//                Log.e(TAG, "handlePaste: all is blank");
//            }
//        }
//    }
//
//    private void handleMultipleUrls(boolean b) {
//        if (currenturls != null && !TextUtils.isEmpty(currenturls) && !etUrl.getText().toString().trim().equals(currenturls)) {
//            etUrl.setText(currenturls);
//            callNewApi(currenturls);
//        }
//    }
//
//    private String getUrlWithoutParameters(String url) {
//        try {
//            URI uri = new URI(url);
//            return new URI(uri.getScheme(),
//                    uri.getAuthority(),
//                    uri.getPath(),
//
//                    uri.getFragment()).toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            setToast(App.getContext(), App.getContext().getResources().getString(R.string.enter_valid_url));
//            return "";
//        }
//    }
//
//    private void callNewApi(String obj) {
//        if (obj == null || obj.isEmpty()) {
//            Toast.makeText(InstaDpSaverActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        progressLoader.setVisibility(View.VISIBLE);
//        rlSaver.setVisibility(View.GONE);
//        Log.e(TAG, "callNewApi: ..1");
//        String UrlWithoutQP = getUrlWithoutParameters(obj);
//        UrlWithoutQP = UrlWithoutQP + "?__a=1";
//
//        String finalUrlWithoutQP = UrlWithoutQP;
//        Log.e(TAG, "callNewApi: " + finalUrlWithoutQP);
//        ServiceHandler.get(finalUrlWithoutQP, null, new TextHttpResponseHandler1() {
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.e(TAG, "callNewApi: ..failur");
//                progressLoader.setVisibility(View.GONE);
//                ivUser.setVisibility(View.GONE);
//                rlSaver.setVisibility(View.GONE);
//                Toast.makeText(InstaDpSaverActivity.this, "User not found or something went wrong.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                Log.e(TAG, "callNewApi: ..2" + responseString);
//                progressLoader.setVisibility(View.GONE);
//                Type listType = new TypeToken<ModelResponse>() {
//                }.getType();
//                ModelResponse modelResponse = new Gson().fromJson(responseString.toString(), listType);
//                if (modelResponse != null) {
//                    Log.e(TAG, "callNewApi: ..3");
//                    if (modelResponse.getModelGraphql() != null) {
//
//                        userId = modelResponse.getModelGraphql().getUser().getId();
//                        callDirect();
//                        // Glide.with(InstaDpSaverActivity.this).load(modelResponse.getModelGraphql().getUser().getProfilePicUrlHd()).thumbnail(0.2f).into(ivUser);
//                        Log.e(TAG, "callNewApi: ..4");
//                        Log.e(TAG, "onSuccess: HD" + modelResponse.getModelGraphql().getUser().getProfilePicUrlHd());
//                    } else {
//                        ivUser.setVisibility(View.GONE);
//                        rlSaver.setVisibility(View.GONE);
//                        Toast.makeText(InstaDpSaverActivity.this, "User not found or something went wrong.", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    rlSaver.setVisibility(View.GONE);
//                    Toast.makeText(InstaDpSaverActivity.this, "User not found or something went wrong.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, true, Config.getCookie(InstaDpSaverActivity.this), Config.USERAGENT);
//    }
//
//    private void callDirect() {
//        progressLoader.setVisibility(View.VISIBLE);
//        String myUrl = "https://i.instagram.com/api/v1/users/" + userId + "/info/";
//        ServiceHandler.get(myUrl, null, new TextHttpResponseHandler1() {
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                progressLoader.setVisibility(View.GONE);
//                ivUser.setVisibility(View.GONE);
//                rlSaver.setVisibility(View.GONE);
//                Toast.makeText(InstaDpSaverActivity.this, "User not found or something went wrong.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                try {
//                    progressLoader.setVisibility(View.GONE);
//                    Type listType = new TypeToken<InstaDpModel>() {
//                    }.getType();
//                    InstaDpModel modelResponse = new Gson().fromJson(responseString, listType);
//                    Glide.with(InstaDpSaverActivity.this).load(modelResponse.getUser().getHdProfilePicUrlInfo().getUrl()).thumbnail(0.2f).into(ivUser);
//                    ivUser.setVisibility(View.VISIBLE);
//                    rlSaver.setVisibility(View.VISIBLE);
//                    txtSave.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            DpDownload(modelResponse.getUser().getHdProfilePicUrlInfo().getUrl(), String.valueOf(System.currentTimeMillis()));
//                        }
//                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(InstaDpSaverActivity.this, "User not found or something went wrong.", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }, true, Config.getCookie(InstaDpSaverActivity.this), Config.USERAGENT);
//    }
//
//
//    public void FavClicked(int i, UserModel userModel) {
//
//    }
//
//    public void UserClick(int i, UserModel userModel) {
//        Log.e(TAG, "UserClick: " + userModel.getProfile_pic_url());

//        Intent intent = new Intent(this.activity, StoryFeedDetailActivity.class);
//        String str = "";
//        intent.putExtra("UserId", userModel.getPk() + str);
//        intent.putExtra("Name", userModel.getFull_name() + str);
//        intent.putExtra("UserName", userModel.getUsername() + str);
//        intent.putExtra("ProfileImage", userModel.getProfile_pic_url() + str);
//        startActivity(intent);
//    }
//
//    @Override
//    public void DpDownload(int i, UserModel userModel) {
//        String str = "insta_";
//        String videoUrl;
//        StringBuilder stringBuilder;
//        videoUrl = userModel.getProfile_pic_url();
//        stringBuilder = new StringBuilder();
//        stringBuilder.append(str);
//        stringBuilder.append(userModel.getProfile_pic_id());
//        stringBuilder.append(".jpg");
//        Utils.startDownload(videoUrl, this.activity, stringBuilder.toString(), "Download");
//    }
//
//    public void DpDownload(String url, String num) {
//        String str = "insta_";
//        String videoUrl;
//        StringBuilder stringBuilder;
//
//        stringBuilder = new StringBuilder();
//        stringBuilder.append(str);
//        stringBuilder.append(num);
//        stringBuilder.append(".jpg");
//        Utils.startDownload(url, this.activity, stringBuilder.toString(), "Download");
//    }
//}
