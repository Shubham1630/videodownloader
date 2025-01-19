package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.insta;

import android.app.Activity;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.PrefManager;

public class Config {
    public static String BASE_URL = "https://i.instagram.com/api/v1/";
    public static String LOGINURL = "https://www.instagram.com/accounts/login/";
    public static String POSTDETAILLINK = "https://www.instagram.com/p/";
    public static String POSTDETAILLINKIGTV = "https://www.instagram.com/tv/";
    public static String STORYUSERLIST;
    public static String USERAGENT = "Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+";
    public static String USERAGENTIGTV = "Instagram 128.0.0.19.128 (Linux; Android 8.0; ANE-LX1 Build/HUAWEIANE-LX1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.109 Mobile Safari/537.36";
    public static String USERSEARCH;
    public static String USERTIMELINE;
    public String UserAgent = "\"Mozilla/5.0 (Linux; Android 13; Windows NT 10.0; Win64; x64; rv:109.0;Mobile; LG-M255; rv:113.0; SM-A205U; LM-Q720; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.5672.131 Mobile Safari/537.36 Instagram 282.0.0.22.119\"";

    static {
        STORYUSERLIST = BASE_URL +
                "feed/reels_tray/";
        USERTIMELINE = BASE_URL +
                "feed/timeline?max_id=";
        USERSEARCH = BASE_URL +
                "users/search?q=";
    }

    public static String IGTVLIST(String str, String str2) {
        String str3 = "https://www.instagram.com/graphql/query/?query_hash=bc78b344a68ed16dd5d7f264681c4c76&variables={\"id\":\"";
        if (str2.equals("")) {
            return str3 +
                    str +
                    "\",\"first\":15}";
        }
        return str3 +
                str +
                "\",\"first\":15,\"after\":\"" +
                str2 +
                "\"}";
    }

    public static String USERFEEDSTORY(String str, String str2) {
        return BASE_URL + "feed/reels_media/" +
                "users/" +
                str +
                "/full_detail_info?max_id=" +
                str2;
    }

    public static String USERFEEDSTORYNEW(String str, String str2) {
        return "https://i.instagram.com/api/v1/feed/reels_media/";
    }

    public static String getCookie(Activity activity) {
        return "ds_user_id=" +
                PrefManager.getInstance(activity).getString(PrefManager.USERID) +
                "; sessionid=" +
                PrefManager.getInstance(activity).getString(PrefManager.SESSIONID);
    }

    public static String getCookieNew(Activity activity) {
        return
                PrefManager.getInstance(activity).getString(PrefManager.COOKIES);

    }
}
