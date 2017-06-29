package com.withball.baselibrary;

import com.withball.baselibrary.beans.UFClass;
import com.withball.baselibrary.beans.UFColumnItem;
import com.withball.baselibrary.enums.UFColumnType;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：ModelUtil
 * 描述：
 * 包名： com.withball.jfm_android
 * 项目名：Jfm_android
 * Created by qinzongke on 9/12/16.
 */
public class ModelUtil {

    public static List<UFClass> getGeneratorClasses(){
        List<UFClass> classes = new ArrayList<>();
        UFClass entity = new UFClass("entity1",1);
        entity.setAutoIncrePrimaryKey();
        entity.addColumns(new UFColumnItem("column1", UFColumnType.INTEGER));
        entity.addColumns(new UFColumnItem("column2", UFColumnType.STRING));
        entity.addColumns(new UFColumnItem("column3",UFColumnType.STRING));
//        entity.addColumns(new UFColumnItem("column4",UFColumnType.STRING));
//        entity.addColumns(new UFColumnItem("column5",UFColumnType.INTEGER));


        UFClass user = new UFClass("user",1);
        user.setAutoIncrePrimaryKey();
        user.addColumns(new UFColumnItem("name", UFColumnType.STRING));
        user.addColumns(new UFColumnItem("address", UFColumnType.STRING));
        classes.add(entity);
        classes.add(user);

        return classes;



    }
}
