package com.withball.jfmlibrary.idc;

import com.withball.jfmlibrary.UFModel;

/**
 * 类名：IDC
 * 描述：
 * 包名： com.withball.jfmlibrary.idc
 * 项目名：Jfm_android
 * Created by qinzongke on 17/6/29.
 */

public interface IDC {

    void setIsPrint(boolean isPrint);

    void beginTranstation();

    void endTranstation();


    <T extends UFModel<T>>UFModel.QueryOut queryAll(UFModel<?> model);

    <T extends UFModel<T>>UFModel.QueryOut queryFirst(UFModel<?> model);

    void save(UFModel model);

    void update(UFModel model);

    void delete(UFModel model);

}
