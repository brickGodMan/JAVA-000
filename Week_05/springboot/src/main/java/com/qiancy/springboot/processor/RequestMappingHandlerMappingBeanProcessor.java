package com.qiancy.springboot.api;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2021/1/21
 * @since 1.0.0
 */
public class RequestMappingHandlerMappingBeanProcessor implements BeanPostProcessor {


    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

//        requestMappingHandlerMapping.getHandler()
        return bean;
    }


}
