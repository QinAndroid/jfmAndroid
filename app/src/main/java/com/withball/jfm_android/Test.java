package com.withball.jfm_android;


import com.withball.baselibrary.ModelUtil;
import com.withball.baselibrary.beans.UFClass;
import com.withball.baselibrary.generator.UFGeneratorClass;

import java.io.IOException;
import java.util.List;

/**
 * 类名：Test
 * 描述：
 * 包名： com.withball.jfm_android
 * 项目名：Jfm_android
 * Created by qinzongke on 8/29/16.
 */
public class Test {

    public static void main(String[] args) {
        List<UFClass> classes = ModelUtil.getGeneratorClasses();
        String path = "/Users/qinzongke/Projects/Jfm_android/app/src/main/java/com/withball/jfm_android/model";
        try {
            UFGeneratorClass.generatorClass(path,classes);
        }catch (IOException e){
            e.printStackTrace();
        }

    }




}

