package com.qiancy.config;

import com.qiancy.Klass;
import com.qiancy.School;
import com.qiancy.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能简述：配置类
 *
 * @author qiancy
 * @create 2020/11/16
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({School.class, Student.class, Klass.class})
@EnableConfigurationProperties(SelfProperties.class)
@PropertySource(value = "classpath:self.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@ConditionalOnProperty(prefix = SelfProperties.PREFIX, name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class SpringBootConfiguration {

    @Autowired
    private final SelfProperties props;

    @Bean
    @Autowired(required = false)
    public School school() {
        return new School();
    }


    @Bean
    @Autowired(required = false)
    public Student student100() {
        return new Student(100, "100号学生");
    }

    @Bean
    @Autowired(required = false)
    public Klass class1() {
        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 5; i++) {
            Student student = new Student(i, i + "号学生");
            students.add(student);
        }
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }


}
