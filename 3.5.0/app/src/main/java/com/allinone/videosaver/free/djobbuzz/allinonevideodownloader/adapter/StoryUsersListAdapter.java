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
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta.UserListInterface;
//import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instagram.instagramstory.ModelTrail;
//
//import videodownload.com.newmusically.R;
//
//public class StoryUsersListAdapter extends RecyclerView.Adapter<StoryUsersListAdapter.StoryHolder> {
//    private Context context;
//    private ArrayList<ModelTrail> modelTrailArrayList;
//    private UserListInterface userListInterface;
//
//    public StoryUsersListAdapter(Context context, ArrayList<ModelTrail> list, UserListInterface listInterface) {
//        this.context = context;
//        this.modelTrailArrayList = list;
//        this.userListInterface = listInterface;
//    }
//
//    @NonNull
//    @Override
//    public StoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View view = layoutInflater.inflate(R.layout.story_list_object, viewGroup, false);
//        return new StoryHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull StoryHolder viewHolder, int position) {
//
//        viewHolder.real_name.setText(modelTrailArrayList.get(position).getUser().getFull_name());
//        viewHolder.user_name.setText(modelTrailArrayList.get(position).getUser().getUsername());
//       /* Glide.with(context).load(trayModelArrayList.get(position).getUser().getProfile_pic_url()).apply(new RequestOptions().circleCrop())
//                .thumbnail(0.2f).into(viewHolder.story_icon);*/
//
//        viewHolder.story_object.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userListInterface.userListClick(position, modelTrailArrayList.get(position));
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelTrailArrayList == null ? 0 : modelTrailArrayList.size();
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
//            setIsRecyclable(true);
//        }
//    }
//}