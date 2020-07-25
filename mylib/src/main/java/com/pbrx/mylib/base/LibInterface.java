package com.pbrx.mylib.base;

import android.app.Dialog;

/**
 * Created by Iverson on 2016/12/23 下午7:58
 * 此类用于：
 */

public interface LibInterface {

    /**
     * 数据请求发生网络异常时调用。
     */
    void showNetworkException();

    /**
     * 发生Error但又不是网络异常时调用。
     */
    void showUnknownException();

    /**
     * 数据成功返回但不是预期值时调用。
     */
    void showDataException(String msg);

    /**
     * 显示加载完成的UI(e.g. 复位 Ultra-Ptr头部或尾部)
     */
    void showLoadingComplete();

    /**
     * 显示进度条对话框。
     */
    Dialog showLoadingDialog();

    /**
     * 关闭进度条对话框。
     */
    void dismissLoadingDialog();
    /**
     * 关闭进度条对话框。
     */
    void toLogin();

}
