package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model;

import android.app.Notification;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.FileUtils;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility;
import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONException;

public class VideoHistoryModel implements Serializable {
    public static String MEDIA_TYPE_ANIMATED_GIF = "gif";
    public static String MEDIA_TYPE_IMAGE = "jpg";
    public static String MEDIA_TYPE_MP4 = "mp4";
    public static String MEDIA_TYPE_MUTIPLY = "media_mutiply";
    public String audio_local_file;
    public String audio_sampling_rate;
    public String audio_url;
    public String create_time;
    public int download_id;
    public float download_percent;
    public float has_download_percent;
    public String history_id;
    public boolean isMergeingAV;
    public int is_audio_download_finished;
    public int is_download_finished;
    public boolean is_downloading;
    public boolean is_multiply_media;
    public boolean is_query_size;
    public String local_file_path;
    public String media_tags;
    public String media_type;
    public transient Notification notification;
    public int notify_id;
    public String origin_url;
    public transient RemoteViews remoteView;
    public long size;
    public String stories_item_id;
    public String stories_item_pk;
    public String thumb_url;
    public long time;
    public String title;
    public String user_name;
    public String user_profile_url;
    public String video_quality;
    public String video_source;
    public String video_url;

    public static class AudioInfoModel {
        public String audio_sampling_rate;
        public String audio_url;
    }

    public String getLocalUrlByIndex(int i) {
        if (!Utility.isNullOrEmpty(this.local_file_path)) {
            String[] split = this.local_file_path.split("[,]");
            if (i < split.length) {
                return split[i];
            }
        }
        return "";
    }

    public String getMbSizeDesc() {
        if (this.size <= 0 && !TextUtils.isEmpty(this.local_file_path)) {
            this.size = FileUtils.getLength(this.local_file_path);
            // TODO: 3/12/2021

        }
        long j = this.size;
        if (j <= 0) {
            return "-Mb";
        }
        if (((((float) j) * 1.0f) / 1024.0f) / 1024.0f >= 1.0f) {
            return String.format("%.1fMb", ((((float) j) * 1.0f) / 1024.0f) / 1024.0f);
        }
        return String.format("%.1fKb", (((float) j) * 1.0f) / 1024.0f);
    }

    public int getMediaCount() {
        return !Utility.isNullOrEmpty(this.local_file_path) ? this.local_file_path.split("[,]").length : 0;
    }

    public String getMediaType() {
        if (this.video_url.startsWith("[")) {
            try {
                JSONArray jSONArray = new JSONArray(this.video_url);
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    String optString = jSONArray.optJSONObject(i3).optString("media_type");
                    if (MEDIA_TYPE_MP4.equals(optString)) {
                        i++;
                    } else if (MEDIA_TYPE_IMAGE.equals(optString) || MEDIA_TYPE_ANIMATED_GIF.equals(optString)) {
                        i2++;
                    }
                }
                if (i == jSONArray.length()) {
                    return MEDIA_TYPE_MP4;
                }
                if (i2 == jSONArray.length()) {
                    return MEDIA_TYPE_IMAGE;
                }
                return MEDIA_TYPE_MUTIPLY;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Utility.getSafeString(this.media_type);
    }

    public String getTags() {
        String str = "";
        if (Utility.isNullOrEmpty(this.media_tags)) {
            return str;
        }
        int indexOf = this.media_tags.indexOf("#");
        if (indexOf > 0) {
            str = this.media_tags.substring(indexOf);
        }
        return str;
    }

    public String getTitle() {
        String str = this.title;
        if (TextUtils.isEmpty(str)) {
            String optString;
            if (this.video_url.startsWith("[")) {
                try {
                    optString = new JSONArray(this.video_url).optJSONObject(0).optString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                    optString = "";
                }
            } else {
                optString = this.video_url;
            }
            for (String str2 : Uri.parse(optString).getPathSegments()) {
                if (!str2.endsWith(".mp4")) {
                    if (str2.endsWith(".jpg")) {
                    }
                }
                str = str2;
            }
        }
        return str == null ? "title" : str;
    }

    public String getVideoTime() {
        long j = this.time;
        if (j <= 0) {
            return "-";
        }
        int i = (int) (((float) j) / 3600.0f);
        int i2 = (int) (j - ((long) (((int) (((float) (j - ((long) ((i * 60) * 60)))) / 60.0f)) * 60)));
        if (i > 0) {
            // TODO: 3/12/2021
            return String.format("%02d:%02d:%02d", i, i, i2);
        }
        // TODO: 3/12/2021
        return String.format("%02d:%02d", i, i2);
    }

    public String getVideoUrlByIndex(int i) {
        if (this.video_url.startsWith("[")) {
            try {
                JSONArray jSONArray = new JSONArray(this.video_url);
                return i < jSONArray.length() ? jSONArray.optJSONObject(i).optString("url") : "";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this.video_url;
    }

    public boolean isFbUrl() {
        return Utility.getSafeString(this.origin_url).contains("fb.watch") || Utility.getSafeString(this.origin_url).contains("facebook.com");
    }

    public boolean isInstgramUrl() {
        return Utility.getSafeString(this.origin_url).contains("instagram.com/stories") || Utility.getSafeString(this.origin_url).contains("instagram.com");
    }

    public boolean isMulitiplyMedia() {
        if (Utility.isNullOrEmpty(this.local_file_path) || this.local_file_path.split("[,]").length <= 1) {
            return false;
        }
        return true;
    }

    public boolean isNeedUpdateUI() {
        float f = this.download_percent;
        if (((double) (f - this.has_download_percent)) < 0.005d) {
            return false;
        }
        this.has_download_percent = f;
        return true;
    }

    public boolean isNormalInstgramUrl() {
        return Utility.getSafeString(this.origin_url).contains("instagram.com");
    }

    public boolean isStoriesUrl() {
        return Utility.getSafeString(this.origin_url).contains("instagram.com/stories");
    }

    public boolean isVideo() {
        return MEDIA_TYPE_MP4.equals(this.media_type);
    }

    @Override
    public String toString() {
        return "VideoHistoryModel{" +
                "audio_local_file='" + audio_local_file + '\'' +
                ", audio_sampling_rate='" + audio_sampling_rate + '\'' +
                ", audio_url='" + audio_url + '\'' +
                ", create_time='" + create_time + '\'' +
                ", download_id=" + download_id +
                ", download_percent=" + download_percent +
                ", has_download_percent=" + has_download_percent +
                ", history_id='" + history_id + '\'' +
                ", isMergeingAV=" + isMergeingAV +
                ", is_audio_download_finished=" + is_audio_download_finished +
                ", is_download_finished=" + is_download_finished +
                ", is_downloading=" + is_downloading +
                ", is_multiply_media=" + is_multiply_media +
                ", is_query_size=" + is_query_size +
                ", local_file_path='" + local_file_path + '\'' +
                ", media_tags='" + media_tags + '\'' +
                ", media_type='" + media_type + '\'' +
                ", notification=" + notification +
                ", notify_id=" + notify_id +
                ", origin_url='" + origin_url + '\'' +
                ", remoteView=" + remoteView +
                ", size=" + size +
                ", stories_item_id='" + stories_item_id + '\'' +
                ", stories_item_pk='" + stories_item_pk + '\'' +
                ", thumb_url='" + thumb_url + '\'' +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_profile_url='" + user_profile_url + '\'' +
                ", video_quality='" + video_quality + '\'' +
                ", video_source='" + video_source + '\'' +
                ", video_url='" + video_url + '\'' +
                '}';
    }
}
