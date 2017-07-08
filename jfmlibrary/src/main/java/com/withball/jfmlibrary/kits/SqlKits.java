package com.withball.jfmlibrary.kits;

import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.beans.SqlBean;
import com.withball.jfmlibrary.constants.UFDBConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类名：SqlKits
 * 描述：
 * 包名： com.withball.jfmlibrary.kits
 * 项目名：Jfm_android
 * Created by qinzongke on 17/6/29.
 */

public class SqlKits {

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

    private static String[] getOutPutField(Map<String, Object> attrs) {
        return (String[]) attrs.get(UFDBConstants.OUTPUTFIELD);
    }


    public static SqlBean queryAll(UFModel model) {
        Map<String, Object> attrs = getAttrs(model);
        StringBuffer sql = getQueryHeader(model);
        Map<String, String> conntMap = getConnects(attrs);
        Map<String, String> optsMap = getOpts(attrs);
        Set<String> connectKeySet = conntMap.keySet();
        Iterator<String> iterator = connectKeySet.iterator();
        List<String> arges = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String connect = conntMap.get(key);
            String opt = optsMap.get(key);
            Object value = null;
            Object[] values = null;
            if (attrs.get(key) instanceof Object[]) {
                values = (Object[]) attrs.get(key);
            } else if (attrs.get(key) instanceof Object) {
                value = attrs.get(key);
            }
            if (value == null && (values == null || values.length == 0)) {
                continue;
            }
            if (opt.trim().equals(LIKE.trim())) {
                if (value == null) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add("%" + String.valueOf(values[0]) + "%");
                } else {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add("%" + String.valueOf(value) + "%");
                }
            } else if (opt.trim().equals(BETWEEN.trim())) {
                if (values != null && values.length >= 2) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?").append(AND).append("?");
                    arges.add(String.valueOf(values[0]));
                    arges.add(String.valueOf(values[1]));
                }
            } else {
                if (value == null) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add(String.valueOf(values[0]));
                } else {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add(String.valueOf(value));
                }
            }
        }
        String orderStr = getOrderSql(attrs).toString();
        sql.append(orderStr);
        SqlBean bean = new SqlBean();
        bean.setSql(sql.toString());
        bean.setSqlStringValues(arges);
        return bean;
    }

    /**
     * order by
     *
     * @param attrs
     * @return
     */
    private static String getOrderSql(Map<String, Object> attrs) {
        StringBuffer sql = new StringBuffer();
        String[] orders = (String[]) attrs.get(UFDBConstants.ORDERKEY);
        StringBuffer order = new StringBuffer();
        String suffix = ",";
        if (orders != null && orders.length != 0) {
            for (String column : orders) {
                if (column == null || column.trim().equals("")) {
                    continue;
                }
                order.append(column).append(suffix);
            }
            if (order.length() != 0 || order.toString().endsWith(suffix)) {
                order = order.delete(order.length() - 1, order.length());
                sql.append(ORDERBY).append(order);
            }
        }
        return sql.toString();
    }


    public static SqlBean queryFirst(UFModel model) {
        Map<String, Object> attrs = getAttrs(model);
        StringBuffer sql = getQueryHeader(model);
        Map<String, String> conntMap = getConnects(attrs);
        Map<String, String> optsMap = getOpts(attrs);
        Set<String> connectKeySet = conntMap.keySet();
        Iterator<String> iterator = connectKeySet.iterator();
        List<String> arges = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String connect = conntMap.get(key);
            String opt = optsMap.get(key);
            Object value = null;
            Object[] values = null;
            if (attrs.get(key) instanceof Object[]) {
                values = (Object[]) attrs.get(key);
            } else if (attrs.get(key) instanceof Object) {
                value = attrs.get(key);
            }
            if (value == null && (values == null || values.length == 0)) {
                continue;
            }
            if (opt.trim().equals(LIKE.trim())) {
                if (value == null) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add("%" + String.valueOf(values[0]) + "%");
                } else {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add("%" + String.valueOf(value) + "%");
                }
            } else if (opt.trim().equals(BETWEEN.trim())) {
                if (values != null && values.length >= 2) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?").append(AND).append("?");
                    arges.add(String.valueOf(values[0]));
                    arges.add(String.valueOf(values[1]));
                }
            } else {
                if (value == null) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add(String.valueOf(values[0]));
                } else {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add(String.valueOf(value));
                }
            }
        }
        sql.append(LIMIT);
        SqlBean bean = new SqlBean();
        bean.setSql(sql.toString());
        bean.setSqlStringValues(arges);
        return bean;
    }

    public static SqlBean save(UFModel model) {
        Map<String, Object> attrs = getAttrs(model);
        String tableName = getTableName(attrs);
        if (attrs.containsKey(UFDBConstants.CONNECT)) {
            attrs.remove(UFDBConstants.CONNECT);
        }
        if (attrs.containsKey(UFDBConstants.CONDITION)) {
            attrs.remove(UFDBConstants.CONDITION);
        }
        attrs.remove(UFDBConstants.TABLENAME);
        Set<String> keySet = attrs.keySet();
        Collection<Object> valueSet = attrs.values();
        Iterator<Object> iterator = valueSet.iterator();

        StringBuffer values = new StringBuffer();
        List<Object> args = new ArrayList<>();
        while (iterator.hasNext()) {
            Object value = iterator.next();
            args.add(value);
            values.append("?,");
        }
        String keys = keySet.toString().substring(1, keySet.toString().length() - 1);
        String value = values.substring(0, values.length() - 1);
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append(INSERT).append(tableName).append("(").append(keys).append(")");
        sqlStr.append(VALUES).append("(").append(value).append(")");
        SqlBean sql = new SqlBean();
        sql.setSql(sqlStr.toString());
        sql.setSqlValues(args);
        return sql;
    }


    public static SqlBean update(UFModel model) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> attrs = getAttrs(model);
        String tableName = getTableName(attrs);
        if (attrs.containsKey(UFDBConstants.CONNECT)) {
            attrs.remove(UFDBConstants.CONNECT);
        }
        if (attrs.containsKey(UFDBConstants.CONDITION)) {
            attrs.remove(UFDBConstants.CONDITION);
        }
//        if (attrs.containsKey(UFDBConstants.TABLENAME)) {
//            attrs.remove(UFDBConstants.TABLENAME);
//        }
        sql.append(UPDATE).append(tableName).append(SET);
        StringBuffer setValues = new StringBuffer();
        Set<String> keySet = attrs.keySet();
        Iterator<String> iterator = keySet.iterator();
        List<Object> args = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals(UFDBConstants.PRIMARYKEY)) {
                continue;
            }
            if (key.equals(UFDBConstants.TABLENAME)) {
                continue;
            }
            Object value = attrs.get(key);
            if (value == null) {
                continue;
            }
            setValues.append(key).append(EQ).append("?");
            args.add(value);
            setValues.append(",");
        }
        if (setValues.length() <= 0) {
            throw new IllegalArgumentException("update Missing SetValues ");
        }
        sql.append(setValues.substring(0, setValues.length() - 1));
        sql.append(WHERE);
        if (attrs.containsKey(UFDBConstants.PRIMARYKEY)) {
            Object id = attrs.get(UFDBConstants.PRIMARYKEY);
            sql.append(AND).append(UFDBConstants.PRIMARYKEY).append(EQ).append("?");
            args.add(id);
        }
        SqlBean bean = new SqlBean();
        bean.setSql(sql.toString());
        bean.setSqlValues(args);
        return bean;
    }

    /**
     * delete table where
     * @param model
     * @return
     */
    public static SqlBean delete(UFModel model) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> attrs = getAttrs(model);
        String tableName = getTableName(attrs);
        sql.append(DELETE).append(tableName).append(WHERE);
        Map<String, String> conntMap = getConnects(attrs);
        Map<String, String> optsMap = getOpts(attrs);
        Set<String> connectKeySet = conntMap.keySet();
        Iterator<String> iterator = connectKeySet.iterator();
        List<Object> arges = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String connect = conntMap.get(key);
            String opt = optsMap.get(key);
            Object value = null;
            Object[] values = null;
            if (attrs.get(key) instanceof Object[]) {
                values = (Object[]) attrs.get(key);
            } else if (attrs.get(key) instanceof Object) {
                value = attrs.get(key);
            }
            if (value == null && (values == null || values.length == 0)) {
                continue;
            }
            if (opt.trim().equals(LIKE.trim())) {
                if (value == null) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add("%" + String.valueOf(values[0]) + "%");
                } else {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add("%" + String.valueOf(value) + "%");
                }
            } else if (opt.trim().equals(BETWEEN.trim())) {
                if (values != null && values.length >= 2) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?").append(AND).append("?");
                    arges.add(String.valueOf(values[0]));
                    arges.add(String.valueOf(values[1]));
                }
            } else {
                if (value == null) {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add(String.valueOf(values[0]));
                } else {
                    sql.append(connect).append(key).append(opt);
                    sql.append("?");
                    arges.add(String.valueOf(value));
                }
            }
        }
        SqlBean sqlBean = new SqlBean();
        sqlBean.setSql(sql.toString());
        sqlBean.setSqlValues(arges);
        return sqlBean;
    }


    /**
     * select * from xx where 1=1
     *
     * @param model
     * @return
     */
    private static StringBuffer getQueryHeader(UFModel model) {
        Map<String, Object> attrs = getAttrs(model);
        String[] outPutFields = getOutPutField(attrs);
        String tableName = getTableName(attrs);
        StringBuffer sql = new StringBuffer();
        sql.append(SELECT);
        if (outPutFields != null && outPutFields.length != 0) {
            StringBuffer fields = new StringBuffer();
            for (String field : outPutFields) {
                if (field != null && !field.equals("")) {
                    fields.append(field).append(",");
                }
            }
            if (fields.toString().endsWith(",")) {
                fields = new StringBuffer(fields.subSequence(0, fields.length() - 1));
            }
            if (fields.length() != 0) {
                sql.append(fields);
            } else {
                sql.append(ALL);
            }
        } else {
            sql.append(ALL);
        }
        sql.append(FROM).append(tableName).append(WHERE);
        return sql;
    }

}
