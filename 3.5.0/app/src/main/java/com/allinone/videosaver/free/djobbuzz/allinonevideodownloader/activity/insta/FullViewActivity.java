package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.insta;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta.ViewPageAdapter;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper.ServiceHandler;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.FullViewModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.DisplayResource;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.Edge;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.EdgeSidecarToChildren;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.graph.ResponseModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.CandidatesModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.ItemModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.VideoVersionModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline.TimelineItemModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler1;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import videodownload.com.newmusically.R;

public class FullViewActivity extends AppCompatActivity {
    ArrayList<ItemModel> IntentStoryImageList;
    private int Position = 0;
    String ShareRepostDownload;
    String Story;
    String UrlCode;
    private FullViewActivity activity;
    ImageView[] dots;
    int dotscount;
    private ArrayList<FullViewModel> imageList;
    private String intentFrom;
    String[] permissions = new String[]{/*"android.permission.WRITE_EXTERNAL_STORAGE",*/ "android.permission.READ_EXTERNAL_STORAGE"};
    ViewPageAdapter viewPageAdapter;
    ViewPager vpView;
    ImageView im_close;
    LinearLayout SliderDots;
    LinearLayout lnr_footer;
    FloatingActionButton imDownload;
    FloatingActionButton imShare;

    TimelineItemModel timelineItemModel;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_full_view);
        this.activity = this;
        String str = "";
        this.ShareRepostDownload = str;
        this.intentFrom = str;
        this.Story = getIntent().getStringExtra("Story");
        this.timelineItemModel = (TimelineItemModel) getIntent().getSerializableExtra("TimelineItemModel");
        if (this.Story.equals("1")) {
            this.IntentStoryImageList = (ArrayList) getIntent().getSerializableExtra("StoryList");
        }
        this.Position = getIntent().getIntExtra("Position", 0);
        this.UrlCode = getIntent().getStringExtra("Code");
        this.intentFrom = getIntent().getStringExtra("From");

        initViews();
    }

    public void initViews() {
        vpView = findViewById(R.id.vp_view);
        im_close = findViewById(R.id.im_close);
        SliderDots = findViewById(R.id.SliderDots);
        lnr_footer = findViewById(R.id.lnr_footer);
        imDownload = findViewById(R.id.imDownload);
        imShare = findViewById(R.id.imShare);

        this.imageList = new ArrayList();
        if (this.Story.equals("1")) {
            for (int i = 0; i < this.IntentStoryImageList.size(); i++) {
                FullViewModel fullViewModel = new FullViewModel();
                if (((ItemModel) this.IntentStoryImageList.get(i)).getMedia_type() == 2) {
                    fullViewModel.setVideo(true);
                    fullViewModel.setVideoUrl(((VideoVersionModel) ((ItemModel) this.IntentStoryImageList.get(i)).getVideo_versions().get(0)).getUrl());
                } else {
                    fullViewModel.setVideo(false);
                    fullViewModel.setVideoUrl("");
                }
                fullViewModel.setImageUrl(((CandidatesModel) ((ItemModel) this.IntentStoryImageList.get(i)).getImage_versions2().getCandidates().get(0)).getUrl());
                fullViewModel.setThumbnail(((CandidatesModel) ((ItemModel) this.IntentStoryImageList.get(i)).getImage_versions2().getCandidates().get(0)).getUrl());
                fullViewModel.setId(((ItemModel) this.IntentStoryImageList.get(i)).getId());
                this.imageList.add(fullViewModel);
            }
            this.viewPageAdapter = new ViewPageAdapter(this.imageList, this.activity);
            vpView.setAdapter(this.viewPageAdapter);
            vpView.setCurrentItem(this.Position);
            vpView.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrollStateChanged(int i) {
                }

                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    Position = i;
                }
            });
        } else if (this.Story.equals("2")) {

            FullViewModel fullViewModel = new FullViewModel();
            if (this.timelineItemModel.getMedia_type() == 2) {
                fullViewModel.setVideo(true);
                fullViewModel.setVideoUrl((this.timelineItemModel.getVideo_versions().get(0)).getUrl());
            } else {
                fullViewModel.setVideo(false);
                fullViewModel.setVideoUrl("");
            }
            fullViewModel.setImageUrl(((this.timelineItemModel.getImage_versions2().getCandidates().get(0)).getUrl()));
            fullViewModel.setThumbnail(((CandidatesModel) (this.timelineItemModel).getImage_versions2().getCandidates().get(0)).getUrl());
            fullViewModel.setId((this.timelineItemModel).getId());
            this.imageList.add(fullViewModel);

            this.viewPageAdapter = new ViewPageAdapter(this.imageList, this.activity);
            vpView.setAdapter(this.viewPageAdapter);
            vpView.setCurrentItem(this.Position);
            vpView.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrollStateChanged(int i) {
                }

                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    Position = i;
                }
            });

        } else {
            callFetchImages();
        }
        imDownload.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShareRepostDownload = "Download";
              /*  if (VERSION.SDK_INT >= 23) {
                    checkPermissions(100);
                } else {
                    Download();
                }*/
                Download();
            }
        });
        imShare.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShareRepostDownload = "Share";
               /* if (VERSION.SDK_INT >= 23) {
                    checkPermissions(100);
                } else {
                    Download();
                }*/
                Download();
            }
        });

        im_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    protected void onResume() {
        super.onResume();
        this.activity = this;
    }

    private void callFetchImages() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Config.POSTDETAILLINK);
        stringBuilder.append(this.UrlCode);
        String str = "?__a=1";
        stringBuilder.append(str);
        String stringBuilder2 = stringBuilder.toString();
        if (this.intentFrom.equalsIgnoreCase("IGTV")) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Config.POSTDETAILLINKIGTV);
            stringBuilder.append(this.UrlCode);
            stringBuilder.append(str);
            stringBuilder2 = stringBuilder.toString();
        }
        try {

            Utils.showProgress(this.activity);

            String str2 = Config.USERAGENT;
            if (stringBuilder2.contains("/tv/")) {
                str2 = Config.USERAGENTIGTV;
            }
            ServiceHandler.get(stringBuilder2, null, new TextHttpResponseHandler1() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Utils.hideProgress();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    Utils.hideProgress();
                    try {
                        EdgeSidecarToChildren edge_sidecar_to_children = null;
                        ResponseModel responseModel = (ResponseModel) new Gson().fromJson(responseString, new TypeToken<ResponseModel>() {
                        }.getType());
                        if (responseModel.getGraphql() != null && responseModel.getGraphql().getShortcode_media() != null) {
                            edge_sidecar_to_children = responseModel.getGraphql().getShortcode_media().getEdge_sidecar_to_children();
                        }
                        String str = "";
                        if (edge_sidecar_to_children != null) {
                            List edges = edge_sidecar_to_children.getEdges();
                            for (int i = 0; i < edges.size(); i++) {
                                FullViewModel fullViewModel;
                                String video_url;
                                if (((Edge) edges.get(i)).getNode().isIs_video()) {
                                    video_url = ((Edge) edges.get(i)).getNode().getVideo_url();
                                    String src = ((DisplayResource) ((Edge) edges.get(i)).getNode().getDisplay_resources().get(((Edge) edges.get(i)).getNode().getDisplay_resources().size() - 1)).getSrc();
                                    fullViewModel = new FullViewModel();
                                    fullViewModel.setVideo(true);
                                    fullViewModel.setVideoUrl(video_url);
                                    fullViewModel.setImageUrl(src);
                                    fullViewModel.setThumbnail(src);
                                    fullViewModel.setId(String.valueOf(((Edge) edges.get(i)).getNode().getId()));
                                } else {
                                    video_url = ((DisplayResource) ((Edge) edges.get(i)).getNode().getDisplay_resources().get(((Edge) edges.get(i)).getNode().getDisplay_resources().size() - 1)).getSrc();
                                    fullViewModel = new FullViewModel();
                                    fullViewModel.setVideoUrl(str);
                                    fullViewModel.setVideo(false);
                                    fullViewModel.setImageUrl(video_url);
                                    fullViewModel.setThumbnail(video_url);
                                    fullViewModel.setId(String.valueOf(((Edge) edges.get(i)).getNode().getId()));
                                }
                                imageList.add(fullViewModel);
                            }
                            viewPageAdapter = new ViewPageAdapter(imageList, activity);
                            vpView.setAdapter(viewPageAdapter);
                        } else {
                            boolean isIs_video = responseModel.getGraphql().getShortcode_media().isIs_video();
                            FullViewModel fullViewModel2 = new FullViewModel();
                            String video_url2;
                            if (isIs_video) {
                                fullViewModel2.setVideo(true);
                                video_url2 = responseModel.getGraphql().getShortcode_media().getVideo_url();
                                str = ((DisplayResource) responseModel.getGraphql().getShortcode_media().getDisplay_resources().get(responseModel.getGraphql().getShortcode_media().getDisplay_resources().size() - 1)).getSrc();
                                fullViewModel2.setVideoUrl(video_url2);
                                fullViewModel2.setImageUrl(str);
                                fullViewModel2.setThumbnail(str);
                                fullViewModel2.setId(String.valueOf(responseModel.getGraphql().getShortcode_media().getId()));
                            } else {
                                fullViewModel2.setVideoUrl(str);
                                fullViewModel2.setVideo(false);
                                video_url2 = ((DisplayResource) responseModel.getGraphql().getShortcode_media().getDisplay_resources().get(responseModel.getGraphql().getShortcode_media().getDisplay_resources().size() - 1)).getSrc();
                                fullViewModel2.setImageUrl(video_url2);
                                fullViewModel2.setThumbnail(video_url2);
                                fullViewModel2.setId(String.valueOf(responseModel.getGraphql().getShortcode_media().getId()));
                            }
                            imageList.add(fullViewModel2);
                            viewPageAdapter = new ViewPageAdapter(imageList, activity);
                            vpView.setAdapter(viewPageAdapter);
                        }
                        AddDots(Position);
                        Utils.hideProgress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, true, Config.getCookie(FullViewActivity.this), str2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddDots(int i) {
        this.dotscount = this.viewPageAdapter.getCount();
        this.dots = new ImageView[this.dotscount];
        for (int i2 = 0; i2 < this.dotscount; i2++) {
            this.dots[i2] = new ImageView(this.activity);
            this.dots[i2].setImageDrawable(ContextCompat.getDrawable(this.activity, R.drawable.non_active_dot));
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.setMargins(4, 0, 4, 0);
            SliderDots.addView(this.dots[i2], layoutParams);
        }
        this.dots[0].setImageDrawable(ContextCompat.getDrawable(this.activity, R.drawable.active_dot));
        vpView.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                Position = i;
                for (int i2 = 0; i2 < dotscount; i2++) {
                    dots[i2].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.non_active_dot));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.active_dot));
            }
        });
    }

    private boolean checkPermissions(int i) {
        ArrayList arrayList = new ArrayList();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this.activity, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            if (i == 100) {
                Download();
            }
            return true;
        }
        ActivityCompat.requestPermissions(this.activity, (String[]) arrayList.toArray(new String[arrayList.size()]), i);
        return false;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 100 && iArr.length > 0 && iArr[0] == 0) {
            Download();
        }
    }

    public void Download() {
        String str = "insta_";
        String videoUrl;

        FullViewActivity fullViewActivity;
        StringBuilder stringBuilder;
        if (this.Story.equals("2")) {

            if (this.timelineItemModel.getMedia_type() == 2) {
                videoUrl = this.timelineItemModel.getVideo_versions().get(0).getUrl();
                fullViewActivity = this.activity;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append((timelineItemModel.getId()));
                stringBuilder.append(".mp4");
            } else {
                videoUrl = (this.timelineItemModel.getImage_versions2().getCandidates().get(0)).getUrl();
                fullViewActivity = this.activity;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append((timelineItemModel.getId()));
                stringBuilder.append(".png");
            }

        }
        else {

            if (((FullViewModel) this.imageList.get(this.Position)).isVideo()) {
                videoUrl = this.Story.equals("2") ? this.timelineItemModel.getMedia_type() == 2 ?
                        this.timelineItemModel.getVideo_versions().get(0).getUrl() : (this.timelineItemModel.getImage_versions2().getCandidates().get(0)).getUrl() :
                        ((FullViewModel) this.imageList.get(this.Position)).getVideoUrl();

                fullViewActivity = this.activity;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(this.imageList.get(this.Position).getId());
                stringBuilder.append(".mp4");
            } else {
                videoUrl = this.Story.equals("2") ? this.timelineItemModel.getMedia_type() == 2 ?
                        this.timelineItemModel.getVideo_versions().get(0).getUrl() : (this.timelineItemModel.getImage_versions2().getCandidates().get(0)).getUrl() :
                        this.imageList.get(this.Position).getImageUrl();

                fullViewActivity = this.activity;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(((FullViewModel) this.imageList.get(this.Position)).getId());
                stringBuilder.append(".png");
            }

        }

        Utils.startDownload(videoUrl, fullViewActivity, stringBuilder.toString(), this.ShareRepostDownload);
        Utils.startDownloadNew(videoUrl,"/In Insta/",getApplicationContext(),stringBuilder.toString());

    }


}
