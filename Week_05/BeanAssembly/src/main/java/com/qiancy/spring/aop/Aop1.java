package com.qiancy.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/15
 * @since 1.0.0
 */

@Component
@Aspect
public class Aop1 {

    @Pointcut(value = "execution(* com.qiancy.spring.xml.BeanAssembly.approach(..))")
    public void point(){}

    @Before(value = "point()")
    public void before() {
        System.out.println("begin =========");
    }

    @AfterReturning(value = "point()")
    public void after(){
        System.out.println("========>after");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("========>around begin ");
        joinPoint.proceed();
        System.out.println("========>around after ");

    }
}
