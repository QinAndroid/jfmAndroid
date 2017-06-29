package com.withball.baselibrary.beans;


import com.withball.baselibrary.enums.UFColumnType;
import com.withball.baselibrary.enums.UFPrimaryKeyType;
import com.withball.baselibrary.utils.UFStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：UFClass
 * 描述：
 * 包名： com.withball.jfmlibrary.bean
 * 项目名：Jfm_android
 * Created by qinzongke on 8/30/16.
 */
public class UFClass {

    private String className;

    private UFColumnItem primaryKey = null;

    private UFPrimaryKeyType primaryKeyType = null;

    private int version = 0;


    private boolean isHasPrimaryKey = false;

    private List<UFColumnItem> columns = new ArrayList<>();

    private UFClass() {
    }




    public UFClass(String className,int version) {
        checkVersion(version);
        this.className = className;
        this.version = version;
    }

    public UFClass(String className, UFColumnItem primaryKey,int version) {
        checkVersion(version);
        this.className = className;
        this.primaryKey = primaryKey;
        this.version = version;
    }

    private void checkVersion(int version){
        if(version <= 0){
            throw new IllegalArgumentException("class version is error");
        }
    }

    public String getClassName() {
        return className;
    }

    public UFColumnItem getPrimaryKey() {
        return primaryKey;
    }


    /**
     * 设置自增主键
     */
    public void setAutoIncrePrimaryKey() {
        primaryKey = new UFColumnItem("id", UFColumnType.INTEGER);
        this.primaryKeyType = UFPrimaryKeyType.AUTOINCREMENT;
        isHasPrimaryKey = true;
    }

    /**
     * 设置不自增主键
     * @param type
     */
    public void setUNAutoIncrePrimaryKey(UFColumnType type){
        primaryKey = new UFColumnItem("id",type);
        this.primaryKeyType = UFPrimaryKeyType.UNAUTOINCREMENT;
        isHasPrimaryKey = true;
    }

    public UFPrimaryKeyType getPrimaryKeyType() {
        return primaryKeyType;
    }

    public void addColumns(UFColumnItem item) {
        columns.add(item);
    }

    public List<UFColumnItem> getColumns() {
        return columns;
    }

    public boolean isHasPrimaryKey() {
        return isHasPrimaryKey;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
