package com.withball.jfm_android.model;

import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.constants.UFDBConstants;
import com.withball.baselibrary.annotations.UFTableSql;

@UFTableSql(value = "CREATE TABLE user_1(name VARCHAR,address VARCHAR,id Integer PRIMARY KEY AUTOINCREMENT)")
public class User  <M extends User<M>> extends UFModel<M>{


	public static  final  String TABLENAME  = "user_1";

	public static  final  String ID = "id";

	public static  final  String NAME = "name";

	public static  final  String ADDRESS = "address";

	private Integer id;

	private String name;

	private String address;

	public User(){
		set(UFDBConstants.TABLENAME,"user_1");
	}


	public Integer getId(){
		return get("id");
	}

	public void setId(Integer  id){
		set("id",id);
	}

	public String getName(){
		return get("name");
	}

	public void setName(String  name){
		set("name",name);
	}

	public String getAddress(){
		return get("address");
	}

	public void setAddress(String  address){
		set("address",address);
	}
}