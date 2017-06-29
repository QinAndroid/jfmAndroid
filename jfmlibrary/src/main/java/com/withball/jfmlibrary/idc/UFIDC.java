package com.withball.jfmlibrary.idc;

import com.withball.jfmlibrary.UFModel;

import java.util.List;

import com.withball.jfmlibrary.UFModel.QueryOut;

/**
 * 类名：UFIDC
 * 描述：
 * 包名： com.withball.jfmlibrary.idc
 * 项目名：Jfm_android
 * Created by qinzongke on 9/5/16.
 */
public interface UFIDC {

    void setIsPrint(boolean isPrint);

    void beginTranstation();

    void endTranstation();


    <T extends UFModel<T>>QueryOut queryAll(UFModel<?> model);

    <T extends UFModel<T>>QueryOut queryFirst(UFModel<?> model);

    void save(UFModel model);

    void update(UFModel model);

    void delete(UFModel model);


}
