package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter.insta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.UserListInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.TrayModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import videodownload.com.newmusically.R;

public class UserListAdapter extends Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<TrayModel> trayModelArrayList;
    private UserListInterface userListInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView realName;
        CircleImageView storyIcon;
        View RLStoryLayout;

        public ViewHolder(View view) {
            super(view);
            realName = view.findViewById(R.id.real_name);
            storyIcon = view.findViewById(R.id.story_icon);
            RLStoryLayout = view.findViewById(R.id.RLStoryLayout);
        }
    }

    public UserListAdapter(Context context, ArrayList<TrayModel> arrayList, UserListInterface userListInterface) {
        this.context = context;
        this.trayModelArrayList = arrayList;
        this.userListInterface = userListInterface;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int i) {
        ViewHolder viewHolder = (ViewHolder) holder1;
        viewHolder.realName.setText(((TrayModel) this.trayModelArrayList.get(i)).getUser().getFull_name());
        Glide.with(this.context).load(((TrayModel) this.trayModelArrayList.get(i)).getUser().getProfile_pic_url()).placeholder(R.drawable.ic_placeholder).thumbnail(0.2f).into(viewHolder.storyIcon);
        viewHolder.RLStoryLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserListAdapter.this.userListInterface.userListClick(i, (TrayModel) UserListAdapter.this.trayModelArrayList.get(i));
            }
        });
    }


    public int getItemCount() {
        ArrayList arrayList = this.trayModelArrayList;
        return arrayList == null ? 0 : arrayList.size();
    }
}
