package com.withball.jfmlibrary.beans;

import java.util.List;

/**
 * 类名：SqlBean
 * 描述：
 * 包名： com.withball.jfmlibrary.beans
 * 项目名：Jfm_android
 * Created by qinzongke on 17/6/29.
 */

public class SqlBean {

    private String sql;
    private List<Object> sqlValues;
    private List<String> sqlStringValues;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getSqlValues() {
        return sqlValues;
    }

    public void setSqlValues(List<Object> sqlValues) {
        this.sqlValues = sqlValues;
    }

    public List<String> getSqlStringValues() {
        return sqlStringValues;
    }

    public void setSqlStringValues(List<String> sqlStringValues) {
        this.sqlStringValues = sqlStringValues;
    }
}
