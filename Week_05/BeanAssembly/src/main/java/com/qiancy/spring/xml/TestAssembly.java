package com.qiancy.spring.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */
public class TestAssembly {

    public static void main(String[] args) {
        //第一种通过xml装配bean
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BeanAssembly beanAssembly = (BeanAssembly) context.getBean("beanAssemblyXml1");
        beanAssembly.approach();

        BeanList beanList = context.getBean(BeanList.class);

        System.out.printf("BeanList 对象被Aop实际代理后的对象%s%n",beanList.getClass());
        beanList.done();
    }
}
