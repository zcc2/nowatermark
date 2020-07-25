package com.pbrx.mylib.base;

import android.app.Activity;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.pbrx.mylib.util.LogUtil;
import com.pbrx.mylib.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iverson on 2017/10/9 下午4:20
 * 此类用于：分类加载的application
 */

public class LibApplication extends MultiDexApplication {

    public static List<Activity> activities = new ArrayList<>();
    private static LibApplication instance;
    private void LibApplication(){}
    public static LibApplication getInstance() {
        if(instance == null){
            synchronized (LibApplication.class){
                if (instance == null) {
                    instance = new LibApplication();
                }
            }
        }
        return  instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        //初始化Bugly
        SPUtils.init(getApplicationContext(),"wiring");
    }


    public void addActivity(Activity activity) {
        if(activity == null) return;
        activities.add(activity);
        LogUtil.e("activityName: ","activityName add:"+activity.toString());
    }

    public void deleActvitity(Activity activity){
        if(activity == null) return;
        activities.remove(activity);
        LogUtil.e("activityName: ","activityName deleActvitity: "+activity.toString());
    }

    public void removeAllActivity(){
        if(null != activities){
            for(int i = 0 ; i< activities.size() ; i++){
                if(null != activities.get(i)){
                    activities.get(i).finish();
                }
            }

        }
    }


}
