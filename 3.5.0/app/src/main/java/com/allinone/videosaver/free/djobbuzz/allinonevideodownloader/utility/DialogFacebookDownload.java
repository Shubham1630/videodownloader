package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.NewfbActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.PlayvideosActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.VideoHistoryModel;

import java.util.ArrayList;
import java.util.List;

import videodownload.com.newmusically.BuildConfig;
import videodownload.com.newmusically.R;

public class DialogFacebookDownload extends BaseDialogView {
    private static final String TAG = "DialogFacebookDownload";
    public static VideoHistoryModel mVideoHistoryModel;
    public final int MSG_QUERY_VIDEO_FINISHED = 256;
    public final int MSG_QUERY_VIDEO_SIZE = 257;
    public ImageView img_hd_download;
    public ImageView img_hd_watch;
    public ImageView img_sd_watch;
    public ImageView img_sd_download;
    public LinearLayout ll_container_1;
    public LinearLayout ll_container_2;
    public LinearLayout ll_container_watch;
    public LinearLayout ll_item_container;
    public List<VideoHistoryModel> mArrayVideoHistorys;

    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        public void handleMessage(@NonNull Message message) {
            int i = message.what;
            Log.e(TAG, "handleMessage: ............1");
            Log.e(TAG, "handleMessage: what:" + i);
            if (i == 256) {
                Log.e(TAG, "handleMessage: ............2");
                DialogFacebookDownload.this.img_hd_download.setVisibility(View.VISIBLE);
                DialogFacebookDownload.this.img_hd_watch.setVisibility(View.VISIBLE);
                DialogFacebookDownload.this.img_sd_watch.setVisibility(View.VISIBLE);
                DialogFacebookDownload.this.img_sd_download.setVisibility(View.VISIBLE);
                DialogFacebookDownload.this.pb_progress_hd.setVisibility(View.GONE);
                DialogFacebookDownload.this.pb_progress_sd.setVisibility(View.GONE);
                Object obj = message.obj;
                if (obj != null) {
                    DialogFacebookDownload.this.handleVideo((ArrayList) obj);
                    return;
                }
                DialogFacebookDownload.this.handleVideo(null);
            } else if (257 == i) {
                Object[] objArr = (Object[]) message.obj;
                VideoHistoryModel videoHistoryModel = (VideoHistoryModel) objArr[0];
                ProgressBar progressBar = (ProgressBar) objArr[1];
                TextView textView = (TextView) objArr[2];
                progressBar.setVisibility(View.GONE);
                textView.setText(videoHistoryModel.getMbSizeDesc());
            }
        }
    };
    public String mszVid;
    public String mszVideoUrl;
    public ProgressBar pb_progress;
    public ProgressBar pb_progress_1;
    public ProgressBar pb_progress_2;
    public ProgressBar pb_progress_hd;
    public ProgressBar pb_progress_sd;
    public TextView tv_size_1;
    public TextView tv_size_2;

    public DialogFacebookDownload(Context context, String str, String str2, List<VideoHistoryModel> list) {
        super(context);
        this.mszVideoUrl = str;
        this.mszVid = str2;
        this.mArrayVideoHistorys = list;
    }

    private void fetchVideo(String str) {
        FetchVideoUtils.sharedInstance(this.mCtx).fetchVideo(str, new FetchVideoUtils.IDownloadFinisheCallback() {
            public void onDownloadFailed(int i) {
                Message message = new Message();
                message.what = 256;
                DialogFacebookDownload.this.mHandler.sendMessage(message);
            }

            public void onDownloadFinished(List<VideoHistoryModel> list) {
                Message message = new Message();
                message.what = 256;
                message.obj = list;
                DialogFacebookDownload.this.mHandler.sendMessage(message);
            }
        });
    }

    public static StringBuilder getapendstr(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        return stringBuilder;
    }

    private void handleVideo(List<VideoHistoryModel> list) {
        Log.e(TAG, "handleVideo: called------------- size:" + (list == null ? "null" : list.size()));
        if (list != null && !list.isEmpty() && BuildConfig.DEBUG) {
            for (VideoHistoryModel videoHistoryModel : list) {
                Log.e(TAG, " videoHistoryModel \n******************************************************\n:" +
                        "" + videoHistoryModel.toString() + "\n******************************************************\n");
            }
        }
        this.ll_item_container.setVisibility(View.VISIBLE);
        this.pb_progress.setVisibility(View.GONE);
        if (list == null) {
            VideoHistoryModel videoHistoryModel = new VideoHistoryModel();
            StringBuilder k = getapendstr("https://www.facebook.com/watch/?v=");
            k.append(this.mszVid);
            videoHistoryModel.origin_url = k.toString();
            String str = "";
            videoHistoryModel.thumb_url = str;
            videoHistoryModel.user_profile_url = str;
            videoHistoryModel.user_name = str;
            videoHistoryModel.title = str;
            videoHistoryModel.video_url = this.mszVideoUrl;
            videoHistoryModel.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
            this.ll_container_1.setVisibility(View.GONE);
            this.ll_container_2.setVisibility(View.VISIBLE);
            pb_progress_2.setVisibility(View.GONE);
            this.ll_container_2.setTag(videoHistoryModel);
            this.img_sd_watch.setTag(videoHistoryModel);
            queryVideoSize(videoHistoryModel, this.pb_progress_1, this.tv_size_1);
        } else if (list.size() == 1) {
            VideoHistoryModel videoHistoryModel2 = (VideoHistoryModel) list.get(0);
            if (!videoHistoryModel2.video_url.contains(".mp4")) {
                videoHistoryModel2.video_url = this.mszVideoUrl;
            }
            this.ll_container_1.setVisibility(View.GONE);
            this.ll_container_2.setVisibility(View.VISIBLE);
            pb_progress_2.setVisibility(View.GONE);
            this.ll_container_2.setTag(videoHistoryModel2);
            this.img_sd_watch.setTag(videoHistoryModel2);
            queryVideoSize((VideoHistoryModel) list.get(0), this.pb_progress_1, this.tv_size_1);
        } else if (list.size() >= 2) {
            this.ll_container_1.setVisibility(View.VISIBLE);
            this.ll_container_1.setTag(list.get(0));
            this.img_hd_watch.setTag(list.get(0));
            queryVideoSize((VideoHistoryModel) list.get(0), this.pb_progress_1, this.tv_size_1);
            this.ll_container_2.setVisibility(View.VISIBLE);
            this.ll_container_2.setTag(list.get(1));
            this.img_sd_watch.setTag(list.get(1));

            queryVideoSize((VideoHistoryModel) list.get(1), this.pb_progress_2, this.tv_size_2);
        }
    }

    private void navDownload(VideoHistoryModel videoHistoryModel) {
        dismiss();
        Context context = this.mCtx;
        if (context instanceof NewfbActivity) {
            ((NewfbActivity) context).navDownload(videoHistoryModel);
        } else {
            Intent intent = new Intent();
            intent.setAction("ACTION_START_DOWNLOAD_FB");
            intent.putExtra("videoInfo", videoHistoryModel);
            this.mCtx.sendBroadcast(intent);
        }

    }

    private void queryVideoSize(final VideoHistoryModel videoHistoryModel, final ProgressBar progressBar, final TextView textView) {
        FetchVideoUtils.sharedInstance(this.mCtx).getVideoSize(videoHistoryModel, new FetchVideoUtils.IQueryVideoSizeCallback() {
            public void onQueryFinished() {
                Object[] objArr = new Object[]{videoHistoryModel, progressBar, textView};
                Message message = new Message();
                message.what = 257;
                message.obj = objArr;
                DialogFacebookDownload.this.mHandler.sendMessage(message);
            }
        });
    }

    public static void showDialog(Context context, String str, String str2, List<VideoHistoryModel> list) {
        if (!((Activity) context).isFinishing()) {
            new DialogFacebookDownload(context, str, str2, list).showDialogView();
        }
    }

    private void toDownload(VideoHistoryModel videoHistoryModel) {
        navDownload(videoHistoryModel);
    }


    public View getView() {
        return LayoutInflater.from(this.mCtx).inflate(R.layout.dialog_view_facebook_download, null);
    }

    public void showDialogView() {
        super.showDialogView();
        Window window = getDlg().getWindow();
        Display defaultDisplay = window.getWindowManager().getDefaultDisplay();
        LayoutParams attributes = getDlg().getWindow().getAttributes();
        attributes.width = (int) (((float) defaultDisplay.getWidth()) * 0.86f);
        window.setAttributes(attributes);
        this.ll_item_container = (LinearLayout) this.mView.findViewById(R.id.ll_item_container);
        this.ll_container_watch = (LinearLayout) this.mView.findViewById(R.id.ll_container_watch);
        ll_container_watch.setVisibility(View.GONE);
        this.ll_container_1 = (LinearLayout) this.mView.findViewById(R.id.ll_container_1);
        this.ll_container_2 = (LinearLayout) this.mView.findViewById(R.id.ll_container_2);
        this.pb_progress = (ProgressBar) this.mView.findViewById(R.id.pb_progress);
        this.pb_progress_1 = (ProgressBar) this.mView.findViewById(R.id.pb_progress_1);
        this.pb_progress_2 = (ProgressBar) this.mView.findViewById(R.id.pb_progress_2);
        this.tv_size_1 = (TextView) this.mView.findViewById(R.id.tv_size_1);
        this.tv_size_2 = (TextView) this.mView.findViewById(R.id.tv_size_2);
        this.pb_progress_hd = (ProgressBar) this.mView.findViewById(R.id.pb_progress_hd);
        this.pb_progress_sd = (ProgressBar) this.mView.findViewById(R.id.pb_progress_sd);
        this.img_hd_download = (ImageView) this.mView.findViewById(R.id.img_hd_download);
        this.img_hd_watch = (ImageView) this.mView.findViewById(R.id.img_hd_watch);
        this.img_sd_watch = (ImageView) this.mView.findViewById(R.id.img_sd_watch);
        this.img_sd_download = (ImageView) this.mView.findViewById(R.id.img_sd_download);
        // TODO: 3/12/2021
        ll_container_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toDownload((VideoHistoryModel) v.getTag());

            }
        });

        img_sd_watch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoHistoryModel videoHistoryModel = (VideoHistoryModel) v.getTag();
                ArrayList<String> arrayList = new ArrayList();
                arrayList.add(videoHistoryModel.video_url);
                Intent intent = new Intent(mCtx, PlayvideosActivity.class);
                int i = 0;
                intent.putExtra("item", i);
                intent.putExtra("videoPath", arrayList);
                intent.putExtra("isfromdownloads", true);
                mCtx.startActivity(intent);

            }
        });
        img_hd_watch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoHistoryModel videoHistoryModel = (VideoHistoryModel) v.getTag();
                ArrayList<String> arrayList = new ArrayList();
                arrayList.add(videoHistoryModel.video_url);
                Intent intent = new Intent(mCtx, PlayvideosActivity.class);
                int i = 0;
                intent.putExtra("item", i);
                intent.putExtra("videoPath", arrayList);
                intent.putExtra("isfromdownloads", true);
                mCtx.startActivity(intent);

            }
        });
        ll_container_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toDownload((VideoHistoryModel) v.getTag());
            }
        });

        if (this.mArrayVideoHistorys != null) {
            this.ll_container_watch.setVisibility(View.GONE);
            this.img_hd_download.setVisibility(View.VISIBLE);
            this.img_hd_watch.setVisibility(View.VISIBLE);
            this.img_sd_watch.setVisibility(View.VISIBLE);
            this.img_sd_download.setVisibility(View.VISIBLE);
            this.pb_progress_hd.setVisibility(View.GONE);
            this.pb_progress_sd.setVisibility(View.GONE);
            handleVideo(this.mArrayVideoHistorys);
            return;
        }
        VideoHistoryModel videoHistoryModel = new VideoHistoryModel();
        String str = "";
        videoHistoryModel.thumb_url = str;
        videoHistoryModel.user_profile_url = str;
        videoHistoryModel.user_name = str;
        videoHistoryModel.title = str;
        videoHistoryModel.video_url = this.mszVideoUrl;
        this.ll_item_container.setVisibility(View.VISIBLE);
        this.ll_container_2.setVisibility(View.VISIBLE);
        this.ll_container_watch.setTag(videoHistoryModel);
        String stringBuilder = "https://www.facebook.com/watch/?v=" +
                this.mszVid;
        fetchVideo(stringBuilder);
    }
}
