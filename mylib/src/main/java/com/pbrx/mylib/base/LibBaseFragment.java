package com.pbrx.mylib.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.pbrx.mylib.R;
import com.pbrx.mylib.constant.LibConstant;
import com.pbrx.mylib.permission.RequestResult;
import com.pbrx.mylib.util.BaseTools;
import com.pbrx.mylib.util.CustomToast;
import com.pbrx.mylib.util.LogUtil;
import com.pbrx.mylib.util.StateLayout;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;
import java.util.concurrent.TimeUnit;


import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Iverson on 2017/10/14 下午6:27
 * 此类用于：
 */

public abstract class LibBaseFragment extends Fragment implements LibInterface {


    protected FragmentActivity mContext;
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private RequestResult mRequestResult;
    protected StateLayout rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LibBaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =new StateLayout(getContext());
        //给自定义显示界面 增添第四种界面 即正常界面
        rootView.setContentView(getLayoutId());
        return rootView;
    }

    protected abstract int getLayoutId();
    protected abstract void goLogin();

    @Override
    public void toLogin() {
        goLogin();
    }

    @Override
    public void showLoadingComplete() {
        //Empty implementation
        dismissLoadingDialog();
    }

    @Override
    public Dialog showLoadingDialog() {
        return showLoadingDialog(getResources().getString(R.string.defult_load_content));
    }


    protected Dialog showLoadingDialog(String content) {
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

        mProgressDialog = ProgressDialog.show(mContext, null, content, true, true);
        return mProgressDialog;
    }

    @Override
    public void dismissLoadingDialog() {
        if (mProgressDialog==null || (!mProgressDialog.isShowing())){
            return ;
        }
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }
    @Override
    public void showDataException(String msg) {
        toastShort(msg);
    }

    @Override
    public void showNetworkException() {
//        toastShort(R.string.msg_network_error);
//        rootView.showFailView();
    }

    @Override
    public void showUnknownException() {
//        toastShort(R.string.msg_unknown_error);
    }

    protected void toastShort(@StringRes int msg){
        CustomToast.makeCustomText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastShort(@NonNull String msg){
        CustomToast.makeCustomText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 事件点击前检查
     * @param view 点击的view
     * @param action1 检查通过处理
     * @param func1 检查条件
     * @param windowDuration 时长
     * @param unit 时间标准
     */
    protected void filterClick(View view, Func1<Void, Boolean> func1, Action1<Void> action1, long windowDuration, TimeUnit unit){
        RxView.clicks(view).throttleFirst(windowDuration, unit)
                .filter(func1)
                .subscribe(action1);
    }

    /**
     * 快速点击
     * @param view
     * @param action1
     */
    protected void onClick(Object view,Action1<Void> action1){
        View clickView = null;
        if(view instanceof View){
            clickView = (View) view;
        }else {
            clickView = mContext.findViewById((Integer) view);
        }
        subscribeClick(clickView,action1, LibConstant.VIEW_THROTTLE_SHORT_TIME,TimeUnit.MILLISECONDS);
    }

    /**
     * 短时间延迟
     */
    protected void onShortClick(Object view, Action1<Void> action1){
        View clickView = null;
        if(view instanceof View){
            clickView = (View) view;
        }else {
            clickView = mContext.findViewById((Integer) view);
        }
        subscribeClick(clickView,action1,LibConstant.VIEW_THROTTLE_MIDDLING_TIME,TimeUnit.SECONDS);
    }

    /**
     * 长时间延迟
     */
    protected void onLongClick(Object view, Action1<Void> action1){
        View clickView = null;
        if(view instanceof View){
            clickView = (View) view;
        }else {
            clickView = mContext.findViewById((Integer) view);
        }
        subscribeClick(clickView,action1,LibConstant.VIEW_THROTTLE_LONG_TIME,TimeUnit.SECONDS);
    }


    /**
     * 点击事件处理
     * @param view
     * @param action1
     */
    private void subscribeClick(View view, Action1<Void> action1,long windowDuration, TimeUnit unit){
        RxView.clicks(view)
                .throttleFirst(windowDuration,unit)
                .subscribe(action1);
    }


    @Override
    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
    }
    /**
     * 请求权限
     * @param requestResult 处理权限
     */
    protected void requestPremission(RequestResult requestResult, String... permissions){
        this.mRequestResult = requestResult;
        AndPermission.with(this)
                .permission(permissions)
                //被拒绝时
                .rationale(mRationale)
                //允许通过
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        mRequestResult.successResult();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //当总是被拒绝
                        mRequestResult.failuerResult();
                        if (AndPermission.hasAlwaysDeniedPermission(getActivity(), permissions)) {
                            final SettingService settingService = AndPermission.permissionSetting(getActivity());
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("温馨提示")
                                    .setMessage("您已拒绝手机权限，没有权限，无法流畅的体验所用功能，点击\"去设置\"按钮授权给我吧！")
                                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            // 如果用户同意去设置：
                                            settingService.execute();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            // 如果用户不同意去设置：
                                            settingService.cancel();
                                        }
                                    }).show();
                        }
                    }
                })
                .start();
    }
    private Rationale mRationale = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions,
                                  final RequestExecutor executor) {
            // 这里使用一个Dialog询问用户是否继续授权。
            // 提示用户再次给予授权。自定义对话框。
            new AlertDialog.Builder(getActivity())
                    .setTitle("温馨提示")
                    .setMessage("您已拒绝获取权限，点击\"确认\"按钮授权给我吧！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            // 如果用户继续：
                            executor.execute();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            // 如果用户中断：
                            executor.cancel();
                        }
                    }).show();
        }
    };
}
