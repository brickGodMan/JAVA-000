package com.qiancy.springboot.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Value;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/18
 * @since 1.0.0
 */
@Configuration
public class SpringHikariConfig {

//    @Value("${sping.datasource.url}")
//    private String url;
//    @Value("${sping.datasource.hikari.username}")
//    private String userName;
//
//    @Value("${sping.datasource.hikari.driver-class-name}")
//    private String driverClass;

    @Bean
    public DataSource dataSource() {
        InputStream in = SpringHikariConfig.class.getClassLoader().getResourceAsStream("application.yaml");
        Properties properties = new Properties();
        HikariDataSource dataSource = null;
        try {
            properties.load(in);
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("url"));
            config.setDriverClassName(properties.getProperty("driverName"));
            config.setUsername(properties.getProperty("user"));
            config.setConnectionTimeout(Long.parseLong(properties.getProperty("connection-timeout")));
            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return dataSource;
    }
}
