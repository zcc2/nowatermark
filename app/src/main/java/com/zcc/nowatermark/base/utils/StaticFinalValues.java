package com.zcc.nowatermark.base.utils;

import android.os.Environment;

/**
 * description:
 * Created by aserbao on 2018/5/15.
 */


public class StaticFinalValues {
    //int final
    public static final int RECORD_MIN_TIME = 5 * 1000;
    //=======================handler
    public static final int EMPTY = 0;
    public static final int DELAY_DETAL = 1;
    public static final int MY_TOPIC_ADAPTER = 9;
    public static final int CHANGE_IMAGE = 10;
    public static final int OVER_CLICK = 11;//视频定时结束
    //================================================path
    public static final String SAVETOPHOTOPATH = "/storage/emulated/0/DCIM/Camera/";//保存至本地相册路径
    public static final String ISSAVEVIDEOTEMPEXIST = "/storage/emulated/0/anowatermark/";
    public static final String VIDEOTEMP = "/storage/emulated/0/anowatermark/videotemp/";
    public static final String MP3TEMP = "/storage/emulated/0/anowatermark/mp3temp/";
    public static final String STORAGE_TEMP_VIDEO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/123.mp4";
    public static final String STORAGE_TEMP_VIDEO_PATH1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1233.mp4";

    //======================string
    public static final String MAX_NUMBER = "MaxNumber";
    public static final String RESULT_PICK_VIDEO = "ResultPickVideo";
    public static final String RESULT_PICK_AUDIO = "ResultPickAudio";
    public static final String VIDEOFILEPATH = "VideoFilePath";
    public static final String MISNOTCOMELOCAL = "mIsNotComeLocal";//0表示本地视频，1表示非本地视频
    public static final String BUNDLE = "bundle";
    public static final String CUT_TIME = "cut_time";
    public static final String MAIN_TYPE = "main_type";//主页功能

    private static final String VIDEO_PATH = "video_path";

    //-------------type---------------
    public static final int VIEW_HOLDER_HEAD = 99;
    public static final int VIEW_HOLDER_TEXT = 100;
    public static final int VIEW_HOLDER_IMAGE_100H = 101;
    public static final int VIEW_HOLDER_CIRCLE_IMAGE_ITEM = 1001;
    public static final int VIEW_FULL_IMAGE_ITEM = 1002;
    public static final int VIEW_HOLDER_CLASS = 102;
    public static final int VIEW_BLEND_MODE = 103;

    //=======================requestCode and resultCode
    public static final int COMR_FROM_SEL_COVER_TIME_ACTIVITY = 1;
    public static final int COMR_FROM_VIDEO_EDIT_TIME_ACTIVITY = 2;
    //======================int
    public static final int REQUEST_CODE_PICK_VIDEO = 0x200;
    public static final int REQUEST_CODE_TAKE_VIDEO = 0x201;
    public static float VIDEO_WIDTH_HEIGHT = 0.85f;
    public static int REQUEST_CODE_CUT=111;
    public static int REQUEST_CODE_COMPOSE=222;
    public static int REQUEST_CODE_ADD_WATERMARK=333;
    public static int REQUEST_CODE_CHANGE_COVER=444;
    public static int REQUEST_CODE_CHANGE_BM=555;
    public static int REQUEST_CODE_REMOVE_WATERMARK=666;
    public static int REQUEST_CODE_GET_AUDIO=777;
}
