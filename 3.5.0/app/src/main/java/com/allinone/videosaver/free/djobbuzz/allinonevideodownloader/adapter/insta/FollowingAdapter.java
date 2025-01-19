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

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FavUserClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.FollowingUserClickInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.UserModel;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import videodownload.com.newmusically.R;

public class FollowingAdapter extends Adapter<RecyclerView.ViewHolder> {
    private Context context;
    // DatabaseHandler databaseHandler;
    FavUserClickInterface favUserClickInterface;
    private FollowingUserClickInterface followingUserClickInterface;
    private ArrayList<UserModel> userList;
    private boolean isFromDp;

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        RelativeLayout RLFollowing;
        RelativeLayout RLProfile;
        CircleImageView imImage;
        TextView tvName;
        TextView tvUserName;
        ImageView ivSave;

        public ViewHolder(View view) {
            super(view);
            RLFollowing = view.findViewById(R.id.RLFollowing);
            RLProfile = view.findViewById(R.id.RLProfile);
            imImage = view.findViewById(R.id.imImage);
            tvName = view.findViewById(R.id.tvName);
            tvUserName = view.findViewById(R.id.tvUserName);
            ivSave = view.findViewById(R.id.ivSave);
        }
    }

    public FollowingAdapter(Context context, ArrayList<UserModel> arrayList, FollowingUserClickInterface followingUserClickInterface, FavUserClickInterface favUserClickInterface, boolean isFromDp) {
        this.context = context;
        this.userList = arrayList;
        this.followingUserClickInterface = followingUserClickInterface;
        this.favUserClickInterface = favUserClickInterface;
        this.isFromDp = isFromDp;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_following_user_list, viewGroup, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        try {
            //this.databaseHandler = new DatabaseHandler(this.context);
            holder.tvName.setText(((UserModel) this.userList.get(i)).getFull_name());
            holder.tvUserName.setText(((UserModel) this.userList.get(i)).getUsername());
            Glide.with(this.context).load(((UserModel) this.userList.get(i)).getProfile_pic_url()).thumbnail(0.2f).into(holder.imImage);
            //  holder.imFav.setVisibility(0);
           /* if (this.databaseHandler.checkFavUserData(String.valueOf(((UserModel) this.userList.get(i)).getPk()))) {
                viewHolder.binding.imFav.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_favorite_2));
            } else {
                viewHolder.binding.imFav.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_favorite_1));
            }*/
            holder.RLFollowing.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FollowingAdapter.this.followingUserClickInterface.UserClick(i, (UserModel) FollowingAdapter.this.userList.get(i));
                }
            });
            /*holder.imFav.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    FollowingAdapter.this.favUserClickInterface.FavClicked(i, (UserModel) FollowingAdapter.this.userList.get(i));
                }
            });*/
            if (isFromDp) {
                holder.ivSave.setVisibility(View.VISIBLE);
                holder.ivSave.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (followingUserClickInterface != null) {
                            followingUserClickInterface.DpDownload(i, userList.get(i));
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getItemCount() {
        ArrayList arrayList = this.userList;
        return arrayList == null ? 0 : arrayList.size();
    }
}
