package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FileListClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.ReelFeedModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.CandidatesModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.ItemModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import videodownload.com.newmusically.R;

public class StoryViewAdapter extends Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private FileListClickInterface fileListClickInterface;
    private ArrayList<ItemModel> itemModelsList;
    private LayoutInflater layoutInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_main;
        ImageView iv_image;
        ImageView ivCameraIcon;
        TextView tv_fileName;

        public ViewHolder(View view) {
            super(view);
            rl_main = view.findViewById(R.id.rl_main);
            iv_image = view.findViewById(R.id.iv_image);
            ivCameraIcon = view.findViewById(R.id.ivCameraIcon);
            tv_fileName = view.findViewById(R.id.tv_fileName);

        }
    }

    public StoryViewAdapter(Context context, ArrayList<ItemModel> arrayList, FileListClickInterface fileListClickInterface) {
        this.context = context;
        this.itemModelsList = arrayList;
        this.fileListClickInterface = fileListClickInterface;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_file_view, viewGroup, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ItemModel itemModel = (ItemModel) this.itemModelsList.get(position);
        try {
            if (itemModel.getMedia_type() == 2) {
                viewHolder.ivCameraIcon.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivCameraIcon.setVisibility(View.GONE);
            }
            Glide.with(this.context).load(((CandidatesModel) itemModel.getImage_versions2().getCandidates().get(0)).getUrl()).placeholder(R.drawable.ic_placeholder).into(viewHolder.iv_image);
        } catch (Exception unused) {
        }
        viewHolder.rl_main.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StoryViewAdapter.this.fileListClickInterface.getPosition(position);
            }
        });
    }


    public int getItemCount() {
        ArrayList arrayList = this.itemModelsList;
        return arrayList == null ? 0 : arrayList.size();
    }
}
