package com.qiancy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/16
 * @since 1.0.0
 */
@Data
public class Klass {

    List<Student> students;

    public void dong(){
        System.out.println("Klass…………");
    }

}
