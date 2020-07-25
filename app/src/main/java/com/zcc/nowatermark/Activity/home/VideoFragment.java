package com.zcc.nowatermark.Activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zcc.nowatermark.Activity.VideoEditActivity;
import com.zcc.nowatermark.R;
import com.zcc.nowatermark.base.BaseFragment;
import com.zcc.nowatermark.base.readapter.recyclerview.MultiItemTypeAdapter;
import com.zcc.nowatermark.bean.EditItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by zcc on 2019/3/17.
 */
public class VideoFragment extends BaseFragment {
    public static final String TAG = "VideoFragment";
    @BindView(R.id.banner)
    ConvenientBanner convenBanner;
    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.cardview1)
    CardView mCardview1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.cardview2)
    CardView mCardview2;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ArrayList<EditItem> mEditItemList;

    public static VideoFragment newInstance() {

        Bundle args = new Bundle();

        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_video;
    }


    @Override
    protected void initViews() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.first1);
        list.add(R.drawable.first2);
        requestLocalBanner(convenBanner, list);
        convenBanner.setCanLoop(true);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mEditItemList = new ArrayList<>();
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.time_cut), null));
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.size_cut), null));
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.compose), null));
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.audio_dum), null));
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.reverse), null));
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.speed), null));
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.video_dum), null));
        mEditItemList.add(new EditItem(R.drawable.cut,
                getResources().getString(R.string.image_to_video), null));
        VideoListAdapter videoListAdapter=new VideoListAdapter(getActivity(), mEditItemList,getActivity().getWindowManager().getDefaultDisplay().getHeight());
        mRecyclerView.setAdapter(videoListAdapter);
        videoListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent=null;
                switch (position){
                    case 0:

                        break;
                }
                if(intent!=null){
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @OnClick({R.id.cardview1, R.id.cardview2})
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.cardview1:

                break;
            case R.id.cardview2:
                intent = new Intent(getActivity(), VideoEditActivity.class);
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }

    }
    private void requestLocalBanner(ConvenientBanner convenientBanner, final List<Integer> sBanners) {
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new NetLocalBannerHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.banner_item;
            }
        }, sBanners)
                .setPageIndicator(new int[]{R.drawable.circle_gray_dot, R.drawable.cirle_black_dot})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });

    }
    class NetLocalBannerHolderView extends Holder<Integer> {
        ImageView imageView;

        public NetLocalBannerHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = (ImageView) itemView;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        @Override
        public void updateUI(Integer data) {
            imageView.setImageResource(data);
        }
    }
}
