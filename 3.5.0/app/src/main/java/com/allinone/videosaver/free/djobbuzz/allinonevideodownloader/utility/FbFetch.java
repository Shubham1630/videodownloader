package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.IOnFetchCompleted;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.VideoHistoryModel;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ResponseType;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.internal.ANRequestQueue;

import okhttp3.Response;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.nodes.Document;

public class FbFetch {
    public static final String TAG = "FbFetch";

    public static boolean b(String str) {
        Matcher matcher = Pattern.compile("hd_src:\"(.*?)\",sd_src").matcher(str);
        boolean z = false;
        while (matcher.find()) {
            TextUtils.isEmpty(matcher.group(1).replace("amp;", ""));
            z = true;
        }
        return z;
    }

    public static void getData(final String str, final IOnFetchCompleted iOnFetchCompleted) {
        ANRequest.GetRequestBuilder gVar = new ANRequest.GetRequestBuilder(str);
        gVar.addHeaders("User-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
        gVar.addHeaders("sec-fetch-dest", "document");
        gVar.addHeaders("sec-fetch-mode", "navigate");
        gVar.addHeaders("sec-fetch-site", "same-origin");
        gVar.addHeaders("sec-fetch-user", "?1");
        gVar.addHeaders("upgrade-insecure-requests", "1");
        ANRequest bVar = new ANRequest(gVar);

        OkHttpResponseListener okHttpResponseListener = new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response) {
                FbFetch.handleHtml(response, str, iOnFetchCompleted);
            }

            @Override
            public void onError(ANError anError) {
                iOnFetchCompleted.onError(404);
            }

        };
        bVar.setResponseAs(ResponseType.OK_HTTP_RESPONSE);
        bVar.getAsOkHttpResponse(okHttpResponseListener);
        ANRequestQueue.getInstance().addRequest(bVar);

    }

    public static String getMetaByHtml(Document fVar, String str) {
        return "mymeta";

    }

    public static String getTitleByHtml(Document fVar) {
        return "my title";

    }

    public static void handleHtml(final Response c0Var, final String str, final IOnFetchCompleted iOnFetchCompleted) {
        new AsyncTask<Object, Object, ArrayList<VideoHistoryModel>>() {

            public ArrayList<VideoHistoryModel> doInBackground(Object... objArr) {
                Exception e;
                String str = "url";
                String str2 = "name";
                ArrayList<VideoHistoryModel> arrayList = new ArrayList<>();
                try {
                    String x = c0Var.body().string();
                    if (x.contains("r.php?r=")) {
                        return null;
                    }
                    String replace;
                    String string;
                    int safeInt32;
                    String optString;
                    String[] split;
                    String str3;
                    int indexOf = x.indexOf("{\"\\u0040type\":\"VideoObject\"");
                    String str4 = "[-]";
                    String str5 = "";
                    String optString2;
                    if (indexOf > 0) {
                        int indexOf2 = x.indexOf("\"\\u0040context\":\"https:\\/\\/schema.org\"}", indexOf);
                        if (indexOf2 > 0) {
                            JSONObject jSONObject = new JSONObject(StringUtils.decode(x.substring(indexOf, indexOf2 + 39)));
                            replace = jSONObject.optString(str2).replace(" | Facebook", str5);
                            string = jSONObject.getString("description");
                            if (!Utility.isNullOrEmpty(string)) {
                                replace = string;
                            }
                            String[] split2 = jSONObject.optString("duration").split("[T|M|S]");
                            safeInt32 = split2.length == 3 ? (Utility.getSafeInt32(split2[1]) * 60) + Utility.getSafeInt32(split2[2]) : 0;
                            if (split2.length == 2) {
                                safeInt32 = Utility.getSafeInt32(split2[1]);
                            }
                            string = jSONObject.optString(str);
                            JSONObject optJSONObject = jSONObject.optJSONObject("thumbnail");
                            optString = optJSONObject != null ? optJSONObject.optString("contentUrl") : str5;
                            optJSONObject = jSONObject.optJSONObject("publisher");
                            if (optJSONObject != null) {
                                optString2 = optJSONObject.optString(str2);
                                JSONObject optJSONObject2 = optJSONObject.optJSONObject("logo");
                                str = optJSONObject2 != null ? optJSONObject2.optString(str) : str5;
                            } else {
                                str = str5;
                                optString2 = str;
                            }
                            if (Utility.isNullOrEmpty(optString2)) {
                                String[] split3 = jSONObject.optString(str2).split(str4);
                                if (split3.length > 0) {
                                    optString2 = split3[0];
                                }
                            }
                        } else {
                            str = str5;
                            replace = str;
                            optString = replace;
                            string = optString;
                            optString2 = string;
                            safeInt32 = 0;
                        }
                        str2 = optString2;
                    } else {
                        Document L = parseUtils.parsefburls(x);
                        string = FbFetch.getMetaByHtml(L, "property=og:video:url");
                        replace = FbFetch.getMetaByHtml(L, "name=description");
                        optString = FbFetch.getMetaByHtml(L, "property=og:image");
                        split = Utility.getSafeString(FbFetch.getTitleByHtml(L)).split(str4);
                        if (split.length > 0) {
                            optString2 = split[0];
                            str = str5;
                            str2 = optString2;
                        } else {
                            str = str5;
                            str2 = str;
                        }
                        safeInt32 = 0;
                    }
                    if (Utility.isNullOrEmpty(str)) {
                        String[] split4 = str.split("[/]");
                        if (split4.length > 1) {
                            Object obj = split4[3];
                            str = String.format("https://graph.facebook.com/%s/picture?type=large", obj);
                        }
                    }
                    int indexOf3 = x.indexOf("json_encoded_video_data:\"");
                    int indexOf4 = x.indexOf("}}", indexOf3);
                    String str6 = "1280x720";
                    String str7 = "hd_src:";
                    String str8 = "sd_src:";
                    String str9 = "\"";
                    String str10 = string;
                    string = "640x480";
                    String str11;
                    VideoHistoryModel videoHistoryModel;
                    int indexOf5;
                    VideoHistoryModel videoHistoryModel2;
                    if (indexOf3 <= 0 || indexOf4 <= 0) {
                        str11 = str;
                        str3 = str7;
                        int indexOf6 = x.indexOf("videoData:[{");
                        int indexOf7 = x.indexOf("stream_type", indexOf6);
                        if (indexOf7 > indexOf6 && indexOf6 > -1) {
                            String str12;
                            split = x.substring(indexOf6 + 12, indexOf7 - 1).split("[,]");
                            indexOf7 = split.length;
                            CharSequence charSequence = str5;
                            CharSequence charSequence2 = charSequence;
                            int i = 0;
                            while (i < indexOf7) {
                                String[] strArr;
                                int i2 = indexOf7;
                                str12 = split[i];
                                if (str12.startsWith(str8)) {
                                    charSequence2 = str12.replace(str8, str5).replace(str9, str5);
                                    strArr = split;
                                    str = str3;
                                } else {
                                    strArr = split;
                                    str = str3;
                                    if (str12.startsWith(str)) {
                                        charSequence = str12.replace(str, str5).replace(str9, str5);
                                    }
                                }
                                i++;
                                str3 = str;
                                indexOf7 = i2;
                                split = strArr;
                            }
                            str = str3;
                            if (!TextUtils.isEmpty(charSequence)) {
                                if (!"null".contentEquals(charSequence)) {
                                    videoHistoryModel = new VideoHistoryModel();
                                    videoHistoryModel.video_quality = str6;
                                    videoHistoryModel.video_url = charSequence.toString();
                                    videoHistoryModel.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
                                    videoHistoryModel.origin_url = str;
                                    videoHistoryModel.user_name = str2;
                                    videoHistoryModel.user_profile_url = str11;
                                    videoHistoryModel.time = (long) safeInt32;
                                    videoHistoryModel.title = replace;
                                    videoHistoryModel.thumb_url = optString;
                                    arrayList.add(videoHistoryModel);
                                }
                            }
                            if (!TextUtils.isEmpty(charSequence2)) {
                                videoHistoryModel = new VideoHistoryModel();
                                videoHistoryModel.video_quality = string;
                                videoHistoryModel.video_url = charSequence2.toString();
                                videoHistoryModel.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
                                videoHistoryModel.origin_url = str;
                                videoHistoryModel.user_name = str2;
                                videoHistoryModel.user_profile_url = str11;
                                videoHistoryModel.time = (long) safeInt32;
                                videoHistoryModel.title = replace;
                                videoHistoryModel.thumb_url = optString;
                                arrayList.add(videoHistoryModel);
                            }
                            if (arrayList.size() != 0) {
                                indexOf7 = x.indexOf(str8);
                                str8 = ",";
                                if (indexOf7 > 0) {
                                    indexOf5 = x.indexOf(str8, indexOf7);
                                    if (indexOf5 > 0) {
                                        str12 = x.substring(indexOf7, indexOf5);
                                        videoHistoryModel2 = new VideoHistoryModel();
                                        videoHistoryModel2.video_quality = string;
                                        videoHistoryModel2.video_url = str12;
                                        videoHistoryModel2.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
                                        videoHistoryModel2.origin_url = str;
                                        videoHistoryModel2.user_name = str2;
                                        videoHistoryModel2.user_profile_url = str11;
                                        str5 = str2;
                                        videoHistoryModel2.time = (long) safeInt32;
                                        videoHistoryModel2.title = replace;
                                        videoHistoryModel2.thumb_url = optString;
                                        arrayList.add(videoHistoryModel2);
                                        indexOf6 = x.indexOf(str);
                                        if (indexOf6 > 0) {
                                            indexOf7 = x.indexOf(str8, indexOf6);
                                            if (indexOf7 > 0) {
                                                str = x.substring(indexOf6, indexOf7);
                                                videoHistoryModel = new VideoHistoryModel();
                                                videoHistoryModel.video_quality = str6;
                                                videoHistoryModel.video_url = str;
                                                videoHistoryModel.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
                                                videoHistoryModel.origin_url = str;
                                                videoHistoryModel.user_name = str5;
                                                videoHistoryModel.user_profile_url = str11;
                                                videoHistoryModel.time = (long) safeInt32;
                                                videoHistoryModel.title = replace;
                                                videoHistoryModel.thumb_url = optString;
                                                arrayList.add(videoHistoryModel);
                                            }
                                        }
                                    }
                                }
                                str5 = str2;
                                indexOf6 = x.indexOf(str);
                            } else {
                                str5 = str2;
                            }
                            VideoHistoryModel videoHistoryModel3;
                            if (arrayList.size() == 0) {
                                str12 = str10;
                                if (!Utility.isNullOrEmpty(replace)) {
                                    videoHistoryModel3 = new VideoHistoryModel();
                                    DialogFacebookDownload.mVideoHistoryModel = videoHistoryModel3;
                                    videoHistoryModel3.video_quality = string;
                                    videoHistoryModel3.video_url = str12;
                                    videoHistoryModel3.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
                                    DialogFacebookDownload.mVideoHistoryModel.origin_url = str;
                                    DialogFacebookDownload.mVideoHistoryModel.user_name = str5;
                                    DialogFacebookDownload.mVideoHistoryModel.user_profile_url = str11;
                                    DialogFacebookDownload.mVideoHistoryModel.time = (long) safeInt32;
                                    DialogFacebookDownload.mVideoHistoryModel.title = replace;
                                    DialogFacebookDownload.mVideoHistoryModel.thumb_url = optString;
                                }
                            } else if (!Utility.isNullOrEmpty(str10)) {
                                videoHistoryModel3 = new VideoHistoryModel();
                                videoHistoryModel3.video_quality = string;
                                videoHistoryModel3.video_url = str10;
                                videoHistoryModel3.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
                                videoHistoryModel3.origin_url = str;
                                videoHistoryModel3.user_name = str5;
                                videoHistoryModel3.user_profile_url = str11;
                                videoHistoryModel3.time = (long) safeInt32;
                                videoHistoryModel3.title = replace;
                                videoHistoryModel3.thumb_url = optString;
                                arrayList.add(videoHistoryModel3);
                            }
                            return arrayList;
                        }
                    }
                    str3 = str7;
                    str11 = x.substring(indexOf3 + 25, indexOf4 + 2).replace("\\\"", str9).replace("\\\\u003C", "<").replace("\\\\\"", str9).replace("\\\\/", "/").replace("\\\\n", str5);
                    indexOf4 = str11.indexOf("<?xml version=");
                    indexOf5 = str11.indexOf("</MPD>", indexOf4);
                    if (indexOf5 > 0) {
                        str11 = str11.replace(str11.substring(indexOf4, indexOf5 + 6), str5);
                    }
                    JSONObject jSONObject2 = new JSONObject(str11);
                    str11 = jSONObject2.optString("sd_src");
                    if (TextUtils.isEmpty(str11)) {
                        str11 = str;
                    } else {
                        try {
                            videoHistoryModel2 = new VideoHistoryModel();
                            videoHistoryModel2.video_quality = string;
                            videoHistoryModel2.video_url = str11;
                            videoHistoryModel2.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;
                            videoHistoryModel2.origin_url = str;
                            videoHistoryModel2.user_name = str2;
                            videoHistoryModel2.user_profile_url = str;
                            str11 = str;
                            videoHistoryModel2.time = (long) safeInt32;
                            videoHistoryModel2.title = replace;
                            videoHistoryModel2.thumb_url = optString;
                            arrayList.add(videoHistoryModel2);
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            return arrayList;
                        }
                    }
                    str = jSONObject2.optString("hd_src");
                    if (TextUtils.isEmpty(str)) {

                    } else {
                        videoHistoryModel = new VideoHistoryModel();
                        videoHistoryModel.video_quality = str6;
                        videoHistoryModel.video_url = str;
                        videoHistoryModel.media_type = VideoHistoryModel.MEDIA_TYPE_MP4;

                        try {
                            videoHistoryModel.origin_url = str;
                            videoHistoryModel.user_name = str2;
                            videoHistoryModel.user_profile_url = str11;
                            videoHistoryModel.time = (long) safeInt32;
                            videoHistoryModel.title = replace;
                            videoHistoryModel.thumb_url = optString;
                            arrayList.add(videoHistoryModel);
                        } catch (Exception e3) {
                            e = e3;
                            e.printStackTrace();
                            return arrayList;
                        }
                    }
                    str = str3;

                    return arrayList;
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    return arrayList;
                }
            }

            public void onPostExecute(ArrayList<VideoHistoryModel> arrayList) {
                if (arrayList == null) {
                    iOnFetchCompleted.onError(407);
                } else if (arrayList.size() == 0) {
                    iOnFetchCompleted.onError(0);
                } else {
                    iOnFetchCompleted.onFetchCompleted(arrayList);
                }
            }
        }.execute();
    }
}
