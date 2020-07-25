package com.zcc.nowatermark.base.readapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/3/11.
 */
public abstract class SimplePagerAdapter<T> extends PagerAdapter {
    public abstract View initView(ViewGroup container, int position);

//    private List<T> mList;
//    private Context mContext;
//    private int layoutId;
//
//    public SimplePagerAdapter(Context context, List<T> list, int layoutId) {
//        mList = list;
//        mContext = context;
//        this.layoutId = layoutId;
//    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = initView(container, position);
        view.setClickable(true);
        container.addView(view);
        if (mOnItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
//        convert(mList.get(position), position);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

//    @Override
//    public int getCount() {
//        return mList == null ? 0 : mList.size();
//    }

//    public abstract void convert(T t, int position);

}
