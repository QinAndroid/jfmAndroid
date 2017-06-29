package com.withball.jfm_android;

import android.app.Application;

//import com.withball.jfm_android.model.UFEntity1;
import com.withball.jfmlibrary.dbmanager.UFDBHelper;

/**
 * 类名：MyApplication
 * 描述：
 * 包名： com.withball.jfm_android
 * 项目名：Jfm_android
 * Created by qinzongke on 9/7/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        dbtest();
    }


    private void dbtest() {
        UFDBHelper.getInstance(getApplicationContext(),"test");
    }
}
