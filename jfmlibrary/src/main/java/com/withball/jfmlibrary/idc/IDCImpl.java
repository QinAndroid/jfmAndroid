package com.withball.jfmlibrary.idc;

import android.database.Cursor;

import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.beans.SqlBean;
import com.withball.jfmlibrary.constants.UFDBConstants;
import com.withball.jfmlibrary.dbmanager.UFDBHelper;
import com.withball.jfmlibrary.kits.SqlKits;
import com.withball.jfmlibrary.utils.UFCursorToMapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：IDCImpl
 * 描述：
 * 包名： com.withball.jfmlibrary.idc
 * 项目名：Jfm_android
 * Created by qinzongke on 17/6/29.
 */

public class IDCImpl implements IDC {

    @Override
    public void setIsPrint(boolean isPrint) {

    }

    @Override
    public void beginTranstation() {

    }

    @Override
    public void endTranstation() {

    }


    @Override
    public <T extends UFModel<T>> UFModel.QueryOut queryAll(UFModel<?> model) {
        SqlBean sql = SqlKits.queryAll(model);
        Cursor cursor = UFDBHelper.getInstance().query(sql.getSql(),sql.getSqlStringValues());
        List<Map<String, Object>> result= UFCursorToMapUtil.cursorToListMap(cursor,model);
        List<T> resultInstance = new ArrayList<T>();
        Class<? extends UFModel<?>> modelClass = (Class<? extends UFModel<?>>) model.getClass();
        for (int index = 0; index < result.size(); index++) {
            T instance = null;
            try {
                instance = (T) modelClass.newInstance();
                instance.put(result.get(index));
            } catch (Exception e) {
                throw (new RuntimeException(e.getLocalizedMessage()));
            }
            resultInstance.add(instance);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(UFDBConstants.QUERYDATA, resultInstance);
        data.put(UFDBConstants.QUERYDATACOUNT, resultInstance.size());
        UFModel.QueryOut out = new UFModel.QueryOut(data);
        return out;
    }

    @Override
    public <T extends UFModel<T>> UFModel.QueryOut queryFirst(UFModel<?> model) {
        SqlBean sql = SqlKits.queryFirst(model);
        Cursor cursor = UFDBHelper.getInstance().query(sql.getSql(),sql.getSqlStringValues());
        Map<String,Object> result = UFCursorToMapUtil.cursorToMap(cursor,model);
        Class<? extends UFModel<?>> modelClass = (Class<? extends UFModel<?>>) model.getClass();
        T instance = null;
        try {
            instance = (T) modelClass.newInstance();
            instance.put(result);
        } catch (Exception e) {
            throw (new RuntimeException(e.getLocalizedMessage()));
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put(UFDBConstants.QUERYDATA, instance);
        data.put(UFDBConstants.QUERYDATACOUNT, 1);
        UFModel.QueryOut out = new UFModel.QueryOut(data);
        return out;
    }

    @Override
    public void save(UFModel model) {
        SqlBean sql = SqlKits.save(model);
        UFDBHelper.getInstance().execuSql(sql.getSql(),sql.getSqlValues());
    }

    @Override
    public void update(UFModel model) {
        SqlBean sql = SqlKits.update(model);
        UFDBHelper.getInstance().execuSql(sql.getSql(),sql.getSqlValues());
    }

    @Override
    public void delete(UFModel model) {
        SqlBean sql = SqlKits.delete(model);
        UFDBHelper.getInstance().execuSql(sql.getSql(),sql.getSqlValues());
    }
}
