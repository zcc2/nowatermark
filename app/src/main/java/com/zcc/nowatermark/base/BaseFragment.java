package com.zcc.nowatermark.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbrx.mylib.base.LibBaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Iverson on 2017/10/14 下午7:24
 * 此类用于：
 */

public abstract class BaseFragment extends LibBaseFragment {

    protected Unbinder mUnbinder;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mUnbinder = ButterKnife.bind(this,rootView);
        super.onCreateView(inflater, container, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,rootView);
        return  rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView.showContentView();
        initViews();
    }


    protected abstract void initViews();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder!= null){
            mUnbinder.unbind();
        }
    }
    public void goActivity(Class<?> clazz){
        Intent intent=new Intent(getActivity(),clazz);
        startActivity(intent);
    }
    protected void createLayoutManger(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void goLogin() {
//        try {
//            BaseActivity activity= (BaseActivity) getActivity();
//            activity.showRelogin();
//        }catch (Exception e){
//            LogUtil.e("登录异常",e.toString());
//        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }
    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    protected  void loadData(){};
    public void onResume() {
        super.onResume();
        isViewCreated = true;
        lazyLoad();
    }
}
