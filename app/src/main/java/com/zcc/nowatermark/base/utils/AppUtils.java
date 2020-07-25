package com.zcc.nowatermark.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;

import com.pbrx.mylib.util.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by xyb on 2017/7/10.
 */

public class AppUtils {
    /**
     * 正则表达式：验证手机号
     */
//    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_MOBILE = "^(1[3-9])\\d{9}$";
    public static final String REGEX_IDCARD = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * 验证手机号格式是否正确
     *
     * @param phone 手机号
     * @return
     */
    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        if (phone.matches(REGEX_MOBILE)) {
            return true;
        }
        return false;
    }

    /**
     * 验证身份证号格式是否正确
     *
     * @param idcard 身份证号
     * @return
     */
    public static boolean isIDCard(String idcard) {
        if (TextUtils.isEmpty(idcard)) {
            return false;
        }
        if (idcard.matches(REGEX_IDCARD)) {
            return true;
        }
        return false;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }


    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }




    public static String hidePhoneNum(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }





    // 科学计数法转换成数字(保留2位)
    public static String scienceTurnNum(Double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    // 科学计数法转换成数字(保留0位)
    public static String scienceTurnNumZero(Double value) {
        DecimalFormat df = new DecimalFormat("0");
        return df.format(value);
    }

    // 判断字符串是否为空
    public static boolean checkStringNoNull(String paramString) {
        return null != paramString && paramString.length() > 0;
    }

    // 判断字符串组是否为空
    public static boolean checkStrsNoNull(String... strings) {
        boolean flag = true;
        for (String paramString : strings) {
            if (paramString == null || paramString.length() == 0) {
                flag = false;
            }
        }
        return flag;
    }

    // 判断字符串组是否纯数字
    public static boolean checkStrsIsDigit(String str) {
        String reg = "^\\d+$";
        return str.matches(reg);
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isWeiboInstalled(@NonNull Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getTimeHour(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    public static String getTimeSecond(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    public static String getTimeMonth(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * @param lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     * @Description: long类型转换成日期
     */
    public static String longToDate(long lo) {
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }

    public static String longToDate2(long lo) {
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sd.format(date);
    }

    public static String strmmTodd(String str) {
        String strRe = "";
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            strRe = sd.format(sd.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strRe;
    }

    public static String getTimeMM(String str) {
        String strRe = "";
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            strRe = sd1.format(sd.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strRe;
    }

    public static List<String> getMonthBetween(String minDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(longToDate(System.currentTimeMillis())));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 功能描述：判断当前设备是否为模拟器
     * 参数：
     */
    public static boolean isEmulator() {
        if (getBaseband_Ver().equalsIgnoreCase("no message") || getBaseband_Ver().equalsIgnoreCase("no  message")) {
            return true;
        }
        return false;
    }

    /**
     * BASEBAND-VER
     * 基带版本
     * return String
     */

    public static String getBaseband_Ver() {
        String Version = "";
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
// System.out.println(">>>>>>><<<<<<<" +(String)result);
            Version = (String) result;
        } catch (Exception e) {
        }
        return Version;
    }

    public static boolean compareTime(String time1, String time2) throws ParseException {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        Date a = sdf.parse(time1);
        Date b = sdf.parse(time2);
        //将Date转换成毫秒
        if (a.getTime() - b.getTime() < 0)
            return true;
        else
            return false;
    }

    public static Long getLongTime(String time1) throws ParseException {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //将字符串形式的时间转化为Date类型的时间
        Date a = sdf.parse(time1);
        return a.getTime();
    }

    public static String idCardReturnYear(String idcard) {
        //subSequence按指定位置截取18位
        if (idcard.length() != 18) {
            return "";
        }
        String year = (String) idcard.subSequence(6, 10);
        String month = (String) idcard.subSequence(10, 12);
        String day = (String) idcard.subSequence(12, 14);
        return year + "-" + month + "-" + day;
    }

    public static String cardYear(String idcard) {
        //subSequence按指定位置截取18位
        String year = (String) idcard.subSequence(0, 4);
        String month = (String) idcard.subSequence(4, 6);
        String day = (String) idcard.subSequence(6, 8);
        return year + "-" + month + "-" + day;
    }

    public static String getGender(String gender) {

        return "M".equals(gender) ? "男" : "女";
    }
    public static String getSex(String sex) {

        return "男".equals(sex) ? "M" : "F";
    }

    public static boolean isBelong(String begin, String end) {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(begin);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean flag = belongCalendar(now, beginTime, endTime);
        return flag;
    }


    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static final String SEPARATOR_CHAR = ".";

    // 按钮点击频次控制800毫秒内点击无效
    public static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) { // 800毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String toKm(double dou) {
        return dou > 1000 ? scienceTurnNum(dou / 1000) + "千" : scienceTurnNum(dou);
    }

    public static String getPayType(String dou) {
        String str = "";
        if ("cash".equals(dou)) {
            str = "现金";
        } else if ("thirdPay".equals(dou)) {
            str = "第三方";
        } else if ("credit".equals(dou)) {
            str = "刷卡";
        }
        return str;
    }

    public static boolean checkOkPwd(String mobile) {
        String regex = "^[A-Za-z0-9]{6,10}$";
        return Pattern.matches(regex, mobile);
    }
    public static boolean checkVideoUrl(String url) {
        String reg = "(mp4|flv|avi|rm|rmvb|wmv)";
        Pattern p = Pattern.compile(reg);
        return p.matcher(url).find();
    }
    public static String FileNameZz(String url) {
        String reg = "[^/]+(?!.*/)";
        Pattern p = Pattern.compile(reg);
        return p.matcher(url).group();
    }
    public static String fileName(String url) {
       String[] strs=url.split("/");
       String name="";
       if(strs!=null&&strs.length>0){
           name=strs[strs.length-1];
       }
        return name;
    }


    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 判断程序是否在后台运行
     */
    public static boolean isRunBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // 表明程序在后台运行
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static String phone4Hide(String phone) {
        if(TextUtils.isEmpty(phone)){
            return phone;
        }
        if (phone.length() == 11) {
            phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phone;
    }

    public static void setTvStar(TextView... tvStar) {
        String strStr = "<font color='red'>*</font>";
        for (TextView tv : tvStar) {
            tv.setText(Html.fromHtml(tv.getText().toString() + strStr));
        }
    }

    public static String setNullEmpty(String str) {
        String strStr = "";
        if (checkStrsNoNull(str)) {
            strStr = str;
        }
        return strStr;
    }

    public static void setStrSpan(TextView tv, String str, int begin, int end, int color) {
        SpannableString spStr = new SpannableString(str);
        spStr.setSpan(new ForegroundColorSpan(color),
                begin, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spStr);
    }

    public static boolean isEmulator(Context mContext) {
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
// 是否可以处理跳转到拨号的 Intent
        boolean canResolveIntent = intent.resolveActivity(mContext.getPackageManager()) != null;
        LogUtil.e("canResolveIntent==",canResolveIntent+"");
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.SERIAL.equalsIgnoreCase("unknown")
                || Build.SERIAL.equalsIgnoreCase("android")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName().toLowerCase().equals("android")
                || !canResolveIntent;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     *
     * @return true 为模拟器
     */
    public static Boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (null == sensor8) {
            LogUtil.e("canResolveIntentsensor8==",true+"");
            return true;
        } else {
            LogUtil.e("canResolveIntentsensor8==",false+"");
            return false;
        }
    }

    // 时间戳转换
    public static String timestampTotime(long timestamp, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(timestamp);
    }

    //将字符串等分
    public static String[] splitStr(String str, int splitLen) {
        int count = str.length() / splitLen + (str.length() % splitLen == 0 ? 0 : 1);
        String[] strs = new String[count];
        for (int i = 0; i < count; i++) {
            if (str.length() <= splitLen) {
                strs[i] = str;
            } else {
                strs[i] = str.substring(0, splitLen);
                str = str.substring(splitLen);
            }
        }
        return strs;
    }

    //按字符数等分
    public static List<String> splitBySize(String value, int length) {
        char[] cs = value.toCharArray();
        StringBuilder result = new StringBuilder();
        List<String> resultList = new ArrayList<String>();
        int index = 0;
        for (char c : cs) {
            index += String.valueOf(c).getBytes().length;
            if (index > length) {
                resultList.add(result.toString());
                result.delete(0, index - 1);
                index = 0;
            } else {
                result.append(c);
            }
        }
        return resultList;
    }


    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();

            try {
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }

                result = true;
                if (Build.VERSION.SDK_INT >= 23) {
                    if (dark) {
                        window.getDecorView().setSystemUiVisibility(9216);
                    } else {
                        window.getDecorView().setSystemUiVisibility(0);
                    }
                }
            } catch (Exception var8) {
                ;
            }
        }

        return result;
    }
    public static String getSBK(String str1, String str2) {
       StringBuilder sb=new StringBuilder(str1);
       sb.append("(").append(str2).append(")");
        return sb.toString();
    }

}
