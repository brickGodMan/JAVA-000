package com.qiancy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/16
 * @since 1.0.0
 */
@Data
@Component
public class School implements ISchool {

    @Autowired(required = true)
            Klass class1;

    @Resource(name = "student100")
    Student student100;

    @Override
    public String ding() {
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
        return String.format("self-starter invoke,content:Class1 have %s students and one is %s",this.class1.getStudents().size(),this.student100);
    }

}
