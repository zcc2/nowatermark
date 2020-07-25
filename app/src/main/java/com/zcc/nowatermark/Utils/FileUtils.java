package com.zcc.nowatermark.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 作者： zcc on 2020/7/25 14:24
 * 邮箱：m15632271759_1@163.com
 */

public class FileUtils {
    public static void refreshLocalMP4(Context context, String path){
        File imgFile=new File(path);
        if (imgFile.exists()) {
            Uri uri = Uri.fromFile(imgFile);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
    }
}
