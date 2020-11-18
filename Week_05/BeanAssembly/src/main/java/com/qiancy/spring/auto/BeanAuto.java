package com.qiancy.spring.auto;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */
@Component
public class BeanAuto {

    public void approach() {
        System.out.println("自动扫描方式装备bean" );
    }
}
