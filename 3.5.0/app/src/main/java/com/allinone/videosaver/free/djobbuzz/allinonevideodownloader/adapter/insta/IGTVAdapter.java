package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FileListClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.igtv.NodeModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import videodownload.com.newmusically.R;

public class IGTVAdapter extends Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private FileListClickInterface fileListClickInterface;
    private LayoutInflater layoutInflater;
    private ArrayList<NodeModel> nodeModelList;

    public class ViewHolder extends RecyclerView.ViewHolder {
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

    public IGTVAdapter(Context context, ArrayList<NodeModel> arrayList, FileListClickInterface fileListClickInterface) {
        this.context = context;
        this.nodeModelList = arrayList;
        this.fileListClickInterface = fileListClickInterface;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_file_view, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ViewHolder viewHolder= (ViewHolder) holder;
        NodeModel nodeModel = (NodeModel) this.nodeModelList.get(i);
        try {
            viewHolder.ivCameraIcon.setVisibility(View.VISIBLE);
            Glide.with(this.context).load(nodeModel.getNodeDataModel().getDisplay_url()).placeholder(R.drawable.ic_placeholder).into(viewHolder.iv_image);
        } catch (Exception unused) {
        }
        viewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileListClickInterface.getPosition(i);
            }
        });

    }




    public int getItemCount() {
        ArrayList arrayList = this.nodeModelList;
        return arrayList == null ? 0 : arrayList.size();
    }
}
