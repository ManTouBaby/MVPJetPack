package com.hrw.mvplibrary.net;

import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.webkit.WebSettings;

import com.hrw.mvplibrary.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/19 11:05
 * @desc:
 */
public class RetrofitHelper {
    private static RetrofitHelper mRetrofitHelper;
    private static Retrofit mRetrofit;

    private static long mConnectTimeOut = 10;
    private static long mWriteTimeOut = 10;
    private static long mReadTimeOut = 10;
    private static String mBaseUrl;
    private static Application mApplication;


    public static void init(@NonNull String baseUrl, Application application) {
        init(10, 10, 10, baseUrl, application);
    }

    public static void init(long connectTimeOut, long writeTimeOut, long readTimeOut, @NonNull String baseUrl, Application application) {
        mConnectTimeOut = connectTimeOut;
        mWriteTimeOut = writeTimeOut;
        mReadTimeOut = readTimeOut;
        mBaseUrl = baseUrl;
        mApplication = application;

        if (mRetrofit == null) {
            synchronized (RetrofitHelper.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(createOKHttpClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static RetrofitHelper getInstance() {
        if (mRetrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (mRetrofitHelper == null) {
                    mRetrofitHelper = new RetrofitHelper();
                }
            }
        }
        return mRetrofitHelper;
    }

    private static OkHttpClient createOKHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(mConnectTimeOut, TimeUnit.SECONDS)
                .writeTimeout(mWriteTimeOut, TimeUnit.SECONDS)
                .readTimeout(mReadTimeOut, TimeUnit.SECONDS)
                .addInterceptor(new Mine())
                .build();
    }

    public <T> T createService(Class<T> aClass) {
        return mRetrofit.create(aClass);
    }

    static class Mine implements Interceptor {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .removeHeader("User-Agent")//移除旧的
                    .addHeader("User-Agent", WebSettings.getDefaultUserAgent(mApplication))//添加真正的头部
                    .build();
            Response response = chain.proceed(request);
            try {
                assert response.body() != null;
                final String responseString = new String(response.body().bytes());
                LogUtil.d("OkHttp-NET-Interceptor", "Response: " + responseString);
//                String newResponseString = editResponse(responseString);
                LogUtil.d("OkHttp-NET-Interceptor", "Response edited: " + responseString);
                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), responseString))
                        .build();

            } catch (Exception ex) {
                return response;
            }
        }
    }

}
