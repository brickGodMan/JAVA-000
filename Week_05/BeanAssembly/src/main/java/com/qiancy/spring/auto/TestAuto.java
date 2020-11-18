package com.qiancy.spring.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */

public class TestAuto {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        BeanController beanController = context.getBean(BeanController.class);
        beanController.getBeanAuto().approach();
        beanController.getBeanAutoRes().approach();
    }
}
