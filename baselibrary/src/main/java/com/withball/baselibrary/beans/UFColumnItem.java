package com.withball.baselibrary.beans;


import com.withball.baselibrary.enums.UFColumnDefaultValue;
import com.withball.baselibrary.enums.UFColumnType;

/**
 * 类名：UFColumnItem
 * 描述：
 * 包名： com.withball.jfmlibrary.bean
 * 项目名：Jfm_android
 * Created by qinzongke on 8/29/16.
 */
public class UFColumnItem {

    private UFColumnItem(){

    }

    public UFColumnItem(String columnName, UFColumnType columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public UFColumnItem(String columnName, UFColumnType columnType, UFColumnDefaultValue defaultVaule) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.defaultVaule = defaultVaule;
    }

    private String columnName;

    private UFColumnType columnType;

    private UFColumnDefaultValue defaultVaule;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public UFColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(UFColumnType columnType) {
        this.columnType = columnType;
    }

    public UFColumnDefaultValue getDefaultVaule() {
        return defaultVaule;
    }

    public void setDefaultVaule(UFColumnDefaultValue defaultVaule) {
        this.defaultVaule = defaultVaule;
    }
}
