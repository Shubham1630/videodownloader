package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.YnsInterface;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.yns.FormatItem;

import java.util.ArrayList;

import videodownload.com.newmusically.R;

import static ir.siaray.downloadmanagerplus.utils.Utils.readableFileSize;

public class YnsAdapter extends RecyclerView.Adapter<YnsAdapter.StoryHolder> {

    private static final String TAG = "YnsAdapter";
    private Context context;
    private ArrayList<FormatItem> modelTrailArrayList;
    private final YnsInterface userListInterface;

    public YnsAdapter(Context context, ArrayList<FormatItem> list, YnsInterface listInterface) {
        this.context = context;
        this.modelTrailArrayList = list;
        this.userListInterface = listInterface;
    }

    @NonNull
    @Override
    public YnsAdapter.StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryHolder(LayoutInflater.from(context).inflate(R.layout.list_item_yns_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull YnsAdapter.StoryHolder holder, int position) {
        FormatItem formatItem = modelTrailArrayList.get(position);
        if (formatItem.getWidth() == null || formatItem.getHeight() == null) {
            holder.txt_videotitle.setText(String.format("%s File", formatItem.getExt()));
        } else {

            holder.txt_videotitle.setText(getinfostring(formatItem));
        }

        if (formatItem.getSize() == null || formatItem.getSize().equals("0")) {
            holder.txt_videosize.setText("N/A");
        } else {
            holder.txt_videosize.setText(readableFileSize(Long.parseLong(formatItem.getSize())));

        }


        holder.btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: called" );
                userListInterface.userListClick(position, modelTrailArrayList.get(position));
            }
        });

    }

    private String getinfostring(FormatItem formatItem) {
        String appendstring = " Video Only";
        Log.e(TAG, "getinfostring: " + formatItem.getId());
        switch (formatItem.getId()) {
            case "136":
            case "398":
                return "720P MP4" + appendstring;
            case "137":
            case "399":
                return "1080P MP4" + appendstring;
            case "134":
            case "18":
            case "396":
                return "360P MP4" + appendstring;
            case "133":
            case "395":
                return "240P MP4" + appendstring;
            case "397":
            case "135":
                return "480P MP4" + appendstring;
            case "160":
            case "394":
                return "144P MP4" + appendstring;
            case "248":
                return "1080P WEBM" + appendstring;
            case "247":
                return "720P WEBM" + appendstring;
            case "244":
                return "480P WEBM" + appendstring;
            case "243":
                return "360P WEBM" + appendstring;
            case "242":
                return "240P WEBM" + appendstring;
            case "278":
                return "144P WEBM" + appendstring;
            case "22":
                return "720P MP4 VIDEO+AUDIO";
            case "36":
                return "240P 3GP VIDEO+AUDIO";
            case "17":
                return "144P 3GP VIDEO+AUDIO";
            default:
                return "Video File";


        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class StoryHolder extends RecyclerView.ViewHolder {


        TextView txt_videotitle;
        TextView txt_videosize;
        Button btndownload;

        public StoryHolder(View view) {
            super(view);
            txt_videosize = view.findViewById(R.id.txt_videosize);
            txt_videotitle = view.findViewById(R.id.txt_videotitle);
            btndownload = view.findViewById(R.id.btndownload);

        }
    }

    @Override
    public int getItemCount() {
        return modelTrailArrayList.size();
    }
}
