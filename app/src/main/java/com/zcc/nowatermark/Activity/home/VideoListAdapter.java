package com.zcc.nowatermark.Activity.home;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcc.nowatermark.R;
import com.zcc.nowatermark.base.readapter.recyclerview.CommonAdapter;
import com.zcc.nowatermark.base.readapter.recyclerview.base.ViewHolder;
import com.zcc.nowatermark.bean.EditItem;

import java.util.List;

/**
 */

public class VideoListAdapter extends CommonAdapter<EditItem> {
    private VideoItemListener mListener;
    private int height;
    public VideoListAdapter(Context context, List<EditItem> datas,int height) {
        super(context, R.layout.item_edit, datas);
        this.height=height;
    }

    @Override
    protected void convert(ViewHolder holder, EditItem editItem, int position) {
        ImageView iv=holder.getView(R.id.iv_item);
        TextView tv=holder.getView(R.id.tv_item);
        iv.setImageResource(editItem.imgRes);
        tv.setText(editItem.text);
    }

    public interface VideoItemListener {
        void onItemClick(int pos);
    }

    public void setListener(VideoItemListener listener) {
        this.mListener = listener;
    }
}
