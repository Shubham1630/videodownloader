package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.TimelineClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline.TimelineItemModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import videodownload.com.newmusically.R;

public class TimelineAdapter extends Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final TimelineClickInterface timelineClickInterface;
    private final ArrayList<TimelineItemModel> timelineList;


    public TimelineAdapter(Context context, ArrayList<TimelineItemModel> arrayList, TimelineClickInterface timelineClickInterface) {
        this.context = context;
        this.timelineList = arrayList;
        this.timelineClickInterface = timelineClickInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout story_object;
        LinearLayout LLTimeLineUser;
        LinearLayout SliderDots;
        CircleImageView imProfileImage;
        ImageView imTimeLineImage;
        ImageView imShareTimeline;
        ImageView imPlayIcon;
        ImageView imLikeIcon;
        ImageView imDropDownText;
        ImageView imDownloadTimeline;
        TextView tvProfileName;
        TextView tvLikeCount;
        TextView tvTagText;
        TextView tvPostTime;

        public ViewHolder(View view) {
            super(view);
            story_object = view.findViewById(R.id.story_object);
            LLTimeLineUser = view.findViewById(R.id.LLTimeLineUser);
            imProfileImage = view.findViewById(R.id.imProfileImage);
            tvProfileName = view.findViewById(R.id.tvProfileName);
            imTimeLineImage = view.findViewById(R.id.imTimeLineImage);
            imPlayIcon = view.findViewById(R.id.imPlayIcon);
            imLikeIcon = view.findViewById(R.id.imLikeIcon);
            tvLikeCount = view.findViewById(R.id.tvLikeCount);
            SliderDots = view.findViewById(R.id.SliderDots);
            imShareTimeline = view.findViewById(R.id.imShareTimeline);

            imDownloadTimeline = view.findViewById(R.id.imDownloadTimeline);
            tvTagText = view.findViewById(R.id.tvTagText);
            imDropDownText = view.findViewById(R.id.imDropDownText);
            tvPostTime = view.findViewById(R.id.tvPostTime);

        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_timeline, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder1, int i) {

        try {
            ViewHolder viewHolder = (ViewHolder) viewHolder1;
            if (this.timelineList.get(i).getMedia_type() == 1) {
                viewHolder.imPlayIcon.setVisibility(View.GONE);
            } else {
                viewHolder.imPlayIcon.setVisibility(View.VISIBLE);
            }
            viewHolder.tvProfileName.setText(this.timelineList.get(i).getUser().getFull_name());
            TextView textView = viewHolder.tvLikeCount;
            String stringBuilder = this.timelineList.get(i).getLike_count() +
                    " " +
                    this.context.getResources().getString(R.string.likes);
            textView.setText(stringBuilder);
            Glide.with(this.context).load(this.timelineList.get(i).getUser().getProfile_pic_url()).placeholder(R.drawable.ic_placeholder).thumbnail(0.2f).into(viewHolder.imProfileImage);
            Glide.with(this.context).load(this.timelineList.get(i).getImage_versions2().getCandidates().get(0).getUrl()).placeholder(R.drawable.ic_placeholder).thumbnail(0.2f).into(viewHolder.imTimeLineImage);
            if (this.timelineList.get(i).getCaption() != null) {
                viewHolder.tvTagText.setVisibility(View.VISIBLE);
                viewHolder.tvTagText.setText(this.timelineList.get(i).getCaption().getText());
                viewHolder.tvTagText.setMaxLines(3);
                if (this.timelineList.get(i).getCaption().getText().length() > 100) {
                    viewHolder.imDropDownText.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.imDropDownText.setVisibility(View.GONE);
                }
            } else {
                viewHolder.tvTagText.setVisibility(View.GONE);
                viewHolder.imDropDownText.setVisibility(View.GONE);
            }
            viewHolder.tvPostTime.setText(Utils.getTimeAgoString(this.timelineList.get(i).getTaken_at() * 1000, this.context));
            viewHolder.imDropDownText.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    viewHolder.tvTagText.setMaxLines(Integer.MAX_VALUE);
                }
            });
            viewHolder.LLTimeLineUser.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    timelineClickInterface.UserProfileClick(i, timelineList.get(i));
                }
            });
            viewHolder.imTimeLineImage.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    timelineClickInterface.TimelineImageClick(i, timelineList.get(i));
                }
            });
            viewHolder.imDownloadTimeline.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {

                    timelineClickInterface.DownloadClick(i, timelineList.get(i));
                }
            });

            viewHolder.imShareTimeline.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    timelineClickInterface.ShareClick(i, timelineList.get(i));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getItemCount() {
        return (timelineList == null ? 0 : this.timelineList.size());
    }
}
