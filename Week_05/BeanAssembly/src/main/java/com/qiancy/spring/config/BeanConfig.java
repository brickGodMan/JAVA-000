package com.qiancy.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */
@Configuration
@ComponentScan(basePackages = "com.qiancy.spring.*")
public class BeanConfig {

    @Bean
    public BeanAnnotation beanDemo(){
        return new BeanAnnotation();
    }

}
