//package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;
//
//import android.content.ClipboardManager;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.NewDashboardActivity;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.InstaStoryAdapter;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.UserListInterface;
//import com.facebook.shimmer.ShimmerFrameLayout;
//import com.google.gson.Gson;
//import com.loopj.android.http.TextHttpResponseHandler1;
//
//import cz.msebera.android.httpclient.Header;
//
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.FullStoryViewActivity;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.LoginActivity;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory.ModelMyStories;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory.ModelTrail;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;
//
//import videodownload.com.newmusically.R;
//
//import static android.app.Activity.RESULT_OK;
//import static android.content.Context.CLIPBOARD_SERVICE;
//
//
//public class StoryFragment extends Fragment implements View.OnClickListener {
//
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private static final String TAG = "StoryFragment";
//    private CardView cardinstalogin;
//    private CardView cardinstastory;
//    private NewDashboardActivity activity;
//    private ImageView instalogout;
//
//    private RecyclerView rvuserlist;
//    private ProgressBar pr_loading_bar;
//    private ClipboardManager clipBoard;
//    private TextView opentiktok;
//    private ShimmerFrameLayout shimmer;
//    InstaStoryAdapter userListAdapter;
//    AlertDialog alertDialog;
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public StoryFragment() {
//        // Required empty public constructor
//    }
//
//
//    // TODO: Rename and change types and number of parameters
//    public static StoryFragment newInstance(String param1, String param2) {
//        StoryFragment fragment = new StoryFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        activity = (NewDashboardActivity) getActivity();
//        View view = inflater.inflate(R.layout.fragment_story, container, false);
//        binddata(view);
//        initview();
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e(TAG, "onResume: called");
//        if (!PrefManager.getInstance(activity).getBoolean(PrefManager.ISINSTALOGIN)) {
//            shimmer.startShimmer();
//        } else {
//            if (shimmer.isShimmerStarted()) {
//                shimmer.stopShimmer();
//            }
//        }
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
//                            startActivity(new Intent(getContext(), FullStoryViewActivity.class).putExtra("user", String.valueOf(modelTrail.getUser().getPk())));
//
//                        }
//                    });
//                    rvuserlist.setAdapter(userListAdapter);
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
//    private void binddata(View view) {
//        cardinstalogin = view.findViewById(R.id.cardinstalogin);
//        shimmer = view.findViewById(R.id.shimmer);
//        cardinstastory = view.findViewById(R.id.cardinstastory);
//        pr_loading_bar = view.findViewById(R.id.pr_loading_bar);
//        instalogout = view.findViewById(R.id.instalogout);
//        opentiktok = view.findViewById(R.id.opentiktok);
//        opentiktok.setOnClickListener(this);
//        rvuserlist = view.findViewById(R.id.RVUserList);
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
//    public void onDestroy() {
//        super.onDestroy();
//        if (alertDialog != null) {
//            alertDialog.dismiss();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (shimmer.isShimmerStarted()) {
//            shimmer.stopShimmer();
//        }
//
//    }
//}