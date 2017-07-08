package com.withball.jfmlibrary.dbmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.withball.baselibrary.ModelUtil;
import com.withball.baselibrary.annotations.UFTableSql;
import com.withball.baselibrary.beans.UFClass;
import com.withball.baselibrary.utils.UFStringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


/**
 * 类名：UFDBHelper
 * 描述：
 * 包名： com.withball.jfmlibrary.dbmanager
 * 项目名：Jfm_android
 * Created by qinzongke on 9/2/16.
 */
public class UFDBHelper extends SQLiteOpenHelper {

    private static UFDBHelper dbHelper;

    private static SQLiteDatabase db;

    private static String packageName;

    private static final int VERSION = 1;

    private static  boolean isShowLog = true;

    public static void setIsShowLog(boolean show){
        isShowLog = show;
    }

    public static UFDBHelper getInstance(Context context, String name) {
        packageName = context.getPackageName()+".model";
        if (dbHelper == null) {
            dbHelper = new UFDBHelper(context, name, VERSION);
            dbHelper.createTableVersion();
        }
        dbHelper.createTables();
        return dbHelper;
    }


    public static UFDBHelper getInstance() {
        if (dbHelper == null) {
            throw new IllegalArgumentException("please init first");
        }
        return dbHelper;
    }

    private void createTableVersion() {
        String sql = "create table if not exists table_version(name varchar primary key,originversion integer,currentversion integer)";
        getDB().execSQL(sql);
    }


    private void createTables() {
        List<UFClass> classes = ModelUtil.getGeneratorClasses();
        for (UFClass ufClass : classes) {
            String tableName = UFStringUtil.getTableName(ufClass);
            if (!isTableExists(getDB(), tableName)) {
                createTable(ufClass);
            }
        }
    }


    private void tableVersion(UFClass clazz) {
        String baseName = UFStringUtil.firstLetterToLow(clazz.getClassName());
        String selectSql = "select * from table_version where name = '" + baseName + "'";
        Cursor cursor = getDB().rawQuery(selectSql, null);
        int originversion = 0;
        int currentversion = 0;
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex("originversion");
            originversion = cursor.getInt(index);
            index = cursor.getColumnIndex("currentversion");
            currentversion = cursor.getInt(index);
        }
        cursor.close();
        String sql = "";
        if (currentversion != 0) {
            sql = "update table_version set originversion = " + currentversion + ",currentversion=" + clazz.getVersion() + " where name='" + baseName + "'";
        } else {
            sql = "insert into table_version(name,originversion,currentversion)values('" + baseName + "',0," + clazz.getVersion() + ")";
        }
        if(!sql.equals("")){
            getDB().execSQL(sql);
        }
    }


    public void migrage() {
        List<UFClass> classes = ModelUtil.getGeneratorClasses();
        for (UFClass ufClass : classes) {
            String tableName = UFStringUtil.getTableName(ufClass);
            String baseName = ufClass.getClassName();
            int currentversion = ufClass.getVersion();
            int originversion = getOriginVersin(baseName, currentversion);
            if (originversion == 0 || currentversion == originversion) {
                continue;
            }
            String originName = baseName + "_" + originversion;
            List<String> originColumns = getTableColumns(originName);
            List<String> currentColumns = getTableColumns(tableName);
            if (originColumns == null || currentColumns == null || originColumns.size() == 0 || currentColumns.size() == 0) {
                continue;
            }
            String sql = createMigrageSql(originName, tableName, originColumns, currentColumns);
            getDB().execSQL(sql);
            //数据迁移成功后  将tableversion的originversion值改为currentversion
            String updateSql = "update table_version set originversion="+currentversion+" where name='"+baseName+"'";
            getDB().execSQL(updateSql);
            String test = "select * from table_version where name='"+baseName+"'";
            Cursor cursor = getDB().rawQuery(test,null);
            while (cursor.moveToNext()){
                int index = cursor.getColumnIndex("originversion");
                int origin = cursor.getInt(index);
                index = cursor.getColumnIndex("currentversion");
                int current = cursor.getInt(index);
                Log.e("origin",origin+"");
                Log.e("current",current+"");
            }
        }
    }

    private String createMigrageSql(String originName, String currentName, List<String> originColumns, List<String> currentColumns) {
        StringBuffer insertColumns = new StringBuffer();
        for (String str : currentColumns) {
            insertColumns.append(str).append(",");
        }
        insertColumns = insertColumns.delete(insertColumns.length() - 1, insertColumns.length());
        StringBuffer insertSql = new StringBuffer();
        insertSql.append("insert into ").append(currentName).append("(").append(insertColumns).append(") select ");
        StringBuffer selectColumn = new StringBuffer();
        for (String str : currentColumns) {
            if (originColumns.contains(str)) {
                selectColumn.append(str);
            } else {
                selectColumn.append("\"\"");
            }
            selectColumn.append(",");
        }
        selectColumn = selectColumn.delete(selectColumn.length() - 1, selectColumn.length());
        insertSql.append(selectColumn).append(" from ").append(originName);
        Log.e("insertSql", insertSql.toString());
        return insertSql.toString();
    }

    /**
     * 获取需要迁移的数据表版本号
     *
     * @param baseName
     * @param currentVersion
     * @return
     */
    private int getOriginVersin(String baseName, int currentVersion) {
        String selectSql = "select * from table_version where name = '" + baseName + "' and originversion !=0 and currentversion ==" + currentVersion;
        int originversion = 0;
        Cursor cursor = getDB().rawQuery(selectSql, null);
        if (cursor.moveToNext()) {
            int index = cursor.getColumnIndex("originversion");
            originversion = cursor.getInt(index);
        }
        cursor.close();
        return originversion;
    }

    /**
     * 获取表列
     *
     * @param tableName
     * @return
     */
    private List<String> getTableColumns(String tableName) {
        String selectSql = "select * from " + tableName + " where 1=1 ";
        Cursor cursor = getDB().rawQuery(selectSql, null);
        List<String> columns = Arrays.asList(cursor.getColumnNames());
        cursor.close();
        return columns;
    }

    private void createTable(UFClass ufClazz) {
        try {
            String modelName = UFStringUtil.getModelName(ufClazz);
            Class clazz = Class.forName(packageName + "." + modelName);
            String sql = ((UFTableSql) clazz.getAnnotation(UFTableSql.class)).value();
            Log.e("createTable", sql);
            if (sql == null || sql.equals("")) {
                throw new IllegalArgumentException("create table sql not found");
            }
            getDB().execSQL(sql);
            //添加／更新 tableVersion
            tableVersion(ufClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public SQLiteDatabase getDB() {
        if (dbHelper == null) {
            throw new IllegalArgumentException("please initDB first");
        }
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }


    private UFDBHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void execuSql(String sql,List<Object> args){
        if(isShowLog){
            Log.e(sql,args.toString());
        }
        getDB().execSQL(sql,args.toArray());
    }

    public Cursor query(String sql,List<String> args){
        if(isShowLog){
            Log.e(sql,args.toString());
        }
        Cursor cursor = getDB().rawQuery(sql,args.toArray(new String[args.size()]));
        return cursor;
    }


    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        String sql = "select count(*) from sqlite_master where type ='table' and name ='" + tableName + "';";
        Cursor cursor = db.rawQuery(sql, null);
        boolean flag = false;
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                flag = true;
            }
        }
        return flag;
    }
}
