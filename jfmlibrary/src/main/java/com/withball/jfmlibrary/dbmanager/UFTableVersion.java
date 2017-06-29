package com.withball.jfmlibrary.dbmanager;

import com.withball.baselibrary.annotations.UFTableSql;
import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.constants.UFDBConstants;

/**
 * 类名：UFTableVersion
 * 描述：
 * 包名： com.withball.jfmlibrary.dbmanager
 * 项目名：Jfm_android
 * Created by qinzongke on 9/13/16.
 */
@UFTableSql(value = "CREATE TABLE tableVersion(originVersion INTEGER,currentVersion INTEGER,name VARCHAR PRIMARY KEY)")
public class UFTableVersion  <M extends UFTableVersion<M>> extends UFModel<M> {


    public static  final  String TABLENAME  = "tableVersion";

    public static  final  String NAME = "name";

    public static  final  String ORIGINVERSION = "originVersion";

    public static  final  String CURRENTVERSION = "currentVersion";



    public UFTableVersion(){
        set(UFDBConstants.TABLENAME,"tableVersion");
    }


    public Integer getId(){
        return get("id");
    }

    public void setId(Integer  id){
        set("id",id);
    }

    public Integer getColumn1(){
        return get("column1");
    }

    public void setColumn1(Integer  column1){
        set("column1",column1);
    }

    public String getColumn2(){
        return get("column2");
    }

    public void setColumn2(String  column2){
        set("column2",column2);
    }

    public String getColumn3(){
        return get("column3");
    }

    public void setColumn3(String  column3){
        set("column3",column3);
    }

    public String getColumn4(){
        return get("column4");
    }

    public void setColumn4(String  column4){
        set("column4",column4);
    }

    public Integer getColumn5(){
        return get("column5");
    }

    public void setColumn5(Integer  column5){
        set("column5",column5);
    }
}
