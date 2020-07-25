package com.pbrx.mylib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Iverson on 2017/10/13 下午5:55
 * 此类用于：关于数字的工具类
 */

public class NumberUtil {

    /**
     * 判断是否为电话号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
