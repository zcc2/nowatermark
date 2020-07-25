package com.zcc.nowatermark.pickvideo;

import android.support.v4.app.FragmentActivity;

import com.zcc.nowatermark.pickvideo.beans.AudioFile;
import com.zcc.nowatermark.pickvideo.beans.ImageFile;
import com.zcc.nowatermark.pickvideo.beans.NormalFile;
import com.zcc.nowatermark.pickvideo.beans.VideoFile;
import com.zcc.nowatermark.pickvideo.callback.FileLoaderCallbacks;
import com.zcc.nowatermark.pickvideo.callback.FilterResultCallback;

import static com.zcc.nowatermark.pickvideo.callback.FileLoaderCallbacks.TYPE_AUDIO;
import static com.zcc.nowatermark.pickvideo.callback.FileLoaderCallbacks.TYPE_FILE;
import static com.zcc.nowatermark.pickvideo.callback.FileLoaderCallbacks.TYPE_IMAGE;
import static com.zcc.nowatermark.pickvideo.callback.FileLoaderCallbacks.TYPE_VIDEO;


public class FileFilter {
    public static void getImages(FragmentActivity activity, FilterResultCallback<ImageFile> callback){
        activity.getSupportLoaderManager().initLoader(0, null,
                new FileLoaderCallbacks(activity, callback, TYPE_IMAGE));
    }

    public static void getVideos(FragmentActivity activity, FilterResultCallback<VideoFile> callback){
        activity.getSupportLoaderManager().initLoader(1, null,
                new FileLoaderCallbacks(activity, callback, TYPE_VIDEO));
    }

    public static void getAudios(FragmentActivity activity, FilterResultCallback<AudioFile> callback){
        activity.getSupportLoaderManager().initLoader(2, null,
                new FileLoaderCallbacks(activity, callback, TYPE_AUDIO));
    }

    public static void getFiles(FragmentActivity activity,
                                FilterResultCallback<NormalFile> callback, String[] suffix){
        activity.getSupportLoaderManager().initLoader(3, null,
                new FileLoaderCallbacks(activity, callback, TYPE_FILE, suffix));
    }
}