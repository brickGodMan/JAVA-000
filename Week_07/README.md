
### 第七周作业

---
#### 第14节课作业

---
2、（必做）读写分离-动态切换数据库版本1.0

###### 自定义实现有三种方式
1. 多套数据源策略
2. 动态数据源
3. 参数化变更数据源

我采用的是多套源策略（[代码目录](https://github.com/brickGodMan/JAVA-000/tree/main/Week_07/dynamic-datasource)）

[application.properties](https://github.com/brickGodMan/JAVA-000/blob/main/Week_07/dynamic-datasource/src/main/resources/config/application.properties) 配置如下：
```yaml
server.port=8080
server.servlet.context-path=/datasource

logging.level.root=INFO
logging.level.com.qiancy.dynamic.datasource.mapper=debug

# mybatis-plus
mybatis-plus.type-aliases-package=com.qiancy.dynamic.datasource.entity
# 默认位置，可不配置
# mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml
# 使用数据库自增ID
mybatis-plus.global-config.db-config.id-type=auto


# database
db.conn.str = useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useLocalSessionState=true&tinyInt1isBit=false

spring.datasource.master.jdbc-url=jdbc:mysql://192.168.247.128:3306/db?${db.conn.str}
spring.datasource.master.username=root
spring.datasource.master.password=123456
spring.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.slave.jdbc-url=jdbc:mysql://192.168.247.129:3306/db?${db.conn.str}
spring.datasource.slave.username=root
spring.datasource.slave.password=123456
spring.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver
```
关键配置：
[DynamicDatasourceConfig](https://github.com/brickGodMan/JAVA-000/blob/main/Week_07/dynamic-datasource/src/main/java/com/qiancy/dynamic/datasource/config/DynamicDatasourceConfig.java)、[MasterConfig](https://github.com/brickGodMan/JAVA-000/blob/main/Week_07/dynamic-datasource/src/main/java/com/qiancy/dynamic/datasource/config/MasterConfig.java)、[SlaveConfig](https://github.com/brickGodMan/JAVA-000/blob/main/Week_07/dynamic-datasource/src/main/java/com/qiancy/dynamic/datasource/config/SlaveConfig.java)
```java
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

@Configuration
@MapperScan(basePackages = {"com.qiancy.dynamic.datasource.mapper.slave"}, sqlSessionFactoryRef = "sqlSessionFactorySlave")
public class SlaveConfig {
    @Autowired
    @Qualifier(DataSourceConstants.DS_KEY_SLAVE)
    private DataSource dataSourceSlave;


    @Bean
    public SqlSessionFactory sqlSessionFactorySlave() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceSlave);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/slave/*.xml"));
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateSlave() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactorySlave());
    }

}
```
mapper目录下分别建立master与slave文件夹分别对应不同数据源

3、（必做）读写分离-数据库框架版本版本2.0

###### 利用sharding-sphere与JdbcTemplate实现读写分离切换数据源 （[代码目录]()）

[application.properties](https://github.com/brickGodMan/JAVA-000/blob/main/Week_07/sharding-sphere/src/main/resources/application.properties) 配置如下：
```yaml
server.port=8080
server.servlet.context-path=/datasource

# 配置真实数据源
sharding.jdbc.datasource.names=master,slave
db.conn.str=useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true
#主数据库
sharding.jdbc.datasource.master.type=com.zaxxer.hikari.HikariDataSource
sharding.jdbc.datasource.master.hikari.driver-class-name=com.mysql.cj.jdbc.Driver
sharding.jdbc.datasource.master.jdbc-url=jdbc:mysql://192.168.247.128:3306/db?${db.conn.str}

sharding.jdbc.datasource.master.username=root
sharding.jdbc.datasource.master.password=123456

# 从数据库
sharding.jdbc.datasource.slave.type=com.zaxxer.hikari.HikariDataSource
sharding.jdbc.datasource.slave.hikari.driver-class-name=com.mysql.cj.jdbc.Driver
sharding.jdbc.datasource.slave.jdbc-url=jdbc:mysql://192.168.247.129:3306/db?${db.conn.str}


sharding.jdbc.datasource.slave.username=root
sharding.jdbc.datasource.slave.password=123456

# 配置读写分离
# 配置从库选择策略，提供轮询与随机，这里选择用轮询
sharding.jdbc.config.masterslave.load-balance-algorithm-type=round_robin
sharding.jdbc.config.masterslave.name=ms
sharding.jdbc.config.masterslave.master-data-source-name=master
sharding.jdbc.config.masterslave.slave-data-source-names=slave

# 开启SQL显示，默认值: false，注意：仅配置读写分离时不会打印日志
sharding.jdbc.config.props.sql.show=true
spring.main.allow-bean-definition-overriding=true
```
运行日志如下图，查询使用slave库，插入使用master库

![运行结果](https://github.com/brickGodMan/JAVA-000/blob/main/Week_07/db/sharding-img.png)
