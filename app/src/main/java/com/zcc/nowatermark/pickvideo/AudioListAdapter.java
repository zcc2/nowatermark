package com.zcc.nowatermark.pickvideo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcc.nowatermark.R;
import com.zcc.nowatermark.pickvideo.beans.AudioFile;

import java.util.ArrayList;

/**
 * Created by Vincent Woo
 * Date: 2018/2/27
 * Time: 10:25
 */

public class AudioListAdapter extends BaseAdapter<AudioFile, AudioListAdapter.FolderListViewHolder> {
    private AudioListListener mListener;

    public AudioListAdapter(Context ctx, ArrayList<AudioFile> list) {
        super(ctx, list);
    }

    @Override
    public FolderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.audio_list_item,
                parent, false);
        return new FolderListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FolderListViewHolder holder, int position) {
        holder.mTvName.setText(mList.get(position).getName());
        holder.mTvDuration.setText(mList.get(position).getDuration()+"");
        holder.mTvTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onTryClick(mList.get(holder.getAdapterPosition()));
                }
            }
        });
        holder.mTvUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onUseClick(mList.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class FolderListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvDuration;
        private TextView mTvTry;
        private TextView mTvUse;

        public FolderListViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            mTvTry = (TextView) itemView.findViewById(R.id.tv_try_listen);
            mTvUse = (TextView) itemView.findViewById(R.id.tv_use);
        }
    }

    public interface AudioListListener {
        void onTryClick(AudioFile directory);
        void onUseClick(AudioFile directory);
    }

    public void setListener(AudioListListener listener) {
        this.mListener = listener;
    }
}
