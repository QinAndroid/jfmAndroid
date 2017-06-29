package com.withball.jfmlibrary.idc;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.UFModel.QueryOut;
import com.withball.jfmlibrary.constants.UFDBConstants;
import com.withball.jfmlibrary.dbmanager.UFDBHelper;
import com.withball.jfmlibrary.kits.UFSqlKits;
import com.withball.jfmlibrary.utils.UFCursorToMapUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类名：UFIDCImpl
 * 描述：
 * 包名： com.withball.jfmlibrary.idc
 * 项目名：Jfm_android
 * Created by qinzongke on 9/5/16.
 */
public class UFIDCImpl implements UFIDC {


    private Map<String, Object> getAtrrs(UFModel model) {
        return model.dcExport();
    }

    private void print(String sql) {
        if (isPrintf) {
            System.err.println(sql);
        }

    }

    private boolean isPrintf = true;

    @Override
    public void setIsPrint(boolean isPrint) {
        this.isPrintf = isPrint;
    }


    @Override
    public <T extends UFModel<T>> QueryOut queryAll(UFModel<?> model) {
        String sql = UFSqlKits.queryAll(model);
        print(sql);
        Cursor cursor = UFDBHelper.getInstance().getDB().rawQuery(sql,null);

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
        QueryOut out = new QueryOut(data);
        return out;
    }

    @Override
    public <T extends UFModel<T>> QueryOut queryFirst(UFModel<?> model) {
        String sql = UFSqlKits.queryFirst(model);
        print(sql);
        Cursor cursor = UFDBHelper.getInstance().getDB().rawQuery(sql,null);
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
        QueryOut out = new QueryOut(data);
        return out;
    }

    @Override
    public void save(UFModel model) {
        String sql = UFSqlKits.save(model);
        UFDBHelper.getInstance().getDB().execSQL(sql);
    }

    @Override
    public void update(UFModel model) {
        String sql = UFSqlKits.update(model);
        print(sql);
        UFDBHelper.getInstance().getDB().execSQL(sql);
    }

    @Override
    public void delete(UFModel model) {
        String sql = UFSqlKits.delete(model);
        print(sql);
        UFDBHelper.getInstance().getDB().execSQL(sql);
    }

    @Override
    public void beginTranstation() {
        UFDBHelper.getInstance().getDB().beginTransaction();
    }

    @Override
    public void endTranstation() {
        UFDBHelper.getInstance().getDB().setTransactionSuccessful();
        UFDBHelper.getInstance().getDB().endTransaction();

    }
}
