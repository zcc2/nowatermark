package com.pbrx.mylib.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbrx.mylib.R;


/**
 * 封装了4种状态的View：正在加载、加载失败、没有数据、正常界面
 * @author dzl
 */
public class StateLayout extends FrameLayout {

    private  View ivBack;
    private TextView tvback;
    private  View rlTitle;
    private View loadingView;
    private View failView;
    private View emptyView;
    private View contentView;
    /** 包含了3种状态(正在加载、加载失败、没有数据) 的一个容器 */
    private FrameLayout container;

    public StateLayout(Context context) {
        super(context);

        // 创建出来的容器已经包含了3种状态：正在加载、加载失败、没有数据
        container = (FrameLayout) View.inflate(this.getContext(), R.layout.state_layout, null);
        // 查找出3种状态对应的View
        loadingView = container.findViewById(R.id.loadingView);
        failView = container.findViewById(R.id.failView);
        emptyView = container.findViewById(R.id.emptyView);
        rlTitle = container.findViewById(R.id.rl_title);
        ivBack = container.findViewById(R.id.img_back);
        tvback = container.findViewById(R.id.tv_left_text);
        showLoadingView();

        this.addView(container);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    /** 显示“正在加载。。。” */
    public void showLoadingView() {
        showView(loadingView);
    }

    /** 显示“加载失败” */
    public void showFailView() {
        showView(failView);
    }

    /** 显示“加载为空” */
    public void showEmptyView() {
        showView(emptyView);
    }

    /** 显示“正常加载到数据”的界面的View */
    public void showContentView() {
        showView(contentView);
    }

    /** 设置“正常状态的View” */
    public void setContentView(int layoutResId) {
        View contentView = View.inflate(getContext(), layoutResId, null);
        this.setContentView(contentView);
    }

    /** 设置“正常状态的View” */
    public void setContentView(View contentView) {
        this.contentView = contentView;
        container.addView(contentView);
        contentView.setVisibility(View.GONE);   // 默认不显示，默认显示的是LoadingView

    }

    /** 指示指定的View，并隐藏其它的View */
    private void showView(View view) {
        // System.out.println("childCount = " + container.getChildCount());
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            child.setVisibility(child == view ? View.VISIBLE : View.GONE);
        }
    }
    public void setNetErrorClick(OnClickListener onClickListener){
        failView.setOnClickListener(onClickListener);
    }
    public void setBackClick(OnClickListener onClickListener){
        ivBack.setOnClickListener(onClickListener);
        tvback.setOnClickListener(onClickListener);
    }
    public void setLeftText(String text){
        rlTitle.setVisibility(VISIBLE);
        tvback.setText(text);
    }

}
