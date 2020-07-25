package com.pbrx.mylib.base;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.pbrx.mylib.constant.LibConstant;
import com.pbrx.mylib.util.LogUtil;
import com.pbrx.mylib.util.SPUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by Iverson on 2016/12/23 下午9:00
 * 此类用于：
 */

public abstract class LibObserver<E extends LibResponse> implements Observer<E> {
    protected final String LOG_TAG = getClass().getSimpleName();

    private final LibInterface mUiInterface;

    public LibObserver(LibInterface baseUiInterface) {
        mUiInterface = baseUiInterface;
    }

    @Override
    public void onCompleted() {
        mUiInterface.showLoadingComplete();
    }

    @Override
    public void onError(Throwable throwable) {
        LogUtil.e("BaseObserver", "Request Error!");
        LogUtil.e("throwable", throwable.toString());
        mUiInterface.showNetworkException();
        handleError(throwable, mUiInterface, LOG_TAG);
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    public static void handleError(Throwable throwable, LibInterface mUiInterface, String LOG_TAG){
        mUiInterface.showLoadingComplete();
        if (throwable == null) {
            mUiInterface.showUnknownException();
            return;
        }
        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable instanceof SocketTimeoutException || throwable
                instanceof ConnectException || throwable instanceof UnknownHostException) {
            mUiInterface.showNetworkException();
        } else if ((throwable instanceof JsonSyntaxException) || (throwable instanceof
                NumberFormatException) || (throwable instanceof MalformedJsonException)) {
            mUiInterface.showDataException("数据解析出错");
            LogUtil.e(LOG_TAG, "数据解析出错");
        } else if ((throwable instanceof HttpException)) {
//            mUiInterface.showDataException("服务器错误(" + ((HttpException) throwable).code()+")");
            //自动上报这个异常
            LogUtil.e(LOG_TAG, "Error while performing response!");
        } else if (throwable instanceof NullPointerException) {
            mUiInterface.showDataException("网络异常，请稍后再试");
            //自动上报这个异常
            LogUtil.e( LOG_TAG, "Error while performing response!"+throwable.toString());
        } else {
            mUiInterface.showUnknownException();
        }
    }

    @Override
    public void onNext(E response) {
        //401：NotAuthorized(401, "没有权限"),
        //700：ANOTHERPLACELOGIN(700, "当前令牌已失效，请重新登录"),
        //900：EXPIRETIME(900, "令牌过期了,请重新登录"),
        //901：JWTERROR(901, "JWT错误，请重新登录")
        LogUtil.e("mrl",response.getData().toString());
        if(200 == response.getCode()){
            onSuccess(response);
        }else if(401==response.getCode()||700==response.getCode()||900==response.getCode()||901==response.getCode()){
            SPUtils.remove(LibConstant.token);
//            mUiInterface.showDataException(response.getMsg());
           mUiInterface.toLogin();
        } else {
            onDataFailure(response);
        }
    }

    public abstract void onSuccess(E response);

    protected void onDataFailure(E response) {
        String msg = response.getMsg();
        LogUtil.w(LOG_TAG, "request data but get failure:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            mUiInterface.showDataException(response.getMsg());
        }
        else{
            mUiInterface.showUnknownException();
        }
    }

    /**
     * Create a new silence, non-leak observer.
     */
    public static <T> Observer<T> silenceObserver(){
        return new Observer<T>() {
            @Override
            public void onCompleted() {
                //Empty
            }

            @Override
            public void onError(Throwable e) {
                //Empty
            }

            @Override
            public void onNext(T t) {
                //Empty
            }
        };
    }
}
