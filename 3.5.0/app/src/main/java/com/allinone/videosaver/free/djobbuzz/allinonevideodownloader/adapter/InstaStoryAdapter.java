//package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.UserListInterface;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory.ModelTrail;
//import videodownload.com.newmusically.R;
//
//public class InstaStoryAdapter extends RecyclerView.Adapter<InstaStoryAdapter.StoryHolder> {
//
//    private Context context;
//    private ArrayList<ModelTrail> modelTrailArrayList;
//    private UserListInterface userListInterface;
//
//    public InstaStoryAdapter(Context context, ArrayList<ModelTrail> list, UserListInterface listInterface) {
//        this.context = context;
//        this.modelTrailArrayList = list;
//        this.userListInterface = listInterface;
//    }
//
//    @NonNull
//    @Override
//    public InstaStoryAdapter.StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new StoryHolder(LayoutInflater.from(context).inflate(R.layout.story_list_object, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull InstaStoryAdapter.StoryHolder holder, int position) {
//        holder.real_name.setText(modelTrailArrayList.get(position).getUser().getFull_name());
//        holder.user_name.setText(modelTrailArrayList.get(position).getUser().getUsername());
//         Glide.with(context).load(modelTrailArrayList.get(position).getUser().getProfile_pic_url()).apply(new RequestOptions().circleCrop())
//                .thumbnail(0.2f).into(holder.story_icon);
//
//        holder.story_object.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userListInterface.userListClick(position, modelTrailArrayList.get(position));
//            }
//        });
//
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
//
//    public static class StoryHolder extends RecyclerView.ViewHolder {
//        CircleImageView story_icon;
//        View story_object;
//        TextView user_name;
//        TextView real_name;
//
//        public StoryHolder(View view) {
//            super(view);
//            story_icon = view.findViewById(R.id.story_icon);
//            story_object = view.findViewById(R.id.story_object);
//            user_name = view.findViewById(R.id.user_name);
//            real_name = view.findViewById(R.id.real_name);
//           // setIsRecyclable(true);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelTrailArrayList.size();
//    }
//}
