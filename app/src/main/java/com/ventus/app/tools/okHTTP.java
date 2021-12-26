package com.ventus.app.tools;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
public class okHTTP {
    OkHttpClient okHttpClient = new OkHttpClient();

    public void run(String url, Callback callback) {

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}