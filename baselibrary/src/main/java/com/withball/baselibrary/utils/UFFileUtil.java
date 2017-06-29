package com.withball.baselibrary.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 类名：UFFileUtil
 * 描述：
 * 包名： com.withball.jfmlibrary.utils
 * 项目名：Jfm_android
 * Created by qinzongke on 8/30/16.
 */
public class UFFileUtil {


    /**
     * 文件是否存在
     * @param file
     * @return
     */
    public static boolean isFileExists(File file){
        return file.exists();
    }

    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file){
        file.delete();
    }


    /**
     * 创建文件夹
     * @param path
     */
    public static void createFileFolder(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 创建文件
     * @param file
     */
    public static void createFile(File file) throws IOException{
        file.createNewFile();
    }

    /**
     * 文件写入
     * @param file
     * @param content
     */
    public static void writeContent(File file,String content){
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
