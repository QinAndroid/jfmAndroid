package com.withball.jfm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.withball.jfm_android.model.Entity1;
import com.withball.jfmlibrary.UFModel;
import com.withball.jfmlibrary.idc.UFIDC;
import com.withball.jfmlibrary.idc.UFIDCImpl;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();

    }


    private void test(){

        UFIDC idc = new UFIDCImpl();
        Entity1 entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setColumn1(10);
        entity1.setColumn2("1234");
        entity1.save();

//
        entity1 = new Entity1();
        entity1.setIdc(idc);
        entity1.setColumn1(UFModel.Match.LE(5));
        entity1.setOrders(Entity1.ID);
        UFModel.QueryOut<Entity1> queryOut = entity1.queryAll();
        List<Entity1> result = queryOut.getAll();
        for(Entity1 item:result){
            Log.e("id",item.getId()+"");
            Log.e("column1",item.getColumn1()+"");
            Log.e("column2",item.getColumn2());
        }

        entity1 = new Entity1();
        entity1.setIdc(idc);
        UFModel.QueryOut<Entity1> queryOut1 = entity1.querFirst();
        Log.e("first----->","first");
        Entity1 item = queryOut1.getFirst();
        Log.e("id",item.getId()+"");
        Log.e("column1",item.getColumn1()+"");
        Log.e("column2",item.getColumn2());


    }
}
