package com.pbrx.mylib.base;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Iverson on 2016/12/23 下午6:28
 * 此类用于：处理请求和返回结果的基类
 */

public class LibPresenter<V extends LibInterface > {

    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private V mUiInterface;

    protected LibPresenter(V uiInterface) {
        this.mUiInterface = uiInterface;
    }

    public void unsubscribeTasks() {
        mCompositeSubscription.unsubscribe();
    }

    /**
     * 每次发起时加入CompositeSubscription,这个方法应该内部调用，所以使用protected.
     */
    protected void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    protected V getUiInterface() {
        if (mUiInterface == null) {
            throw new IllegalStateException("UiInterface is not initialized correctly.");
        }
        return mUiInterface;
    }

    /**
     * Usage:
     * Observable.compose(applySchedulers)
     */
    protected final <T> Observable.Transformer<T, T> applyAsySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
