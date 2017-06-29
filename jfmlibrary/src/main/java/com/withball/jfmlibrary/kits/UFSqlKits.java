package com.withball.jfmlibrary.kits;

import android.util.Log;

import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.constants.UFDBConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 类名：UFSqlKits
 * 描述：
 * 包名： com.withball.jfmlibrary.kits
 * 项目名：Jfm_android
 * Created by qinzongke on 9/5/16.
 */
public class UFSqlKits {

    private static final String SELECT = " SELECT ";

    private static final String ALL = "*";

    private static final String FROM = " FROM ";

    private static final String WHERE = " WHERE 1 = 1 ";

    private static final String ORDERBY = " ORDER BY ";

    private static final String BETWEEN = " BETWEEN ";

    private static final String AND = " AND ";

    private static final String LIKE = " LIKE ";

    private static final String LIMIT = " LIMIT 1 ";

    private static final String INSERT = " INSERT INTO ";

    private static final String VALUES = " VALUES ";

    private static final String UPDATE = " UPDATE ";

    private static final String SET = " SET ";

    private static final String EQ = "=";

    private static final String DELETE = " DELETE FROM ";

    private static Map<String, Object> getAttrs(UFModel model) {
        return model.dcExport();
    }

    private static String getTableName(Map<String, Object> attrs) {
        return String.valueOf(attrs.get(UFDBConstants.TABLENAME));
    }

    private static Map<String, String> getConnects(Map<String, Object> attrs) {
        return (Map) attrs.get(UFDBConstants.CONNECT);
    }

    private static Map<String, String> getOpts(Map<String, Object> attrs) {
        return (Map) attrs.get(UFDBConstants.CONDITION);
    }

    private static String[] getOutPutField(Map<String,Object> attrs){
        return (String[])attrs.get(UFDBConstants.OUTPUTFIELD);
    }


    public static String queryAll(UFModel model) {
        String sql = querySql(model).toString();
        return sql;
    }


    public static String queryFirst(UFModel model) {
        StringBuffer sql = querySql(model);
        sql.append(LIMIT);
        return sql.toString();

    }

    public static String save(UFModel model){
        Map<String,Object> attrs = getAttrs(model);
        String tableName = getTableName(attrs);
        if (attrs.containsKey(UFDBConstants.CONNECT)){
            attrs.remove(UFDBConstants.CONNECT);
        }
        if(attrs.containsKey(UFDBConstants.CONDITION)){
            attrs.remove(UFDBConstants.CONDITION);
        }
        attrs.remove(UFDBConstants.TABLENAME);
        Set<String> keySet = attrs.keySet();
        Collection<Object> valueSet = attrs.values();
        Iterator<Object> iterator = valueSet.iterator();
        StringBuffer values = new StringBuffer();
        while (iterator.hasNext()) {
            Object value = iterator.next();
            if (value instanceof String) {
                values.append("'").append(value).append("'");
            } else {
                values.append(value);
            }
            values.append(",");
        }
        String keys = keySet.toString().substring(1, keySet.toString().length() - 1);
        String value = values.substring(0, values.length() - 1);
        StringBuffer sql = new StringBuffer();
        sql.append(INSERT).append(tableName).append("(").append(keys).append(")");
        sql.append(VALUES).append("(").append(value).append(")");
        return sql.toString();
    }

    public static String update(UFModel model){
        StringBuffer sql = new StringBuffer();
        Map<String,Object> attrs = getAttrs(model);
        String tableName = getTableName(attrs);
        if (attrs.containsKey(UFDBConstants.CONNECT)){
            attrs.remove(UFDBConstants.CONNECT);
        }
        if(attrs.containsKey(UFDBConstants.CONDITION)){
            attrs.remove(UFDBConstants.CONDITION);
        }
        if(attrs.containsKey(UFDBConstants.TABLENAME)){
            attrs.remove(UFDBConstants.TABLENAME);
        }
        sql.append(UPDATE).append(tableName).append(SET);
        StringBuffer setValues = new StringBuffer();
        Set<String> keySet = attrs.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals(UFDBConstants.PRIMARYKEY)) {
                continue;
            }
            setValues.append(key).append(EQ);
            Object value = attrs.get(key);
            if (value instanceof String) {
                setValues.append("'").append(value).append("'");
            } else {
                setValues.append(value);
            }
            setValues.append(",");
        }
        if (setValues.length() <= 0) {
            throw new IllegalArgumentException("update Missing SetValues ");
        }
        sql.append(setValues.substring(0, setValues.length() - 1));
        sql.append(WHERE);
        if (attrs.containsKey(UFDBConstants.PRIMARYKEY)) {
            sql.append(AND).append(UFDBConstants.PRIMARYKEY).append(" ").append(EQ).append(" ").append(attrs.get(UFDBConstants.PRIMARYKEY));
        }
        return sql.toString();
    }

    public static String delete(UFModel model){
        StringBuffer sql = new StringBuffer();
        Map<String,Object> attrs = getAttrs(model);
        String tableName = getTableName(attrs);
        sql.append(DELETE).append(tableName).append(WHERE);
        sql.append(whereSql(model));
        return sql.toString();

    }


    private static StringBuffer querySql(UFModel model) {
        Map<String, Object> attrs = getAttrs(model);
        String[] outPutFields = getOutPutField(attrs);
        String tableName = getTableName(attrs);
        StringBuffer sql = new StringBuffer();


        sql.append(SELECT);
        if(outPutFields !=null && outPutFields.length != 0){
            StringBuffer fields = new StringBuffer();
            for(String field : outPutFields){
                if(field != null && !field.equals("")){
                    fields.append(field).append(",");
                }
            }
            if(fields.toString().endsWith(",")){
                fields = new StringBuffer(fields.subSequence(0,fields.length()-1));
            }
            if(fields.length() != 0){
                sql.append(fields);
            }else{
                sql.append(ALL);
            }
        }else{
            sql.append(ALL);
        }


        sql.append(FROM).append(tableName).append(WHERE);
        sql.append(whereSql(model));
        String[] orders = (String[])attrs.get(UFDBConstants.ORDERKEY);
        StringBuffer order = new StringBuffer();
        String suffix = ",";
        if(orders != null && orders.length != 0){
            for (String column : orders) {
                if(column == null || column.trim().equals("")){
                    continue;
                }
                order.append(column).append(suffix);
            }
            if(order.length()!=0 || order.toString().endsWith(suffix)){
                order = order.delete(order.length()-1,order.length());
                sql.append(ORDERBY).append(order);
            }
        }

        return sql;
    }

    private static String whereSql(UFModel model){
        StringBuffer sql = new StringBuffer();
        Map<String, Object> attrs = getAttrs(model);
        Map<String, String> conntMap = getConnects(attrs);
        Map<String, String> optsMap = getOpts(attrs);
        Set<String> connectKeySet = conntMap.keySet();
        Iterator<String> iterator = connectKeySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String connect = conntMap.get(key);
            String opt = optsMap.get(key);
            sql.append(connect).append(key).append(opt);
            Object value = null;
            Object[] values = null;
            if(attrs.get(key) instanceof Object[]){
                values = (Object[]) attrs.get(key);
            }else if(attrs.get(key) instanceof Object){
                value = attrs.get(key);
            }
            if (opt.trim().equals(LIKE.trim())) {
                if(value != null){
                    sql.append("'%").append(value).append("%'");
                }else{
                    sql.append("'%").append(values[0]).append("%'");
                }

            } else if (opt.trim().equals(BETWEEN.trim())) {
                sql.append(values[0]).append(AND).append(values[1]);
            } else {
                Object va = null;
                if(value != null){
                    va = value;
                }else{
                    va = values[0];
                }
                if (va instanceof String) {
                    sql.append("'").append(va).append("'");
                } else {
                    sql.append(va);
                }
            }
        }
        return sql.toString();
    }




}
