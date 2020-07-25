package com.zcc.nowatermark.base.readapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class RecycleViewHolder extends RecyclerView.ViewHolder {
    private static View mConvertView;
    private final SparseArray<View> mViews;
    private Context mContext;

    public RecycleViewHolder(View itemView, Context context) {
        this(itemView);
        mContext = context;
    }

    private RecycleViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        this.mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static RecycleViewHolder get(Context context, View convertView,
                                        ViewGroup parent, int layoutId) {
        RecycleViewHolder holder = null;
        if (convertView == null) {
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            holder = new RecycleViewHolder(mConvertView);
        } else {
            holder = (RecycleViewHolder) convertView.getTag();

        }
        return holder;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecycleViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecycleViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public RecycleViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public RecycleViewHolder setImageByUrl(int viewId, String url) {
        Glide.with(mContext).load(url).into((ImageView) getView(viewId));
//        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url, (ImageView) getView(viewId));
        return this;
    }


}
