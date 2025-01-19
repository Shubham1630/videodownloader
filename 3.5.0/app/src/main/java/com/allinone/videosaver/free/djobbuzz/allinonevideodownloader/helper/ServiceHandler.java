package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.helper;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ServiceHandler {
    private static final String TAG = "ServiceHandler";
    public static final int TIMEOUT=10000;
    public static final String gettiktokvideourl = getlink();
    public static final String getmxvideourl = getmxlink();
    public static final String getmojvideourl = getmojLink();
    public static final String getinstastoryurl = getinstastoryLink();
    public static final String gettxurl = gettxlink();
    public static final String getyns = getynsurl();
    public static final String getnewtiktokvdourl = gettiktoknew();


    private static String getmxlink() {
        return getchar(new int[]{103896,115884,115884,111888,57942,46953,46953,108891,119880,115884,96903,106893,96903,115884,96903,106893,117882,104895,99900,100899,110889,99900,110889,118881,109890,107892,110889,96903,99900,100899,113886,45954,114885,103896,104895,117882,105894,96903,102897,96903,113886,45954,98901,110889,45954,104895,109890,46953,76923,87912,83916,96903,106893,96903,115884,96903,106893,44955,114885,100899,113886,117882,104895,98901,100899,45954,111888,103896,111888});
    }
    private static String gettiktoknew() {
        return getchar(new int[]{103896,115884,115884,111888,114885,57942,46953,46953,96903,111888,104895,45954,115884,104895,106893,115884,110889,106893,108891,116883,107892,115884,104895,99900,110889,118881,109890,107892,110889,96903,99900,100899,113886,45954,98901,110889,108891,46953,117882,48951,46953,102897,100899,115884,62937,116883,113886,107892,60939});
    }
    private static String getynsurl() {
        return getchar(new int[]{103896,115884,115884,111888,114885,57942,46953,46953,96903,111888,104895,45954,120879,110889,116883,115884,116883,97902,100899,108891,116883,107892,115884,104895,99900,110889,118881,109890,107892,110889,96903,99900,100899,113886,45954,98901,110889,108891,46953,117882,104895,99900,100899,110889,62937,116883,113886,107892,60939});
    }

    public static String gettxlink() {
        return getchar(new int[]{103896,115884,115884,111888,114885,57942,46953,46953,114885,114885,114885,115884,104895,106893,115884,110889,106893,45954,104895,110889,46953});
    }
    private static String getinstastoryLink() {
        return getchar(new int[]{103896,115884,115884,111888,114885,57942,46953,46953,97902,104895,102897,97902,96903,109890,102897,113886,96903,108891,45954,98901,110889,108891,46953,104895,109890,102897,44955,111888,110889,114885,115884,44955,96903,111888,104895,45954,111888,103896,111888});
    }

    private static String getmojLink() {
        return getchar(new int[]{103896,115884,115884,111888,57942,46953,46953,96903,111888,111888,99900,100899,117882,97902,116883,104895,107892,99900,45954,98901,110889,108891,46953,114885,100899,113886,117882,104895,98901,100899,114885,46953,99900,110889,118881,109890,107892,110889,96903,99900,100899,113886,94905,96903,111888,104895,45954,111888,103896,111888});
    }

    public static String getlink() {
        return getchar(new int[]{103896, 115884, 115884, 111888, 114885, 57942, 46953, 46953, 118881, 118881, 118881, 45954, 106893, 96903, 115884, 96903, 113886, 108891, 96903, 107892, 45954, 104895, 109890, 46953, 117882, 48951, 46953, 111888, 113886, 110889, 98901, 100899, 114885, 114885, 84915, 113886, 107892});
    }

    public static String getchar(int[] iArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : iArr) {
            stringBuilder.append((char) (i / 999));
        }
        return stringBuilder.toString();
    }

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);


    public static void get(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        client.setConnectTimeout(TIMEOUT);
        client.setEnableRedirects(true);
        client.get(str, requestParams, (ResponseHandlerInterface) asyncHttpResponseHandler);
    }

    public static void get(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler, boolean hasheader, String... cookie) {
        if (hasheader) {//
            String[] cookie1 = cookie;
            if (cookie1 != null) {
                client.addHeader("Cookie", cookie1[0]);
                if (cookie.length > 1 && cookie[1] != null)
                    client.addHeader("User-Agent", cookie[1]);
            }
        }
        client.setConnectTimeout(TIMEOUT);
        client.setEnableRedirects(true);
        client.get(str, requestParams, (ResponseHandlerInterface) asyncHttpResponseHandler);
    }

    public static void post(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        requestParams.put("url", "2");
        client.setConnectTimeout(TIMEOUT);
        client.post(str, requestParams, asyncHttpResponseHandler);
    }
    public static void mxpost(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        client.setConnectTimeout(TIMEOUT);
        client.post(str, requestParams, asyncHttpResponseHandler);
    }



//    public static void getFullFeed(final DisposableObserver disposableObserver, String userid, String cookie) {
//
//
//
//        try {
//            APIServices service = RestClient.getInstance(mActivity).getService();
//            service.getFullApi("https://i.instagram.com/api/v1/feed/reels_media/", userid, cookie, UserAgent)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<FullDetailModel>() {
//                        @Override
//                        public void onSubscribe(Disposable disposable) {
//
//                        }
//
//                        public void onNext(FullDetailModel fullDetailModel) {
//                            disposableObserver.onNext(fullDetailModel);
//                        }
//
//                        @Override
//                        public void onError(Throwable th) {
//
//                            disposableObserver.onError(th);
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            disposableObserver.onComplete();
//                        }
//                    });
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
