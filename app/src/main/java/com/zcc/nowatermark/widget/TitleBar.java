package com.zcc.nowatermark.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcc.nowatermark.R;
import com.zcc.nowatermark.base.utils.AppUtils;


public class TitleBar extends LinearLayout{
    private TextView tvLeftView;
    private TextView tvRightView;
    private TextView tvPot;
    private ImageView ivRightShare;
    private ImageView imgback;
    private ImageView imgFriend;
    private RelativeLayout titleTb;
    private RelativeLayout rlFriend;
    private TextView tvFriend;

    public TextView getTvRightView() {
        return tvRightView;
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = LayoutInflater.from(context).inflate(R.layout.include_title, null);
        tvLeftView = inflate.findViewById(R.id.tv_left_text);
        imgback = inflate.findViewById(R.id.img_back);
        tvRightView = inflate.findViewById(R.id.tv_right_text);
        ivRightShare = inflate.findViewById(R.id.iv_right_share);
        titleTb = inflate.findViewById(R.id.title_bar_bg);
        rlFriend = inflate.findViewById(R.id.rl_friend);
        tvFriend = inflate.findViewById(R.id.tv_friend_num);
        tvPot = inflate.findViewById(R.id.tv_red_pot);
        imgFriend = inflate.findViewById(R.id.iv_friends);
        tvLeftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.leftClick();
                }
            }
        });
        imgback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.leftClick();
                }
            }
        });
        tvRightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.rightClick();
                }
            }
        });
        ivRightShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onShareClickListener != null) {
                    onShareClickListener.shareClick();
                }
            }
        });
        addView(inflate);

    }

    public void setShareVisible(boolean shareVisible) {
        if(shareVisible){
            tvRightView.setVisibility(GONE);
        }
        ivRightShare.setVisibility(shareVisible ? VISIBLE : GONE);
    }
    public void setBackVisible(int shareVisible) {
        imgback.setVisibility(shareVisible);
    }

    public void setLeftText(String text) {
        this.tvLeftView.setText(text);
    }
    public void setTvPot(String text) {
        if(AppUtils.checkStrsNoNull(text)){
            tvPot.setVisibility(VISIBLE);
        }else {
            tvPot.setVisibility(GONE);
        }
    }

    public void setRightText(String text) {
        this.tvRightView.setText(text);
    }
    public void setRightTextColor(int textColor) {
        this.tvRightView.setTextColor(textColor);
    }
    public void setRightTextBg(int textBg,String text) {
        this.tvRightView.setBackgroundResource(textBg);
        this.tvRightView.setText(text);
    }
    private OnItemClickListener onItemClickListener = null;
    private OnShareClickListener onShareClickListener = null;
    public void setRightImage(int res,OnClickListener onClickListener) {
        ivRightShare.setVisibility(VISIBLE);
        this.ivRightShare.setImageResource(res);
        ivRightShare.setOnClickListener(onClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnFriendClickListener(OnClickListener onItemClickListener) {
        rlFriend.setVisibility(VISIBLE);
        imgFriend.setOnClickListener(onItemClickListener);
    }
    public void setBadgeFriendNum(Context mContext,int num) {
       if(num>0){
           tvFriend.setVisibility(VISIBLE);
           tvFriend.setText(num+"");
       }else {
           tvFriend.setVisibility(GONE);
       }

    }

    public interface OnItemClickListener {
        void leftClick();

        void rightClick();
    }

    public void setWhiteTvLeftView(){
        if(tvLeftView!=null){
            titleTb.setBackground(getResources().getDrawable(R.mipmap.wallet_top));
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_back_w);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvLeftView.setCompoundDrawables(drawable,null,null,null);
            tvLeftView.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    public interface OnShareClickListener {
        void shareClick();
    }
}
