package com.qiancy.dynamic.datasource.config;

import com.qiancy.dynamic.datasource.constants.DataSourceConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;



/**
 * 功能简述：注解数据源配置类
 *
 * @author qiancy
 * @create 2020/11/29
 * @since 1.0.0
 */

@Configuration
public class DynamicDatasourceConfig {

    @Bean(DataSourceConstants.DS_KEY_MASTER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(DataSourceConstants.DS_KEY_SLAVE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }


}
