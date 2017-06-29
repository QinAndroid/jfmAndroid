package com.withball.baselibrary.generator;


import com.withball.baselibrary.beans.UFClass;
import com.withball.baselibrary.utils.UFClassContentGeneratrorUtil;
import com.withball.baselibrary.utils.UFFileUtil;
import com.withball.baselibrary.utils.UFStringUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 类名：UFGeneratorClass
 * 描述：
 * 包名： com.withball.jfmlibrary.generator
 * 项目名：Jfm_android
 * Created by qinzongke on 8/29/16.
 */
public class UFGeneratorClass {


    public static void generatorClass(String path,List<UFClass> clazzes) throws IOException {

        //创建model包
        UFFileUtil.createFileFolder(path);
        for(UFClass clazz : clazzes){
            String className = UFStringUtil.getModelName(clazz)+ ".java";
            File file = new File(path,className);
            if(UFFileUtil.isFileExists(file)){
                UFFileUtil.deleteFile(file);

            }
            if(!clazz.isHasPrimaryKey()){
                throw new IllegalArgumentException("Missing  PrimaryKey");
            }
            if(clazz.getColumns().size() == 0){
                throw new IllegalArgumentException("Missing Columns");
            }

            //创建java文件
            UFFileUtil.createFile(file);
            String content = UFClassContentGeneratrorUtil.generatorContent(clazz);
            UFFileUtil.writeContent(file, content);
        }
    }
}
