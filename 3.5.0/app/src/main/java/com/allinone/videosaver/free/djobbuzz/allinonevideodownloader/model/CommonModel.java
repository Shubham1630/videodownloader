package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CommonModel")
public class CommonModel   implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long uid;
    @ColumnInfo(name = "imgurl")
    String imgurl;
    @ColumnInfo(name = "videopath")
    String videopath;
    @ColumnInfo(name = "upath")
    String upath;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "saved_video_path")
    String saved_video_path;
    @ColumnInfo(name = "saved_video_name")
    String saved_video_name;
    @ColumnInfo(name = "saved_video_date")
    String saved_video_date;
    @ColumnInfo(name = "saved_video_url")
    String saved_video_url;

    public String getSaved_video_url() {
        return saved_video_url;
    }

    public void setSaved_video_url(String saved_video_url) {
        this.saved_video_url = saved_video_url;
    }

    public static Creator<CommonModel> getCREATOR() {
        return CREATOR;
    }

    private String reasonMessage;
   // private DownloadListener listener;
    private int fileSize = -1;

    public CommonModel() {
        super();
    }

    protected CommonModel(Parcel in) {
        uid = in.readLong();
        imgurl = in.readString();
        videopath = in.readString();
        upath = in.readString();
        title = in.readString();
        saved_video_path = in.readString();
        saved_video_name = in.readString();
        saved_video_date = in.readString();
    }

    /*public CommonModel(DownloadItem downloadItem) {
        super(downloadItem);
    }*/

    public String getReasonMessage() {
        return reasonMessage;
    }

    public void setReasonMessage(String reasonMessage) {
        this.reasonMessage = reasonMessage;
    }

//    public DownloadListener getListener() {
//        return listener;
//    }
//
//    public void setListener(DownloadListener listener) {
//        this.listener = listener;
//    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
    public static final Creator<CommonModel> CREATOR = new Creator<CommonModel>() {
        @Override
        public CommonModel createFromParcel(Parcel in) {
            return new CommonModel(in);
        }

        @Override
        public CommonModel[] newArray(int size) {
            return new CommonModel[size];
        }
    };

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public String getUpath() {
        return upath;
    }

    public void setUpath(String upath) {
        this.upath = upath;
    }

    public String getSaved_video_path() {
        return saved_video_path;
    }

    public void setSaved_video_path(String saved_video_path) {
        this.saved_video_path = saved_video_path;
    }

    public String getSaved_video_name() {
        return saved_video_name;
    }

    public void setSaved_video_name(String saved_video_name) {
        this.saved_video_name = saved_video_name;
    }

    public String getSaved_video_date() {
        return saved_video_date;
    }

    public void setSaved_video_date(String saved_video_date) {
        this.saved_video_date = saved_video_date;
    }


    public String getImagePath() {
        return this.imgurl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getVideoPath() {
        return this.videopath;
    }

    public String getVideoUniquePath() {
        return this.upath;
    }

    public void setImagePath(String str) {
        this.imgurl = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setVideoPath(String str) {
        this.videopath = str;
    }

    public void setVideoUniquePath(String str) {
        this.upath = str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeString(imgurl);
        dest.writeString(videopath);
        dest.writeString(upath);
        dest.writeString(title);
        dest.writeString(saved_video_path);
        dest.writeString(saved_video_name);
        dest.writeString(saved_video_date);
    }

    @Override
    public String toString() {
        return "CommonModel{" +
                "uid=" + uid +
                ", imgurl='" + imgurl + '\'' +
                ", videopath='" + videopath + '\'' +
                ", upath='" + upath + '\'' +
                ", title='" + title + '\'' +
                ", saved_video_path='" + saved_video_path + '\'' +
                ", saved_video_name='" + saved_video_name + '\'' +
                ", saved_video_date='" + saved_video_date + '\'' +
                ", saved_video_url='" + saved_video_url + '\'' +
                ", reasonMessage='" + reasonMessage + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
