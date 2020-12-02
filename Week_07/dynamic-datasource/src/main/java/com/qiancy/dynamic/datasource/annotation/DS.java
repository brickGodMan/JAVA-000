package com.qiancy.dynamic.datasource.annotation;


import com.qiancy.dynamic.datasource.constants.DataSourceConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义数据源注解
 *
 * @author qiancy
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {

    String value() default DataSourceConstants.DS_KEY_MASTER;

}
