package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper;

import android.app.Activity;
import android.util.Log;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.detail.FullDetailModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CommonAPI {
    private static CommonAPI commonAPI;
    private static Activity activity;
    public String UserAgent = "\"Mozilla/5.0 (Linux; Android 13; Windows NT 10.0; Win64; x64; rv:109.0;Mobile; LG-M255; rv:113.0; SM-A205U; LM-Q720; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.5672.131 Mobile Safari/537.36 Instagram 282.0.0.22.119\"";

    public static CommonAPI getInstance(Activity activity) {
        if (commonAPI == null) {
            commonAPI = new CommonAPI();
        }
        CommonAPI.activity = activity;
        return commonAPI;
    }

    public void getFullFeed(final DisposableObserver disposableObserver, String userid, String str2) {
        APIServices service = RestClient.getInstance(activity).getService();
        service.getFullApi("https://i.instagram.com/api/v1/feed/reels_media/", userid, str2, UserAgent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FullDetailModelNew>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(FullDetailModelNew fullDetailModel) {
                        try {
                            // Consider logging selectively or processing data in chunks
                            Log.d("API Response", "FullDetailModel received");
                            disposableObserver.onNext(fullDetailModel);
                        } catch (OutOfMemoryError e) {
                            Log.e("API Response", "Memory error during onNext", e);
                            // Handle memory error, e.g., free up resources, notify user, etc.
                        } catch (Exception e) {
                            Log.e("API Response", "Unexpected error during onNext", e);
                        }
                    }

                    @Override
                    public void onError(Throwable th) {
                        Log.e("API Response", "Error during API call", th);
                        disposableObserver.onError(th);
                    }

                    @Override
                    public void onComplete() {
                        disposableObserver.onComplete();
                    }
                });
    }

    public void getUserPosts(final DisposableObserver<ResponseBody> disposableObserver, String userid, String cookie) {
        APIServices service = RestClient.getInstance(activity).getService();
        service.getPosts("https://i.instagram.com/api/v1/feed/user/" + userid + "/", cookie, UserAgent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String responseString = responseBody.string(); // Convert the raw response to a string

                            // Parse the JSON response
                            JsonObject jsonResponse = JsonParser.parseString(responseString).getAsJsonObject();

                            // Extract the 'user' object
                            JsonArray userObject = jsonResponse.getAsJsonArray("items");
                            Log.d("API Response Post", userObject.getAsJsonArray().toString());

//                            disposableObserver.onNext(userObject);
                        } catch (Exception e) {
                            Log.e("API Response", "Error processing response", e);
                            disposableObserver.onError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable th) {
                        Log.e("API Response", "Error during API call", th);
                        disposableObserver.onError(th);
                    }

                    @Override
                    public void onComplete() {
                        disposableObserver.onComplete();
                    }
                });
    }





    public void Result(final DisposableObserver disposableObserver, String str, String str2) {
        if (isNullOrEmpty(str2)) {
            str2 = "";
        }
        RestClient.getInstance(activity).getService().getResult(str, str2, UserAgent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(JsonObject jsonObject) {
                disposableObserver.onNext(jsonObject);
            }

            @Override
            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            @Override
            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public static boolean isNullOrEmpty(String s) {
        return (s == null) || (s.length() == 0) || (s.equalsIgnoreCase("null"))||(s.equalsIgnoreCase("0"));
    }


}
