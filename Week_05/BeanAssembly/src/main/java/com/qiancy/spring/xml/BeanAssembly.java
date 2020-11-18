package com.qiancy.spring.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BeanAssembly implements Serializable {

    private String beanId;

    private String beanName;


    public BeanAssembly getInstance() {
        return new BeanAssembly("1","第一种方式");
    }

    public void approach() {
        System.out.println("装配类……");
    }
}
