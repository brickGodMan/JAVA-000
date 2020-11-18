package com.qiancy.spring.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */
@Component
public class BeanController {

    @Autowired
    private BeanAuto beanAuto;

    @Resource
    private BeanAuto resBeanAuto;

    public BeanAuto getBeanAuto() {
        System.out.println("Autowired 获取bean");
        return beanAuto;
    }

    public BeanAuto getBeanAutoRes() {
        System.out.println("Resource 获取bean");
        return resBeanAuto;
    }
}
