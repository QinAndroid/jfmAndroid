package com.withball.jfm_android.model;

import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.constants.UFDBConstants;
import com.withball.baselibrary.annotations.UFTableSql;

@UFTableSql(value = "CREATE TABLE entity1_1(column1 INTEGER,column2 VARCHAR,column3 VARCHAR,id Integer PRIMARY KEY AUTOINCREMENT)")
public class Entity1  <M extends Entity1<M>> extends UFModel<M>{


	public static  final  String TABLENAME  = "entity1_1";

	public static  final  String ID = "id";

	public static  final  String COLUMN1 = "column1";

	public static  final  String COLUMN2 = "column2";

	public static  final  String COLUMN3 = "column3";

	private Integer id;

	private Integer column1;

	private String column2;

	private String column3;

	public Entity1(){
		set(UFDBConstants.TABLENAME,"entity1_1");
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
}