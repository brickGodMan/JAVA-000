package com.qiancy.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */
public class Aop2 {

    public void start() {
        System.out.println("start ==============");
    }

    public void end() {
        System.out.println("end =================");
    }

    public void round(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aroud start ===========");
        joinPoint.proceed();
        System.out.println("aroud end ===========");
    }
}
