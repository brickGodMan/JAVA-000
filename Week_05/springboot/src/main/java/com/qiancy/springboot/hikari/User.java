package com.qiancy.springboot.hikari;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/18
 * @since 1.0.0
 */
@Data
public class User {
    private Integer userId;
    private String userName;
    private String userAge;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge='" + userAge + '\'' +
                '}';
    }
}
