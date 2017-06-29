package com.withball.baselibrary.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类名：UFTableSql
 * 描述：
 * 包名： com.withball.jfmlibrary
 * 项目名：Jfm_android
 * Created by qinzongke on 9/8/16.
 */
@Retention(RetentionPolicy.RUNTIME)

public @interface UFTableSql {
     String value() default "";
}
