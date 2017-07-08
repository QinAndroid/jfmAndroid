package com.withball.jfmlibrary.utils;

import android.database.Cursor;
import android.util.Log;

import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.constants.UFDBConstants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类名：UFCursorToMapUtil
 * 描述：
 * 包名： com.withball.jfmlibrary.utils
 * 项目名：Jfm_android
 * Created by qinzongke on 9/8/16.
 */
public class UFCursorToMapUtil {


    private static Map<String,Class> getModelFileds(UFModel<?>model){
        Map<String,Class> attrs = new HashMap<>();
        Class clazz = model.getClass();
        List<Field> publicFields = Arrays.asList(clazz.getFields());
        Field[] allFields = clazz.getDeclaredFields();
        int len = allFields.length;
        for(int i = 0;i<len;i++){
            Field field = allFields[i];
            if(publicFields.contains(field)){
                continue;
            }
            String name = field.getName();
            attrs.put(name,field.getType());
        }
        return attrs;
    }

    public static List<Map<String, Object>> cursorToListMap(Cursor cursor,UFModel<?> model) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (cursor == null) {
            return result;
        }
        int cpunt = cursor.getCount();
        Map<String, Class> attrs = getModelFileds(model);
        while (cursor.moveToNext()) {
            Object value = null;
            Set<String> keySet = attrs.keySet();
            Iterator<String> iterator = keySet.iterator();
            Map<String, Object> item = new HashMap<>();
            if (!item.containsKey(UFDBConstants.TABLENAME)) {
//                item.put(UFDBConstants.TABLENAME,model.TABLENAME);
                item.put(UFDBConstants.TABLENAME, model.get(UFDBConstants.TABLENAME).toString());
            }
            while (iterator.hasNext()) {
                String name = iterator.next();
                int position = cursor.getColumnIndex(name);
                if (position == -1) {
                    continue;
                }
                Class clazz = attrs.get(name);
                if (clazz.equals(Integer.class)) {
                    value = cursor.getInt(position);
                } else if (clazz.equals(String.class)) {
                    value = cursor.getString(position);
                } else if (clazz.equals(Long.class)) {
                    value = cursor.getLong(position);
                } else if (clazz.equals(Double.class)) {
                    value = cursor.getDouble(position);
                } else if (clazz.equals(Float.class)) {
                    value = cursor.getFloat(position);
                } else if (clazz.equals(Short.class)) {
                    value = cursor.getShort(position);
                }
                item.put(name, value);

            }
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public static Map<String, Object> cursorToMap(Cursor cursor,UFModel<?>model) {
        Map<String, Object> map = new HashMap<>();
        if (cursor == null) {
            return map;
        }
        if (!map.containsKey(UFDBConstants.TABLENAME)) {
//            map.put(UFDBConstants.TABLENAME,model.TABLENAME);
            map.put(UFDBConstants.TABLENAME, model.get(UFDBConstants.TABLENAME));
        }
        Map<String, Class> attrs = getModelFileds(model);
        while (cursor.moveToNext()) {
            Object value = null;
            Set<String> keySet = attrs.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String name = iterator.next();
                int position = cursor.getColumnIndex(name);
                if (position == -1) {
                    continue;
                }
                Class clazz = attrs.get(name);
                if (clazz.equals(Integer.class)) {
                    value = cursor.getInt(position);
                } else if (clazz.equals(String.class)) {
                    value = cursor.getString(position);
                } else if (clazz.equals(Long.class)) {
                    value = cursor.getLong(position);
                } else if (clazz.equals(Double.class)) {
                    value = cursor.getDouble(position);
                } else if (clazz.equals(Float.class)) {
                    value = cursor.getFloat(position);
                } else if (clazz.equals(Short.class)) {
                    value = cursor.getShort(position);
                }
                map.put(name, value);
            }
        }
        cursor.close();
        return map;
    }


}
