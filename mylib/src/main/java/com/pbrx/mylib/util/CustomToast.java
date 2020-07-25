package com.pbrx.mylib.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.pbrx.mylib.R;

import java.lang.reflect.Field;

/**
 * Created by Iverson on 2016/12/23 下午8:20
 * 此类用于：吐司工具类
 */

public class CustomToast extends Toast {
    private Toast lastInstance;
    private Context context ;
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public CustomToast(Context context) {
        super(context);
        this.context = context.getApplicationContext();
    }

    public static CustomToast makeCustomText(Context context, CharSequence text,
                                             int duration) {
        context = context.getApplicationContext();
        CustomToast toast = new CustomToast(context);
        /*设置Toast的位置*/
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        /*让Toast显示为我们自定义的样子*/
        toast.setView(getToastView(context, text));
        return toast;
    }

    @Override
    public void show() {
        if (lastInstance != null) {
            lastInstance.cancel();
        }
        super.show();
        lastInstance = this;
    }

    public static CustomToast makeCustomText(Context context, int text,
                                             int duration) {
        return makeCustomText(context, context.getString(text), duration);
    }
    //获取toast布局文件和位置
    public static View getToastView(Context context, CharSequence msg) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.custom_toast, null);
        TextView text = (TextView) v.findViewById(R.id.toast_text);
        text.setText(msg);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(params);
        return v;
    }

    public static int getWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private static Object getField(Class<?> clz, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = clz.getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
