package com.zcc.nowatermark.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.zcc.nowatermark.R;
import com.zcc.nowatermark.Utils.FFmpegUtils;
import com.zcc.nowatermark.Utils.FileUtils;
import com.zcc.nowatermark.base.BaseActivity;
import com.zcc.nowatermark.base.utils.DisplayUtil;
import com.zcc.nowatermark.base.utils.StaticFinalValues;
import com.zcc.nowatermark.pickvideo.VideoPickActivity;
import com.zcc.nowatermark.pickvideo.beans.VideoFile;
import com.zcc.nowatermark.widget.GetDialog;
import com.zcc.nowatermark.widget.MCustomZoomView;
import com.zcc.nowatermark.widget.TitleBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import VideoHandle.OnEditorListener;
import butterknife.BindView;
import butterknife.OnClick;

public class VideoEditActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleBar mTitle;
    @BindView(R.id.surface)
    SurfaceView mSurface;
    @BindView(R.id.bt_submit)
    Button mBtSubmit;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    @BindView(R.id.zoomView)
    MCustomZoomView zoomView;
    private MediaPlayer player;
    private String mVideoPath;
    private SurfaceHolder holder;
    private String mVideoRotation;
    private int mVideoWidth;
    private int mVideoHeight;
    private int mVideoDuration;
    private String mMInputVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_video_edit2;
    }

    @Override
    protected void findView() {
        Intent intent = new Intent(activity, VideoPickActivity.class);
        intent.putExtra(StaticFinalValues.MAX_NUMBER, 1);
        intent.putExtra(IS_NEED_FOLDER_LIST, true);
        startActivityForResult(intent, 111);
        mTitle.setLeftText("Remove WaterMark");
        mTitle.setRightText("Done");
        mTitle.setOnItemClickListener(new TitleBar.OnItemClickListener() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {
                Log.d("xxx", "x: " + zoomView.x);
                Log.d("xxx", "y: " + zoomView.y);
                Log.d("xxx", "height: " + zoomView.height);
                Log.d("xxx", "width: " + zoomView.width);
                Log.d("xxx", "mSurface.getX===: " + mSurface.getX());
                Log.d("xxx", "mSurface.getHeight===: " + mSurface.getHeight());
                Log.d("xxx", "mSurface.getY()==== " + mSurface.getY());
                Log.d("xxx", "mSurface.getWidth()==== " + mSurface.getWidth());
                float videoY = mSurface.getY() - DisplayUtil.dp2px(activity, 45);
                if (zoomView.x < mSurface.getX()) {
                    return;
                }
                float xIn = mVideoHeight * (zoomView.x - mSurface.getX()) / mSurface.getHeight();
                float yIn = mVideoHeight * (zoomView.y - videoY) / mSurface.getHeight();
                float width = zoomView.width * mVideoWidth / mSurface.getWidth();
                float height = zoomView.height * mVideoHeight / mSurface.getHeight();
                Log.d("xxx===", "mSur= " + xIn+"---"+yIn+"---"+width+"---"+height+"---");
                storeNoMark((int) xIn,(int)yIn,(int)width,(int)height);

            }
        });
    }

    private void initMedia(String path) {
        player = new MediaPlayer();
        try {
            player.setDataSource(path);
            holder = mSurface.getHolder();
            holder.addCallback(new MyCallBack());
            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    player.setLooping(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 给seekBar 设置一个最大值 ， 最大值是  MediaPlayer的 长度
        mSeekbar.setMax(player.getDuration());
        mSeekbar.setProgress(0);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                    player.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
                mHandler.postDelayed(run, 100);

            }
        });
    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (player != null)
                player.setDisplay(holder);//绑定
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    @Override
    protected void initView() {

    }

    private void calcVideoWidthHeight(String path) {
        final MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        mediaMetadata.setDataSource(mContext, Uri.parse(path));
        mVideoRotation = mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
        mVideoWidth = Integer.parseInt(mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        mVideoHeight = Integer.parseInt(mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        mVideoDuration = Integer.parseInt(mediaMetadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }

    Handler mHandler = new Handler();
    Runnable run = new Runnable() {
        @Override
        public void run() {
            //player.getCurrentPosition()获取音乐的当前进度
            if (player != null) {
                mSeekbar.setProgress(player.getCurrentPosition());
            }
        }
    };

    @OnClick({R.id.title, R.id.bt_submit, R.id.surface})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title:
                break;
            case R.id.bt_submit:

                break;
            case R.id.surface:
                // 开始播放
//                if (player != null && !player.isPlaying())
//                    player.start();
//                if (player != null && player.isPlaying())
//                    player.pause();
                break;
        }
    }
    private void storeNoMark(int x,int y,int width,int height) {
        showLoadingDialog();
        File file = new File(StaticFinalValues.VIDEOTEMP);
        if (!file.exists()) {
            file.mkdirs();
        }
        final String pathNew2 = StaticFinalValues.VIDEOTEMP + System.currentTimeMillis() + ".mp4";
        FFmpegUtils.cleanWaterMark(mMInputVideoPath, x, y, width, height, pathNew2, new OnEditorListener() {
            @Override
            public void onSuccess() {
                Log.e("cleanWaterMark===", "onSuccess");
                mHandlerFinfish.sendEmptyMessage(1);
            }

            @Override
            public void onFailure() {
                Log.e("cleanWaterMark===", "onFailure");
                mHandlerFinfish.sendEmptyMessage(2);
            }

            @Override
            public void onProgress(float progress) {

            }
        });
    }
    private Handler mHandlerFinfish = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissLoadingDialog();
            if (msg.what == 1) {
                Dialog dialog=mGetDialog.getTipDialog(activity, getResources().getString(R.string.success), new GetDialog.OnDialogClick() {
                    @Override
                    public void onClick() {
                        finish();
                    }
                });
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                FileUtils.refreshLocalMP4(activity,StaticFinalValues.VIDEOTEMP);
            } else if (0==msg.what){
                Toast.makeText(activity, "保存失败，请重试", Toast.LENGTH_LONG);
            }else if(2==msg.what){
                mGetDialog.getTipDialog(activity, getResources().getString(R.string.no_mark_error), new GetDialog.OnDialogClick() {
                    @Override
                    public void onClick() {

                    }
                }).show();
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            ArrayList<VideoFile> list = data.getParcelableArrayListExtra(StaticFinalValues.RESULT_PICK_VIDEO);
            if (list != null && list.size() > 0){
                mMInputVideoPath = list.get(0).getPath();
                initMedia(mMInputVideoPath);
                calcVideoWidthHeight(mMInputVideoPath);
            }else {
                finish();
            }

        }
    }
}