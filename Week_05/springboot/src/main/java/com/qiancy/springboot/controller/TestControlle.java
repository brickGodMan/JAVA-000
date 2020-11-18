package com.qiancy.springboot.controller;

import com.qiancy.School;
import com.qiancy.springboot.hikari.HikariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/16
 * @since 1.0.0
 */
@RestController
public class TestControlle {
    /**
     * self-start 中定义的bean
     */
    @Autowired
    private School school;

    @Autowired
    private HikariService hikariService;

    /**
     * 测试调用自定义starter方法
     * @return
     */
    @RequestMapping(value = "/test")
    public String test() {
        return school.ding();
    }
    @RequestMapping(value = "/hikari")
    public List<String> hikari() {
        return hikariService.approach();
    }

}
