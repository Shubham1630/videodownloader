package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.FullDetailModel;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.story.StoryModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIServices {
    @GET
    Observable<JsonObject> getResult
            (@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @GET
    Observable<FullDetailModelNew> getFullApi
            (@Url String str, @Query("reel_ids") String userId, @Header("Cookie") String cookie, @Header("User-Agent") String userAgent);

    @GET
    Observable<StoryModel> getStories
            (@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);


    @GET
    Observable<ResponseBody> getPosts(@Url String str, @Header("Cookie") String cookie, @Header("User-Agent") String userAgent);


}