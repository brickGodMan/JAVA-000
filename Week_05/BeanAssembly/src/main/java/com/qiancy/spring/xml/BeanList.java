package com.qiancy.spring.xml;

import lombok.Data;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */
@Data
public class BeanList {
    List<BeanAssembly> beanAssemblies;

    public void done() {
        System.out.println(this.getBeanAssemblies());
    }

}
