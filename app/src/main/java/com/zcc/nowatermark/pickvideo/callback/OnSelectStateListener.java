package com.zcc.nowatermark.pickvideo.callback;

public interface OnSelectStateListener<T> {
        void OnSelectStateChanged(boolean state, T file);
    }