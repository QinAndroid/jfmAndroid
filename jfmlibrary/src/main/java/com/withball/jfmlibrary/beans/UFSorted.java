package com.withball.jfmlibrary.beans;

import com.withball.jfmlibrary.constants.UFDBConstants;
import com.withball.jfmlibrary.enums.UFSortedType;

/**
 * 类名：UFSorted
 * 描述：
 * 包名： com.withball.jfmlibrary.beans
 * 项目名：Jfm_android
 * Created by qinzongke on 9/1/16.
 */
public class UFSorted {


    private UFSorted() {}

    public static String DESC(String column) {
        return (column + UFDBConstants.ORDER_DESC);
    }

    public static String ASC(String column) {
        return (column + UFDBConstants.ORDER_ASC);
    }
}
