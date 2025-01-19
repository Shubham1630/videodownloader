package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.ModelWhatsAppStatus;
import videodownload.com.newmusically.R;

public class WhatsAppStatusCleanerAdapter extends Adapter<ViewHolder> {
    private static final String TAG = "AdapterWhatsAppStatus";
    public static int itemStatus = 0;
    private ArrayList<ModelWhatsAppStatus> arrSelectedList = new ArrayList();
    private Context context;
    private boolean isSelection = false;
    private ArrayList<ModelWhatsAppStatus> list;
    private OnAdapterListener onAdapterListener;
    private SelactionChangeListener onSelactionListener;
    private int selectedValue = 0;
    private int layout;
    private boolean isaudio = false;
    private String what = "";
    private RequestOptions requestOptions;

    ViewPropertyTransition.Animator animationObject;

    public WhatsAppStatusCleanerAdapter(Context context, ArrayList<ModelWhatsAppStatus> arrayList, int i, String what, int layout, boolean isaudio) {
        this.context = context;
        this.list = arrayList;
        this.selectedValue = i;
        this.layout = layout;
        this.what = what;
        this.isaudio = isaudio;

        animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
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
        TextView txtaa;
        View layoutStatusImageHolder;

        public WhatsAppStatusViewHolder(View view) {
            super(view);
            this.imgStatusThumb = (ImageView) view.findViewById(R.id.imgStatusThumb);
            this.imgIsVideo = (ImageView) view.findViewById(R.id.imgIsVideo);
            this.imgSelection = (ImageView) view.findViewById(R.id.imgSelection);
            this.viewSelaction = view.findViewById(R.id.viewSelaction);
            this.txtaa = view.findViewById(R.id.txtaa);
            this.layoutStatusImageHolder = view.findViewById(R.id.layoutStatusImageHolder);
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (WhatsAppStatusCleanerAdapter.WhatsAppStatusViewHolder.this.getAdapterPosition() != -1 && onAdapterListener != null) {
                        onAdapterListener.onItemClick(WhatsAppStatusCleanerAdapter.WhatsAppStatusViewHolder.this.getAdapterPosition());
                    }
                }
            });
            //setIsRecyclable(false);
        }

        private void bind(final int i) {
            final ModelWhatsAppStatus modelWhatsAppStatus = (ModelWhatsAppStatus) list.get(i);
            if (WhatsAppStatusCleanerAdapter.isVideoFile(modelWhatsAppStatus.filePath)) {
                this.imgIsVideo.setVisibility(View.VISIBLE);
            } else {
                this.imgIsVideo.setVisibility(View.GONE);
            }
            if (!isaudio) {
                imgStatusThumb.setImageDrawable(null);
                Glide.with(context).load(new File(modelWhatsAppStatus.filePath)).apply(requestOptions).into(this.imgStatusThumb);
            } else {
                try {
                    txtaa.setText(new File(modelWhatsAppStatus.getFilePath()).getName());
                } catch (Exception e) {
                    txtaa.setText("N/A");
                }

            }

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

            if (isaudio) {
                this.layoutStatusImageHolder.setOnClickListener(new OnClickListener() {
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

                    }
                });
                this.layoutStatusImageHolder.setOnLongClickListener(new OnLongClickListener() {
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
    }


    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new WhatsAppStatusViewHolder(LayoutInflater.from(this.context).inflate(layout, viewGroup, false));
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

    public void selectall(boolean b) {
        if (b) {
            isSelection = true;
            if (!arrSelectedList.isEmpty()) arrSelectedList.clear();
            arrSelectedList.addAll(list);
            notifyDataSetChanged();
        } else {
            isSelection = false;
            arrSelectedList.clear();
            notifyDataSetChanged();
        }
    }

    public void deleteAllSelaction() {
        for (int i = 0; i < this.arrSelectedList.size(); i++) {
            File file = new File(((ModelWhatsAppStatus) this.arrSelectedList.get(i)).getFilePath());
            int indexOf = this.list.indexOf(this.arrSelectedList.get(i));
            if (file.exists()) {
                file.delete();
                this.list.remove(indexOf);
                notifyItemRemoved(indexOf);
            }
            if (i == this.arrSelectedList.size() - 1) {
                this.isSelection = false;
                this.arrSelectedList.clear();
                notifyDataSetChanged();
            }
        }
    }
}
