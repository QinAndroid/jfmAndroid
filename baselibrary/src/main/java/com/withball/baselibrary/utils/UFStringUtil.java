package com.withball.baselibrary.utils;

import com.withball.baselibrary.beans.UFClass;

/**
 * 类名：UFStringUtil
 * 描述：
 * 包名： com.withball.jfmlibrary.utils
 * 项目名：Jfm_android
 * Created by qinzongke on 8/30/16.
 */
public class UFStringUtil {

    /**
     * 首字母转大写
     * @param str
     * @return
     */
    public static String firstLetterToUp(String str){
        String newString = str.substring(0, 1).toUpperCase() + str.substring(1);
        return newString;
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String firstLetterToLow(String str){
        String newString = str.substring(0, 1).toLowerCase() + str.substring(1);
        return newString;
    }

    public static String getTableName(UFClass clazz){
        String name = clazz.getClassName().toLowerCase()+"_"+clazz.getVersion();
        return name;
    }

    public static String getModelName(UFClass clazz){
        String name = firstLetterToUp(clazz.getClassName());
        return name;
    }
}
