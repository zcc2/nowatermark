package com.pbrx.mylib.net;

import android.support.multidex.BuildConfig;

import com.pbrx.mylib.constant.LibConstant;
import com.pbrx.mylib.util.SPUtils;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Iverson on 2016/12/23 下午11:10
 * 此类用于：
 */

public class LibNetManager {

    private static final int CONNECT_TIME_OUT = 60;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;
    private Retrofit mRetrofit;

    public static LibNetManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final LibNetManager INSTANCE = new LibNetManager();
    }

    private LibNetManager() {
    }

    public <S> S create(Class<S> service,int type) {
        String baseUrl=null;
        switch (type){
            case 0:
                baseUrl=LibConstant.BaseUrl;
                break;
            case 1:
                baseUrl=LibConstant.BaseUrl1;
                break;
            case 2:
                baseUrl=LibConstant.BaseUrl2;
                break;
            case 3:
                baseUrl=LibConstant.BaseUrl3;
                break;
        }
        return create(service, baseUrl);
    }

    /**
     * 生成网络请求链接
     *
     * @param service
     * @param url
     * @param <S>
     * @return
     */
    public <S> S create(Class<S> service, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder clientBuilder = okHttpClient.newBuilder()
                //添加网络通用请求信息, see http://stackoverflow.com/a/33667739
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token = (String) SPUtils.get(LibConstant.token, "");
                        Request request = chain.request().newBuilder()
//                                .addHeader("x-access-token", token)
                                .addHeader("Authorization", token)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
//                .proxy(Proxy.NO_PROXY)
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addNetworkInterceptor(logging);
        }

        okHttpClient = clientBuilder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .build();

        return mRetrofit.create(service);
    }
}
