package com.withball.baselibrary.enums;

/**
 * 类名：UFColumnType
 * 描述：数据类型
 * 包名： com.withball.jfmlibrary.enums
 * 项目名：Jfm_android
 * Created by qinzongke on 8/29/16.
 */
public enum  UFColumnType {

    INTEGER(1),//Integer

    STRING(2),//String

    LONG(3),//Long

    FLOAT(4),//Float

    DOUBLE(5);//Double



    private UFColumnType(int value){
    }

    public static UFColumnType convertToEnums(String type){
        UFColumnType t = null;
        switch (type){
            case "Integer":{
                t = UFColumnType.INTEGER;
            }
            break;
            case "String":{
                t = UFColumnType.STRING;
            }
            break;
            case "Long":{
                t = UFColumnType.LONG;
            }
            break;
            case "Float":{
                t = UFColumnType.FLOAT;
            }
            break;
            case "Double":{
                t = UFColumnType.DOUBLE;
            }
            break;
        }
        return t;
    }

    public static String getColumnType(UFColumnType type){
        String t = null;
        switch (type){

            case INTEGER:
                t = "Integer ";
                break;
            case STRING:
                t = "String ";
                break;
            case LONG:
                t = "Long ";
                break;
            case FLOAT:
                t = "Float ";
                break;
            case DOUBLE:
                t = "Double ";
                break;
        }
        return t;
    }

    public static String getColumnDBType(UFColumnType type){
        String t = null;
        switch (type){

            case INTEGER:
                t = "INTEGER";
                break;
            case STRING:
                t = "VARCHAR";
                break;
            case LONG:
                t = "LONG";
                break;
            case FLOAT:
                t = "FLOAT";
                break;
            case DOUBLE:
                t = "DOUBLE";
                break;
        }
        return t;
    }
}
