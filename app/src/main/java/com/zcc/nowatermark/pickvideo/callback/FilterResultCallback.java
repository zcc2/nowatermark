package com.zcc.nowatermark.pickvideo.callback;

import com.zcc.nowatermark.pickvideo.beans.BaseFile;
import com.zcc.nowatermark.pickvideo.beans.Directory;

import java.util.List;

/**
 * Created by Vincent Woo
 * Date: 2016/10/11
 * Time: 11:39
 */

public interface FilterResultCallback<T extends BaseFile> {
    void onResult(List<Directory<T>> directories);
}
