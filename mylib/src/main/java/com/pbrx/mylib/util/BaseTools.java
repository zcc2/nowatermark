package com.pbrx.mylib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.DisplayMetrics;

public class BaseTools {
	
	/** ��ȡ��Ļ�Ŀ�� */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}


	/***
	 * 去设置界面
	 */
	public static void goToSetting(Context context){
		//go to setting view
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", context.getPackageName(), null);
		intent.setData(uri);
		context.startActivity(intent);
	}


	/**
	 * get App versionCode
	 * @param context
	 * @return
	 */
	public static String getVersionCode(Context context){
		PackageManager packageManager=context.getPackageManager();
		PackageInfo packageInfo;
		String versionCode="";
		try {
			packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
			versionCode=packageInfo.versionCode+"";
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * get App versionName
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context){
		PackageManager packageManager=context.getPackageManager();
		PackageInfo packageInfo;
		String versionName="";
		try {
			packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
			versionName=packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
}
