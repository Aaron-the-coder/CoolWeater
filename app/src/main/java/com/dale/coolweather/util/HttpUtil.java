package com.dale.coolweather.util;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dale on 2017/8/8.
 * http://www.guolin.tech/api/weather/?cityid=CN101190401&&key=fc286968c1c64dbf938b23cb4d873361k
 */

public class HttpUtil {

    private static final String TAG = "HttpUtil";

    public static void sendOkhttpRequest(String url, Callback callback) {
        LogUtil.e(TAG, "请求的url为:" + url);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
