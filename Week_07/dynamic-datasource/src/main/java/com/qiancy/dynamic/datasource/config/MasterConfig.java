package com.qiancy.dynamic.datasource.config;

import com.qiancy.dynamic.datasource.constants.DataSourceConstants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/12/1
 * @since 1.0.0
 */
@Configuration
@MapperScan(basePackages = {"com.qiancy.dynamic.datasource.mapper.master"}, sqlSessionFactoryRef = "sqlSessionFactoryMaster")
public class MasterConfig {

    @Autowired
    @Qualifier(DataSourceConstants.DS_KEY_MASTER)
    private DataSource dataSourceMaster;


    @Bean
    public SqlSessionFactory sqlSessionFactoryMaster() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceMaster);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/master/*.xml"));
        return factoryBean.getObject();
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplateMaster() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryMaster());
    }

}
