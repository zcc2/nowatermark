package com.zcc.nowatermark.Activity;


import android.content.Context;

import com.zcc.nowatermark.R;
import com.zcc.nowatermark.base.readapter.recyclerview.CommonAdapter;
import com.zcc.nowatermark.base.readapter.recyclerview.base.ViewHolder;
import com.zcc.nowatermark.bean.EditItem;

import java.util.List;

/**
 *
 */
public class TextNewAdapter extends CommonAdapter<EditItem> {
    public TextNewAdapter(Context context, List<EditItem> datas) {
        super(context, R.layout.item_edit, datas);
    }
    @Override
    protected void convert(ViewHolder holder, EditItem baseBean, int position) {
          holder.setText(R.id.tv_item,"条目"+position);
    }

}
