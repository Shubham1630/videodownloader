package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.YnsAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.YnsInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.CommonModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.yns.FormatItem;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.yns.YnsModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.VideoDownload;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler1;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import videodownload.com.newmusically.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler.getyns;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = "BottomSheetDialog";
    public String mylink = "";
    public VideoDownload.MusicallyDelegate musicallyDelegate;
    public ImageView iv_thumb;
    public View scrollView;
    public ProgressBar progressloading;
    public View bottomSheet;
    public View lltoplayout;
    public TextView txt_videotitle;
    public TextView txt_videoduration;
    public ArrayList<FormatItem> formatItemsforvideos;
    public ArrayList<FormatItem> formatItemsforaudios;
    public RecyclerView rv_videosonly;
    public RecyclerView rv_audiosonly;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            bottomSheet.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bottomsheetbg));
        }
        View view = getView();
        if (view != null) {
            view.post(() -> {
                View parent = (View) view.getParent();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
                //   bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
                bottomSheetBehavior.setDraggable(true);
                ((View) bottomSheet.getParent()).setBackgroundColor(Color.TRANSPARENT);

            });
        }

    }

    public BottomSheetDialog(String s, VideoDownload.MusicallyDelegate musicallyDelegate) {
        this.mylink = s;
        this.musicallyDelegate = musicallyDelegate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,
                container, false);

        bindme(v);
        Log.e(TAG, "onCreateView: mylink" + mylink);
        loadfromurl(mylink);
        return v;
    }

    private void bindme(View v) {
        scrollView = v.findViewById(R.id.scrollView);
        iv_thumb = v.findViewById(R.id.iv_thumb);
        txt_videoduration = v.findViewById(R.id.txt_videoduration);
        txt_videotitle = v.findViewById(R.id.txt_videotitle);
        progressloading = v.findViewById(R.id.progressloading);
        rv_videosonly = v.findViewById(R.id.rv_videosonly);
        rv_audiosonly = v.findViewById(R.id.rv_audiosonly);
        lltoplayout = v.findViewById(R.id.lltoplayout);
        progressloading.setVisibility(VISIBLE);
        scrollView.setVisibility(View.GONE);
    }

    private void loadfromurl(String mylink)   {
        try {
            ServiceHandler.get(getyns + URLEncoder.encode(mylink, "UTF-8"), null, new TextHttpResponseHandler1() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, "onFailure: " + responseString);
                    if(musicallyDelegate!=null)
                        musicallyDelegate.OnFailure("Invalid url or Copyrighted video");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    showscrollview();

                    progressloading.setVisibility(View.GONE);
                    try {
                        if (responseString != null && !responseString.isEmpty()) {

                            YnsModel ynsModel = new Gson().fromJson(responseString, YnsModel.class);
                            if (ynsModel != null && ynsModel.getFormat() != null && !ynsModel.getFormat().isEmpty()) {
                                Glide.with(requireContext()).load(ynsModel.getThumbnails().replace("default.jpg", "hqdefault.jpg")).into(iv_thumb);
                                txt_videotitle.setText(String.format("%s", ynsModel.getTitle()));
                                txt_videotitle.requestFocus();
                                txt_videoduration.setText(String.format("Duration : %s", Utility.formatSeconds(Integer.parseInt(ynsModel.getDuration()))));
                                formatItemsforvideos = new ArrayList<>();
                                formatItemsforaudios = new ArrayList<>();
                                for (FormatItem formatItem : ynsModel.getFormat()) {

                                    /* first we find all videos here */
                                    if (formatItem.getHeight() != null
                                            && formatItem.getWidth() != null
                                            && formatItem.getExt() != null
                                            && formatItem.getSize() != null) {
                                        formatItemsforvideos.add(formatItem);
                                    } else {
                                        if (formatItem.getExt() != null && formatItem.getExt().contains("m4a")) {
                                            formatItemsforaudios.add(formatItem);
                                        }
                                    }


                                }

                                handleadapter(formatItemsforvideos, formatItemsforaudios, ynsModel);
                            }
                        } else {

                            if(musicallyDelegate!=null)
                                musicallyDelegate.OnFailure("Invalid url or Copyrighted video");
                            // something went wrong

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(musicallyDelegate!=null)
                            musicallyDelegate.OnFailure("Invalid url or Copyrighted video");
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if(musicallyDelegate!=null)
                musicallyDelegate.OnFailure("Invalid url or Copyrighted video");
        }
    }

    private void showscrollview() {
        ViewPropertyAnimator animate;
        scrollView.setAlpha(1.0f);
        scrollView.setVisibility(INVISIBLE);
        for (final View view : getAllChildren(scrollView)) {
            if (!(view == null || (animate = view.animate()) == null)) {
                ViewPropertyAnimator alpha = animate.alpha(1.0f);
                if (alpha != null) {
                    ViewPropertyAnimator duration = alpha.setDuration(200);
                    if (duration != null) {
                        duration.withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                view.setVisibility(VISIBLE);
                                scrollView.setVisibility(VISIBLE);
                                lltoplayout.requestFocus();
                            }
                        });
                    }
                }
            }
        }
    }

    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }
        ArrayList<View> result = new ArrayList<View>();
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            result.addAll(getAllChildren(child));
        }
        return result;
    }

    private void handleadapter(ArrayList<FormatItem> itemsforvideos, ArrayList<FormatItem> formatItemsforaudios, YnsModel ynsModel) {
        if (itemsforvideos != null && !itemsforvideos.isEmpty()) {
            rv_videosonly.setLayoutManager(new LinearLayoutManager(requireContext()));
            YnsAdapter ynsAdapter = new YnsAdapter(requireContext(), itemsforvideos, new YnsInterface() {
                @Override
                public void userListClick(int position, FormatItem modelTrail) {
                    Log.e(TAG, "userListClick: called");
                    CommonModel igtvModel = new CommonModel();
                    igtvModel.setImagePath(ynsModel.getThumbnails().replace("default.jpg", "hqdefault.jpg"));
                    igtvModel.setVideoPath(modelTrail.getUrl());
                    igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                    String stringBuilder2 = "" + ".mp4";

                    igtvModel.setVideoUniquePath(stringBuilder2);
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnSuccess(igtvModel);
                    }
                }
            });
            //  rv_videosonly.setNestedScrollingEnabled(true);
            rv_videosonly.setAdapter(ynsAdapter);
        }
        if (formatItemsforaudios != null && !formatItemsforaudios.isEmpty()) {
            rv_audiosonly.setLayoutManager(new LinearLayoutManager(requireContext()));
            YnsAdapter ynsAdapter = new YnsAdapter(requireContext(), formatItemsforaudios, new YnsInterface() {
                @Override
                public void userListClick(int position, FormatItem modelTrail) {
                    Log.e(TAG, "userListClick: called");
                    CommonModel igtvModel = new CommonModel();
                    igtvModel.setImagePath(ynsModel.getThumbnails().replace("default.jpg", "hqdefault.jpg"));
                    igtvModel.setVideoPath(modelTrail.getUrl());
                    igtvModel.setTitle(String.valueOf(System.currentTimeMillis()));
                    String stringBuilder2 = "" + ".mp4";

                    igtvModel.setVideoUniquePath(stringBuilder2);
                    if (musicallyDelegate != null) {
                        musicallyDelegate.OnSuccess(igtvModel);
                    }
                }
            });
            rv_audiosonly.setNestedScrollingEnabled(true);
            rv_audiosonly.setAdapter(ynsAdapter);
        }


    }
}