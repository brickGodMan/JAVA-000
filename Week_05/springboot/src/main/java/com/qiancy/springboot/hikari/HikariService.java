package com.qiancy.springboot.hikari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/18
 * @since 1.0.0
 */
@Service
public class HikariService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> approach() {
        String sql = "select * from t_user";
        List<User> users = jdbcTemplate.query(sql,  new BeanPropertyRowMapper(User.class));
        return users.stream().map(v -> v.toString()).collect(Collectors.toList());
    }
}
