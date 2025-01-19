package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.indeterminate.IndeterminateRoundCornerProgressBar;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.PlayvideosActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.OnItemClickListener;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.FileItem;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.DownloaderNew;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.enums.DownloadStatus;
import ir.siaray.downloadmanagerplus.enums.Errors;
import ir.siaray.downloadmanagerplus.interfaces.ActionListener;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import ir.siaray.downloadmanagerplus.utils.Log;
import ir.siaray.downloadmanagerplus.utils.Utils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import videodownload.com.newmusically.R;

import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.NOTIFICATION_VISIBILITY;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.isStoragePermissionGranted;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.setDownloadBackgroundColor;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.showPopUpMenu;


public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

    private static final String TAG = "DownloadListAdapter";
    private List<FileItem> items;
    private int itemLayout;
    private Activity activity;
    private OnItemClickListener mListener;
    private RecyclerView mRecyclerviewDownloads;
    private int height;
    private int width;
    public DeleteListner deleteListner;

    public interface DeleteListner {
        public void ondeleted(long del, int pos);
    }

    public DownloadListAdapter(Activity activity, RecyclerView rvDownloads, List<FileItem> items, int download_list_item, int width, int height, DeleteListner deleteListner) {
        this.items = items;
        this.itemLayout = download_list_item;
        this.activity = activity;
        mRecyclerviewDownloads = rvDownloads;
        this.width = width;
        this.height = height;
        this.deleteListner = deleteListner;
    }

    public void UpdateAdapter(List<FileItem> items, RecyclerView rvDownloads, int pos) {
        if (items != null) {
            android.util.Log.e(TAG, "UpdateAdapter: items != null" + items.size());
            if (!this.items.isEmpty() && pos < this.items.size()) {
                this.items.remove(pos);
                this.mRecyclerviewDownloads = rvDownloads;
                notifyItemRemoved(pos);
                // notifyItemRangeChanged(pos,this.items.size());
                notifyDataSetChanged();
            }

        } else {
            android.util.Log.e(TAG, "UpdateAdapter: items == null");
        }
        //   this.items = items;

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        handleSize(v);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    private void handleSize(View v) {
        int total_width = width;
        int margin_size = 50;

        v.getLayoutParams().width = total_width;
        v.getLayoutParams().height = (height - margin_size) / getdivisionbysize();

    }

    private int getdivisionbysize() {
        return (this.items != null && this.items.size() > 6 ? 6 : 5);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FileItem item = items.get(position);
        holder.tvName.setText(Utils.getFileName(item.getCommonModel().getSaved_video_name()));
        holder.tvName.setSelected(true);
        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnActionButton(holder, item);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deleteDownload(holder, item, position);
                final ActionListener deleteListener = getDeleteListener(holder
                        , holder.downloadProgressBar
                        , item
                        , position);

                showPopUpMenu(activity, view, item, deleteListener, deleteListner, position);

            }
        });
        showProgress(holder, item, position);
        Log.print("i: " + position + " item: " + item);
        Log.print("status: " + position + " : " + item.getDownloadStatus());

        initItem(holder, item);
        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = item.getCommonModel().getSaved_video_path();
                    if (url != null) {
                        int i = url.lastIndexOf('.');
                        if (i < 0) {
                            url = url + "/" + item.getCommonModel().getSaved_video_name();
                        }
                    } else {
                        url = "";
                    }
                    ArrayList<String> arrayList = new ArrayList();
                    File file = new File(url);
                    arrayList.add(file.getAbsolutePath());
                    android.util.Log.e(TAG, "onClick: holder.itemView " + url);
                    Intent intent = new Intent(activity, PlayvideosActivity.class);
                    int i = 0;
                    intent.putExtra("item", i);
                    intent.putExtra("videoPath", arrayList);
                    intent.putExtra("isfromdownloads", true);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(activity, "Something went wrong, Please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initItem(ViewHolder holder, FileItem item) {
        switch (item.getDownloadStatus()) {
            case NONE:
            case CANCELED:
            case FAILED:
                holder.ivAction.setVisibility(View.GONE);
                holder.iv_image_action.setImageResource(R.drawable.ic_download_video);
                break;
            case PAUSED:
                holder.ivAction.setVisibility(View.GONE);
                holder.iv_image_action.setImageResource(R.drawable.ic_close_wa_tip);
                break;
            case PENDING:
                holder.ivAction.setVisibility(View.GONE);
                holder.iv_image_action.setImageResource(R.drawable.ic_close_wa_tip);
                break;
            case SUCCESSFUL:

                android.util.Log.e(TAG, "initItem: " + item.getCommonModel().getSaved_video_path() + "/" + item.getCommonModel().getSaved_video_name());
                String url = item.getCommonModel().getSaved_video_path();
                if (url != null) {
                    int i = url.lastIndexOf('.');
                    if (i < 0) {
                        url = url + "/" + item.getCommonModel().getSaved_video_name();
                    }
                } else {
                    url = "";
                }
                if (AdapterWhatsAppStatusHorizontal.isVideoFile(url)) {
                    holder.imgIsVideo.setVisibility(View.VISIBLE);
                } else {
                    holder.imgIsVideo.setVisibility(View.GONE);
                }
                Glide.with(activity).load(url).transform(new RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).into(holder.ivAction);
//.transition(DrawableTransitionOptions.withCrossFade())
                holder.ivAction.setVisibility(View.VISIBLE);
                holder.iv_image_action.setVisibility(View.GONE);
                holder.tvSpeed.setVisibility(View.GONE);
                holder.rl_loadingbar.setVisibility(View.GONE);
                break;
            case RUNNING:
                holder.ivAction.setVisibility(View.GONE);
                holder.iv_image_action.setImageResource(R.drawable.ic_close_wa_tip);
                break;
            default:
                holder.ivAction.setVisibility(View.GONE);
                holder.iv_image_action.setImageResource(R.drawable.ic_download_video);
        }
        holder.downloadProgressBar.setProgress(item.getPercent());
    }

    @Override
    public int getItemCount() {

        android.util.Log.e(TAG, "getItemCount: " + items.size());
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAction;
        private ImageView iv_image_action;
        private ImageView imgIsVideo;
        private RoundCornerProgressBar downloadProgressBar;
        private ViewGroup btnAction;
        private ViewGroup btnDelete;
        private IndeterminateRoundCornerProgressBar loading;
        private TextView tvName;
        private DownloadListener listener;
        private TextView tvSize;
        private TextView tvSpeed;
        private TextView tvPercent;
        private View rl_loadingbar;

        private ViewHolder(View itemView) {
            super(itemView);
            ivAction = (ImageView) itemView.findViewById(R.id.iv_image);
            imgIsVideo = (ImageView) itemView.findViewById(R.id.imgIsVideo);
            iv_image_action = (ImageView) itemView.findViewById(R.id.iv_image_action);
            btnAction = (ViewGroup) itemView.findViewById(R.id.btn_action);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            downloadProgressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.progressbar);
            btnDelete = (ViewGroup) itemView.findViewById(R.id.btn_delete);
            loading = (IndeterminateRoundCornerProgressBar) itemView.findViewById(R.id.loading);
            tvSize = itemView.findViewById(R.id.tv_size);
            tvSpeed = itemView.findViewById(R.id.tv_speed);
            tvPercent = itemView.findViewById(R.id.tv_percent);
            rl_loadingbar = itemView.findViewById(R.id.rl_loadingbar);
            //  setIsRecyclable(false);
        }
    }

    private void showProgress(ViewHolder holder, FileItem item, int position) {
        initListener(holder, item, position);

        DownloaderNew.getInstance(activity)
                .setUrl(item.getUri())
                .setListener(holder.listener)
                .setToken(item.getToken())
                .setDestinationDir(item.getCommonModel().getSaved_video_path()
                        , item.getCommonModel().getSaved_video_name())
                .setNotificationTitle(Utils.getFileName(item.getCommonModel().getSaved_video_name()))
                .showProgress();
    }

    private void initListener(final ViewHolder holder, final FileItem item, final int position) {
        Log.print("position visible: " + position);
        holder.listener = new DownloadListener() {
            @Override
            public void onComplete(int totalBytes) {
                Log.print("onComplete: " + position);
                item.setDownloadStatus(DownloadStatus.SUCCESSFUL);
                item.setPercent(100);
                if (isCurrentListViewItemVisible(position)) {

                    android.util.Log.e(TAG, "onComplete: " + item.getCommonModel().getSaved_video_path());

                    String url = item.getCommonModel().getSaved_video_path();
                    if (url != null) {
                        int i = url.lastIndexOf('.');
                        if (i < 0) {
                            url = url + "/" + item.getCommonModel().getSaved_video_name();
                        }
                    } else {
                        url = "";
                    }
                    android.util.Log.e(TAG, "onComplete: " + url);
                    if (AdapterWhatsAppStatusHorizontal.isVideoFile(url)) {
                        holder.imgIsVideo.setVisibility(View.VISIBLE);
                    } else {
                        holder.imgIsVideo.setVisibility(View.GONE);
                    }

                    Glide.with(activity).load(url).transform(new RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).into(holder.ivAction);
//.transition(DrawableTransitionOptions.withCrossFade())
                  /*  if (item.getCommonModel().getSaved_video_name().contains(".jp")) {
                        holder.ivAction.setImageResource(R.drawable.ic_image_placeholder);
                    } else {
                        holder.ivAction.setImageResource(R.drawable.ic_play_placeholder);
                    }*/

                    //  holder.ivAction.setImageResource(R.drawable.ic_download_complate);
                    holder.downloadProgressBar.setProgress(100);
                    holder.loading.setVisibility(View.GONE);
                    holder.tvSpeed.setVisibility(View.GONE);
                    holder.tvPercent.setText("100%");
                    holder.tvSize.setText(String.format("%s - Completed", Utils.readableFileSize(totalBytes)));

                    holder.iv_image_action.setVisibility(View.GONE);
                    holder.ivAction.setVisibility(View.VISIBLE);
                    setDownloadBackgroundColor(holder.btnAction, DownloadStatus.SUCCESSFUL);
                    holder.rl_loadingbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPause(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {
                Log.print("onPause: " + position);
                if (isCurrentListViewItemVisible(position)) {
                    if (item.getDownloadStatus() != DownloadStatus.PAUSED) {
                        holder.ivAction.setImageResource(R.drawable.ic_download_cancel);
                        holder.downloadProgressBar.setProgress(percent);
                        holder.loading.setVisibility(View.VISIBLE);
                        holder.tvPercent.setText(item.getPercent() + "%");
                        holder.tvSize.setText(Utils.readableFileSize(downloadedBytes)
                                + "/" + Utils.readableFileSize(totalBytes) + " - Paused");
                        holder.ivAction.setVisibility(View.GONE);
                        holder.iv_image_action.setImageResource(R.drawable.ic_download_cancel);
                        setDownloadBackgroundColor(holder.btnAction, DownloadStatus.PAUSED);
                    }
                }
                item.setDownloadStatus(DownloadStatus.PAUSED);
            }

            @Override
            public void onPending(int percent, int totalBytes, int downloadedBytes) {
                Log.print("onPending: " + position + " : " + isCurrentListViewItemVisible(position));
                if (isCurrentListViewItemVisible(position)) {
                    if (item.getDownloadStatus() != DownloadStatus.PENDING) {
                        holder.ivAction.setImageResource(R.drawable.ic_download_cancel);
                        holder.downloadProgressBar.setProgress(percent);
                        holder.loading.setVisibility(View.VISIBLE);
                        holder.tvPercent.setText(item.getPercent() + "%");
                        holder.tvSize.setText(Utils.readableFileSize(downloadedBytes)
                                + "/" + Utils.readableFileSize(totalBytes) + " - Pending");
                        holder.ivAction.setVisibility(View.GONE);
                        holder.iv_image_action.setImageResource(R.drawable.ic_download_cancel);
                        setDownloadBackgroundColor(holder.btnAction, DownloadStatus.PENDING);
                    }
                }
                item.setDownloadStatus(DownloadStatus.PENDING);
            }

            @Override
            public void onFail(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {
                Log.print("onFail: " + position);
                item.setDownloadStatus(DownloadStatus.FAILED);
                item.setPercent(0);
                if (isCurrentListViewItemVisible(position)) {
                    holder.ivAction.setImageResource(R.drawable.ic_download_video);
                    holder.downloadProgressBar.setProgress(item.getPercent());
                    holder.loading.setVisibility(View.GONE);
                    holder.tvPercent.setText(item.getPercent() + "%");
                    holder.tvSize.setText(Utils.readableFileSize(downloadedBytes)
                            + "/" + Utils.readableFileSize(totalBytes) + " - Failed");
                    holder.ivAction.setVisibility(View.GONE);
                    holder.iv_image_action.setImageResource(R.drawable.ic_download_video);
                    setDownloadBackgroundColor(holder.btnAction, DownloadStatus.FAILED);
                }
                holder.loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancel(int totalBytes, int downloadedBytes) {
                Log.print("onCancel: " + position);
                item.setDownloadStatus(DownloadStatus.CANCELED);
                item.setPercent(0);
                if (isCurrentListViewItemVisible(position)) {
                    holder.ivAction.setImageResource(R.drawable.ic_download_video);
                    holder.downloadProgressBar.setProgress(item.getPercent());
                    holder.loading.setVisibility(View.GONE);
                    holder.tvPercent.setText(item.getPercent() + "%");
                    holder.tvSize.setText(Utils.readableFileSize(downloadedBytes)
                            + "/" + Utils.readableFileSize(totalBytes) + " - Canceled");
                    holder.ivAction.setVisibility(View.GONE);
                    holder.iv_image_action.setImageResource(R.drawable.ic_download_video);
                    setDownloadBackgroundColor(holder.btnAction, DownloadStatus.CANCELED);
                }
            }

            @Override
            public void onRunning(int percent, int totalBytes, int downloadedBytes, float downloadSpeed) {
                Log.print("onRunning: " + position);
                item.setDownloadStatus(DownloadStatus.RUNNING);
                item.setPercent(percent);
                if (isCurrentListViewItemVisible(position)) {
                    holder.ivAction.setImageResource(R.drawable.ic_download_cancel);
                    holder.downloadProgressBar.setProgress(item.getPercent());
                    holder.loading.setVisibility(View.GONE);
                    holder.tvPercent.setText(item.getPercent() + "%");
                    if (totalBytes < 0 || downloadedBytes < 0)
                        holder.tvSize.setText("loading...");
                    else
                        holder.tvSize.setText(Utils.readableFileSize(downloadedBytes)
                                + "/" + Utils.readableFileSize(totalBytes));
                    holder.tvSpeed.setText(Math.round(downloadSpeed) + " KB/sec");
                    holder.ivAction.setVisibility(View.GONE);
                    holder.iv_image_action.setImageResource(R.drawable.ic_download_cancel);
                    setDownloadBackgroundColor(holder.btnAction, DownloadStatus.RUNNING);
                }


                // Log.i("Title: " + downloadInfo.getTitle());
            }

        };
    }

    private void clickOnActionButton(ViewHolder holder, FileItem item) {
        if (!isStoragePermissionGranted(activity))
            return;
        final DownloaderNew downloader = getDownloader(holder, item, activity);
        if (downloader.getStatus(item.getToken()) == DownloadStatus.RUNNING
                || downloader.getStatus(item.getToken()) == DownloadStatus.PAUSED
                || downloader.getStatus(item.getToken()) == DownloadStatus.PENDING)
            downloader.cancel(item.getToken());
        else if (downloader.getStatus(item.getToken()) == DownloadStatus.SUCCESSFUL) {
            Utils.openFile(activity, downloader.getDownloadedFilePath(item.getToken()));
        } else
            downloader.start();
    }

    public static DownloaderNew getDownloader(ViewHolder holder, FileItem item, Context context) {
        DownloaderNew request = DownloaderNew.getInstance(context)
                .setListener(holder.listener)
                .setUrl(item.getUri())
                .setToken(item.getToken())
                .setKeptAllDownload(false)//if true: canceled download token keep in db
                .setAllowedOverRoaming(true)
                .setVisibleInDownloadsUi(true)
                .setDescription(Utils.readableFileSize(item.getFileSize()))
                .setScanningByMediaScanner(true)
                .setNotificationVisibility(NOTIFICATION_VISIBILITY)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                //.setCustomDestinationDir(DOWNLOAD_DIRECTORY, Utils.getFileName(item.getUri()))//TargetApi 28 and lower
                .setCustomDestinationDir(item.getCommonModel().getSaved_video_path(), item.getCommonModel().getSaved_video_name())
                // .setDestinationDir(item.getCommonModel().getSaved_video_path(), item.getCommonModel().getSaved_video_name())
                .setNotificationTitle(item.getCommonModel().getSaved_video_name());
        request.setAllowedOverMetered(true); //Api 16 and higher
        return request;
    }

    private ActionListener getDeleteListener(final ViewHolder holder
            , final RoundCornerProgressBar numberProgressBar
            , final FileItem item
            , final int position) {
        return new ActionListener() {
            @Override
            public void onSuccess() {
                item.setPercent(0);
                //item.setDownloadStatus(DownloadStatus.NONE);
                //if (isCurrentListViewItemVisible(position)) {
                holder.ivAction.setVisibility(View.GONE);
                holder.iv_image_action.setImageResource(R.drawable.ic_download_video);
                numberProgressBar.setProgress(item.getPercent());
                Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show();
                holder.downloadProgressBar.setProgress(item.getPercent());
                holder.loading.setVisibility(View.GONE);
                holder.tvPercent.setText(item.getPercent() + "%");
                holder.tvSize.setText("Deleted");
                setDownloadBackgroundColor(holder.btnAction, DownloadStatus.CANCELED);
                // }
            }

            @Override
            public void onFailure(Errors error) {
                Toast.makeText(activity, "" + error, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private boolean isCurrentListViewItemVisible(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerviewDownloads.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    private DownloadListAdapter.ViewHolder getViewHolder(int position) {
        return (DownloadListAdapter.ViewHolder) mRecyclerviewDownloads.findViewHolderForLayoutPosition(position);
    }
}
