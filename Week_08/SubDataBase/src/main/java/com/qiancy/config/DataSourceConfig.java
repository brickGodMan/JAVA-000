//package com.qiancy.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
///**
// * 功能简述：数据源配置类
// *
// * @author qiancy
// * @create 2020/12/9
// * @since 1.0.0
// */
//@Configuration
//@MapperScan(basePackages = {"com.qiancy.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
//public class DataSourceConfig {
//
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(masterDataSource());
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        return factoryBean.getObject();
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory());
//    }
//}
