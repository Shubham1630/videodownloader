package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.WhatsappFullScreenStatusActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.ModelWhatsAppStatus;
import videodownload.com.newmusically.R;

public class AdapterWhatsAppStatusHorizontal extends Adapter<ViewHolder> {
    private static final String TAG = "AdapterWhatsAppStatus";
    public static int itemStatus = 0;
    private ArrayList<ModelWhatsAppStatus> arrSelectedList = new ArrayList();
    private Context context;
    private boolean isSelection = false;
    private ArrayList<ModelWhatsAppStatus> list;
    private OnAdapterListener onAdapterListener;
    private SelactionChangeListener onSelactionListener;
    private int selectedValue = 0;
    private String what = "";
    private RequestOptions requestOptions;

    ViewPropertyTransition.Animator animationObject;

    public AdapterWhatsAppStatusHorizontal(Context context, ArrayList<ModelWhatsAppStatus> arrayList, int i, String what) {
        this.context = context;
        this.list = arrayList;
        this.selectedValue = i;
        this.what = what;

        animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                // if it's a custom view class, cast it here
                // then find subviews and do the animations
                // here, we just use the entire view for the fade animation
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(500);
                fadeAnim.start();
            }
        };
        requestOptions = new RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder);
    }
    public interface OnAdapterListener {
        void onItemClick(int i);
    }

    public interface SelactionChangeListener {
        void onSelactionChange(boolean z);
    }


    private class WhatsAppStatusViewHolder extends ViewHolder {
        ImageView imgStatusThumb;
        ImageView imgIsVideo;
        ImageView imgSelection;
        View viewSelaction;

        public WhatsAppStatusViewHolder(View view) {
            super(view);
            this.imgStatusThumb = (ImageView) view.findViewById(R.id.imgStatusThumb);
            this.imgIsVideo = (ImageView) view.findViewById(R.id.imgIsVideo);
            this.imgSelection = (ImageView) view.findViewById(R.id.imgSelection);
            this.viewSelaction = view.findViewById(R.id.viewSelaction);
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (WhatsAppStatusViewHolder.this.getAdapterPosition() != -1 && onAdapterListener != null) {
                        onAdapterListener.onItemClick(WhatsAppStatusViewHolder.this.getAdapterPosition());
                    }
                }
            });
            //setIsRecyclable(true);
        }

        private void bind(final int i) {
            final ModelWhatsAppStatus modelWhatsAppStatus = (ModelWhatsAppStatus) list.get(i);
            if (AdapterWhatsAppStatusHorizontal.isVideoFile(modelWhatsAppStatus.filePath)) {
                this.imgIsVideo.setVisibility(View.VISIBLE);
            } else {
                this.imgIsVideo.setVisibility(View.GONE);
            }
            Glide.with(context).load(new File(modelWhatsAppStatus.filePath)).apply(new RequestOptions().dontAnimate()).transition(DrawableTransitionOptions.withCrossFade()).into(this.imgStatusThumb);
            if (isSelection) {
                this.imgSelection.setVisibility(View.VISIBLE);
                this.viewSelaction.setVisibility(View.VISIBLE);
            } else {
                this.imgSelection.setVisibility(View.GONE);
                this.viewSelaction.setVisibility(View.GONE);
            }
            if (arrSelectedList.contains(modelWhatsAppStatus)) {
                this.imgSelection.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_status_selected, null));
            } else {
                this.imgSelection.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_status_unselected, null));
            }
            this.imgStatusThumb.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {

                    if (isSelection) {
                        if (arrSelectedList.contains(modelWhatsAppStatus)) {
                            arrSelectedList.remove(modelWhatsAppStatus);
                        } else {
                            arrSelectedList.add(modelWhatsAppStatus);
                        }
                        if (arrSelectedList.size() > 0 && arrSelectedList.size() < 2) {
                            isSelection = true;
                            if (onSelactionListener != null) {
                                onSelactionListener.onSelactionChange(true);
                            }
                            notifyDataSetChanged();
                        } else if (arrSelectedList.isEmpty()) {
                            isSelection = false;
                            if (onSelactionListener != null) {
                                onSelactionListener.onSelactionChange(false);
                            }
                            notifyDataSetChanged();
                        }
                        notifyItemChanged(i);
                        return;
                    }

                    Intent intent = new Intent(context, WhatsappFullScreenStatusActivity.class);
                    intent.putExtra("selectedValue", selectedValue);
                    intent.putExtra("pos", modelWhatsAppStatus.getItemPos());
                    intent.putExtra("what", what);
                    context.startActivity(intent);
                }
            });
            this.imgStatusThumb.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    if (selectedValue != 0) {
                        if (arrSelectedList.contains(modelWhatsAppStatus)) {
                            arrSelectedList.remove(modelWhatsAppStatus);
                        } else {
                            arrSelectedList.add(modelWhatsAppStatus);
                        }
                        if (arrSelectedList.size() > 0 && arrSelectedList.size() < 2) {
                            isSelection = true;
                            if (onSelactionListener != null) {
                                onSelactionListener.onSelactionChange(true);
                            }
                            notifyDataSetChanged();
                        } else if (arrSelectedList.isEmpty()) {
                            isSelection = false;
                            if (onSelactionListener != null) {
                                onSelactionListener.onSelactionChange(false);
                            }
                            notifyDataSetChanged();
                        }
                        notifyItemChanged(i);
                    }
                    return false;
                }
            });

        }
    }



    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new WhatsAppStatusViewHolder(LayoutInflater.from(this.context).inflate(R.layout.list_item_whats_app_status_horizontal, viewGroup, false));
    }


    public int getItemCount() {
        return this.list.size();
    }

    public int getItemViewType(int i) {
        return ((ModelWhatsAppStatus) this.list.get(i)).itemType;
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (((ModelWhatsAppStatus) this.list.get(i)).itemType == itemStatus) {
            ((WhatsAppStatusViewHolder) viewHolder).bind(i);
        }
    }

    public void setOnAdapterListener(OnAdapterListener onAdapterListener) {
        this.onAdapterListener = onAdapterListener;
    }

    public void setOnSelactionListener(SelactionChangeListener selactionChangeListener) {
        this.onSelactionListener = selactionChangeListener;
    }

    public ArrayList<ModelWhatsAppStatus> getSelectedVideo() {
        return this.arrSelectedList;
    }

    public static boolean isVideoFile(String str) {
        str = URLConnection.guessContentTypeFromName(str);
        return str != null && str.startsWith("video");
    }

    public void deleteAllSelaction() {
        for (int i = 0; i < this.arrSelectedList.size(); i++) {
            File file = new File(((ModelWhatsAppStatus) this.arrSelectedList.get(i)).getFilePath());
            int indexOf = this.list.indexOf(this.arrSelectedList.get(i));
            if (file.exists()) {
                file.delete();
                this.list.remove(indexOf);
            }
            if (i == this.arrSelectedList.size() - 1) {
                this.isSelection = false;
                this.arrSelectedList.clear();
                notifyDataSetChanged();
            }
        }
    }
}
