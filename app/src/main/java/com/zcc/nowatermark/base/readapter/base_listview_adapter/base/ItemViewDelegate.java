package com.zcc.nowatermark.base.readapter.base_listview_adapter.base;


import com.zcc.nowatermark.base.readapter.base_listview_adapter.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);


}
