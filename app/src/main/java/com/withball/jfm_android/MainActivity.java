package com.withball.jfm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.withball.jfm_android.model.Entity1;
import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.beans.UFSorted;
import com.withball.jfmlibrary.idc.IDC;
import com.withball.jfmlibrary.idc.IDCImpl;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();

    }


    private void test(){

        IDC idc = new IDCImpl();
        Entity1 entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setColumn1(10);
        entity1.setColumn2("1234'");
        entity1.save();

//
        entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setId(1);
        entity1.setColumn1(10);
        entity1.setOrders(Entity1.ID);
        List<Entity1> result = entity1.queryAll().getAll();
        for(Entity1 item:result){
            Log.e("id",item.getId()+"");
            Log.e("column1",item.getColumn1()+"");
            Log.e("column2",item.getColumn2());
        }
        Log.e("---->","----->");
//
        entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setColumn1(UFModel.Match.BW(1,3));
        entity1.setOrders(Entity1.ID);
        result = entity1.queryAll().getAll();
        for(Entity1 item:result){
            Log.e("id",item.getId()+"");
            Log.e("column1",item.getColumn1()+"");
            Log.e("column2",item.getColumn2());
        }
//        Log.e("---->","----->");
        entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setColumn2(UFModel.Match.LIKE("3"));
        entity1.setOrders(UFSorted.DESC(Entity1.ID));
        result = entity1.queryAll().getAll();
        for(Entity1 item:result){
            Log.e("id",item.getId()+"");
            Log.e("column1",item.getColumn1()+"");
            Log.e("column2",item.getColumn2());
        }
////
        entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setOrders(UFSorted.DESC(Entity1.ID));
        entity1.setColumn2(UFModel.Match.LIKE("3"));
        UFModel.QueryOut<Entity1> queryOut1 = entity1.querFirst();
        Log.e("first----->","first");
        Entity1 item = queryOut1.getFirst();
        Log.e("id",item.getId()+"");
        Log.e("column1",item.getColumn1()+"");
        Log.e("column2",item.getColumn2());
//
        entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setId(43);
        entity1.setColumn1(1);
        entity1.setColumn2("aaaaa");
        entity1.update();
//
        entity1 = new Entity1();
        entity1.setIdc(idc);
        result = entity1.queryAll().getAll();
        for(Entity1 item1:result){
            Log.e("id",item1.getId()+"");
            Log.e("column1",item1.getColumn1()+"");
            Log.e("column2",item1.getColumn2());
        }
//
        entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setId(43);
        entity1.delete();
        Log.e("Delete--->","delete");

        entity1 = new Entity1();
        entity1.setIdc(idc);
        result = entity1.queryAll().getAll();
        for(Entity1 item1:result){
            Log.e("id",item1.getId()+"");
            Log.e("column1",item1.getColumn1()+"");
            Log.e("column2",item1.getColumn2());
        }



    }
}
