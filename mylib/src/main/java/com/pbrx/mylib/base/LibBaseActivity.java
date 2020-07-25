package com.pbrx.mylib.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;
import java.util.concurrent.TimeUnit;


import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Iverson on 2017/10/10 上午11:21
 * 此类用于：
 */

public abstract class LibBaseActivity extends AppCompatActivity implements LibInterface{

    protected Context mContext;
    protected ProgressDialog mProgressDialog;
    protected RequestResult mRequestResult;
    protected TextView mTvTitleLeft;
    protected ImageButton mBtTitleLeft;
    protected TextView mTvTitleRight;
    protected TextView mTvTitleName;
    protected ImageButton mBtTitleRight;
    public StateLayout rootView;
    private Button bt;
    protected  int process=100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView =new StateLayout(this);
        rootView.setContentView(getLayoutId(savedInstanceState));
        setContentView(rootView);
        rootView.showContentView();
        mContext = this;
        LibApplication.getInstance().addActivity(this);
        rootView.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    protected  void setBtEnable(Button button){
        bt=button;
        if(button!=null){
            button.setEnabled(true);
        }

    }

    @Override
    public void showDataException(String msg) {
        toastShort(msg);
        LogUtil.e("showDataException==",msg);
        setBtEnable(bt);
        process=-1;
    }

    @Override
    public void showNetworkException() {
//        toastShort(R.string.msg_network_error);
//        rootView.showFailView();
        setBtEnable(bt);
        process=-1;
    }

    @Override
    public void showUnknownException() {
        toastShort(R.string.msg_unknown_error);
        setBtEnable(bt);
        process=-1;
    }

    //填充布局
    protected abstract int getLayoutId(Bundle savedInstanceState);

    protected abstract void findView();

    protected abstract void initView();
    protected abstract void goLogin();

    /**
     * 左边的布局ImageButton
     * @param t
     * @param <T>
     */
    protected <T> void setBtGlobalLeft(T t){
        mBtTitleLeft = (ImageButton) findViewById(R.id.bt_global_title_left);
        if(t instanceof Integer){
            mBtTitleLeft.setImageResource((Integer) t);
        }else if(t instanceof Drawable){
            mBtTitleLeft.setImageDrawable((Drawable) t);
        }
        mBtTitleLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 右边的布局ImageButton
     * @param t
     * @param <T>
     */
    protected <T> void setBtGlobalRight(T t){
        mBtTitleRight = (ImageButton) findViewById(R.id.bt_global_title_right);
        if(t instanceof Integer){
            mBtTitleRight.setImageResource((Integer) t);
        }else if(t instanceof Drawable){
            mBtTitleRight.setImageDrawable((Drawable) t);
        }
        mBtTitleRight.setVisibility(View.VISIBLE);
    }

    /**
     * 左边的布局TextView
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalLeft(T t){
        mTvTitleLeft = (TextView) findViewById(R.id.tv_global_title_left);
        if(t instanceof Integer){
            mTvTitleLeft.setText((Integer) t);
        }else if(t instanceof String){
            mTvTitleLeft.setText((String)t);
        }
        mTvTitleLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 右边的布局TextView
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalRight(T t){
        mTvTitleRight = (TextView) findViewById(R.id.tv_global_title_right);
        if(t instanceof Integer){
            mTvTitleRight.setText((Integer) t);
        }else if(t instanceof String){
            mTvTitleRight.setText((String)t);
        }
        mTvTitleRight.setVisibility(View.VISIBLE);
    }


    /**
     * 中间的布局title的TextView
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalTitleName(T t){
        mTvTitleName = (TextView) findViewById(R.id.tv_global_title_name);
        if(t instanceof Integer){
            mTvTitleName.setText((Integer) t);
        }else if(t instanceof String){
            mTvTitleName.setText((String)t);
        }
        mTvTitleName.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LibApplication.getInstance().deleActvitity(this);
    }


    @Override
    public void showLoadingComplete() {
        //Empty implementation
        dismissLoadingDialog();
        setBtEnable(bt);
    }

    @Override
    public Dialog showLoadingDialog() {
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(this, null, "请稍候", true, true);
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

    protected void toastShort(@StringRes int msg){
        CustomToast.makeCustomText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastShort(@NonNull String msg){
        CustomToast.makeCustomText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toLogin() {
        goLogin();
    }

    /**
     * 事件点击前检查
     * @param view 点击的view
     * @param action1 检查通过处理
     * @param func1 检查条件
     * @param windowDuration 时长
     * @param unit 时间标准
     */
    protected void filterClick(View view, Func1<Void, Boolean> func1,Action1<Void> action1, long windowDuration, TimeUnit unit){
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
            clickView = findViewById((Integer) view);
        }
       subscribeClick(clickView,action1,LibConstant.VIEW_THROTTLE_SHORT_TIME,TimeUnit.MILLISECONDS);
    }

    /**
     * 短时间延迟
     */
    protected void onShortClick(Object view, Action1<Void> action1){
        View clickView = null;
        if(view instanceof View){
            clickView = (View) view;
        }else {
            clickView = findViewById((Integer) view);
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
            clickView = findViewById((Integer) view);
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



    /**
     * 指定销毁activity
     * @param cls
     */
    protected void finishActivityByName(@NonNull Class<? extends Activity> cls){
        for (Activity activity : LibApplication.getInstance().activities){
            if(activity.getClass().equals(cls)){
                activity.finish();
            }
        }
    }

    /**
     * 指定销毁activity
     */
    protected void finishAllActivity(){
        for (Activity activity : LibApplication.getInstance().activities){
            activity.finish();
        }
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
                        if (AndPermission.hasAlwaysDeniedPermission(LibBaseActivity.this, permissions)) {
                            final SettingService settingService = AndPermission.permissionSetting(LibBaseActivity.this);
                            new AlertDialog.Builder(LibBaseActivity.this)
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
            new AlertDialog.Builder(LibBaseActivity.this)
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


    //-----------------------点击空白区域隐藏键盘-------------------------------
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param view
     */
    protected void hideKeyboard(final View  view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    // 强制隐藏软键盘
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });

    }
    private void hideKeyboard(IBinder token){
        if (token != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                // 强制隐藏软键盘
                imm.hideSoftInputFromWindow(token, 0);
            }
        }
    }

    //-----------------------点击空白区域隐藏键盘-------------------------------
}
