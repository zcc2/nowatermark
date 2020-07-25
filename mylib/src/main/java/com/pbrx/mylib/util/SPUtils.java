package com.pbrx.mylib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Iverson on 2016/12/23 下午5:57
 * 此类用于：保存配置内容
 */

public class SPUtils {

    private static SharedPreferences mSp;
    private UserInfoBean userInfoBean;

    /**
     * 在Application中初始化
     *
     * @param context
     * @param spName
     */
    public static void init(Context context, String spName) {
        mSp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }
    private static SPUtils sSharedInfo;

    /**
     * 单例
     */
    public static SPUtils getInstance() {
        if (sSharedInfo == null) {
            sSharedInfo = new SPUtils();
        }
        return sSharedInfo;
    }
    /**
     * 保存
     *
     * @param key
     * @param value
     */
    public static void save(String key, Object value) {
        SharedPreferences.Editor editor = mSp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }
    public UserInfoBean getUserInfoBean() {
        if (userInfoBean == null)
            load();
        return userInfoBean;
    }
    /***
     * 获取本地RUserInfoBean
     */
    public void load() {
        final UserInfoBean userInfoBean = get(UserInfoBean.class, null);
        if (userInfoBean != null) {
            setUserInfoBean(userInfoBean);
        }

    }


    public void setUserInfoBean(UserInfoBean userInfoBean) {

        this.userInfoBean = userInfoBean;

        // save to local
        if (userInfoBean != null) {
            put(userInfoBean);
        } else {
            remove(UserInfoBean.class);
        }
    }
    public void remove(final Class<?> clazz) {
        final String innerKey = getKey(clazz);
        if (innerKey != null) {
            basicPutObject(innerKey, null);
        }
    }

    /**
     * 取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key, Object defaultValue) {
        if (defaultValue instanceof String) {
            return mSp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return mSp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return mSp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return mSp.getLong(key, (Long) defaultValue);
        } else {
            return null;
        }
    }

    /**
     * 删除
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.remove(key);

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清空
     */
    public static void clear() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询key是否存在
     *
     * @param key
     * @return
     */
    public static boolean contain(String key) {
        return mSp.contains(key);
    }

    /**
     * 自定义提交方法
     */
    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
            }
            editor.commit();
        }
    }
    /***
     * 对外的get, put 接口 UserRelated 表示数据和当前的用户关联
     */
    public <T> T get(final Class<T> clazz, final T tDefault) {
        final String innerKey = getKey(clazz);
        if (innerKey != null) {
            final T ret = basicGetObject(innerKey, clazz);
            if (ret != null) {
                return ret;
            }
        }
        return tDefault;
    }
    /***
     * basic IO2 (Object): get, put
     */
    private synchronized <T> T basicGetObject(final String key, Class<T> clazz) {
        final String value = basicGetString(key);
        return string2obj(value, clazz);
    }
    /***
     * basic IO1 (String): get, put
     */
    private synchronized String basicGetString(final String key) {
        return mSp.getString(key, null);
    }
    private <T> T string2obj(final String string, final Class<T> clazz) {
        try {
            return new Gson().fromJson(string, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void put(final Object obj) {
        if (obj != null) {
            final String innerKey = getKey(obj.getClass());
            if (innerKey != null) {
                basicPutObject(innerKey, obj);
            }
        }
    }
    private synchronized void basicPutObject(final String key, final Object obj) {
        final String value = obj2string(obj);
        basicPutString(key, value);
    }
    /***
     * Object 到 String 的序列和反序列化
     */
    private String obj2string(final Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private synchronized void basicPutString(final String key, final String value) {
        final SharedPreferences.Editor edit = mSp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    /**
     * 类对应的key
     */
    private String getKey(final Class<?> clazz) {
        if (clazz != null) {
            return clazz.getSimpleName();
        }
        return null;
    }
    /**
     * 用于保存集合
     *
     * @param map map数据
     * @return 保存结果
     */
    public static void saveMap(String tag,Map<String, String> map) {
        if (map != null) {
            JSONStringer jsonStringer = new JSONStringer();
            try {
                jsonStringer.array();
                for (String string : map.keySet()) {
                    jsonStringer.object();
                    jsonStringer.key("year");
                    jsonStringer.value(string);
                    jsonStringer.key("month");
                    jsonStringer.value(map.get(string));
                    jsonStringer.endObject();
                }
                jsonStringer.endArray();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSp.edit().putString(tag, jsonStringer.toString()).apply();
        }
    }
    /**
     * 用于取出集合
     *
     * @return HashMap
     */
    public static Map<String,String> getLocalMap(String tag) {
        Map<String, String> examMap = new HashMap<>();
        String map = mSp.getString(tag, "");
        if (map.length() > 0) {
            JSONTokener jsonTokener = new JSONTokener(map);
            try {
                JSONArray jsonArray = (JSONArray) jsonTokener.nextValue();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    examMap.put(jsonObject.getString("year"), jsonObject.getString("month"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return examMap;
    }

}
