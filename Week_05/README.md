#### 作业

##### 作业二：写代码实现bean的装配
[第一种xml方式](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/BeanAssembly/src/main/java/com/qiancy/spring/xml/TestAssembly.java)

[第二种基于java类配置](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/BeanAssembly/src/main/java/com/qiancy/spring/config/TestAnno.java)

[第三种自动注解](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/BeanAssembly/src/main/java/com/qiancy/spring/auto/TestAuto.java)

##### 作业三：根据前面课程提供的Student/Klass/School实现自动配置和Starer

基于老师演示的spring项目，主要在resource下增加META-INF文件夹，在增加以下3个配置文件

[additional-spring-configuration-metadata.json](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/self-starter/src/main/resources/META-INF/additional-spring-configuration-metadata.json)
```json5
{
  "groups": [
    {
      "name": "self",
      "type": "java.lang.String"
    }
  ],
  "properties": [
    {
      "name": "self.properties",
      "type": "java.lang.String"
    },
    {
      "name": "self.name",
      "type": "java.lang.String",
      "description": "test name"
    }
  ]
}
```

[spring.factories](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/self-starter/src/main/resources/META-INF/spring.factories)
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.qiancy.config.SpringBootConfiguration
```

[spring.provides](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/self-starter/src/main/resources/META-INF/spring.provides)
```properties
provides: self-spring-boot-starter
```
最后打包成一个jar 供下面springboot项目使用。功能是获取一段字符串。

##### 作业6：研究一下JDBC接口和数据库连接池，掌握其设计与用法

[springboot](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/springboot)

引入下面的starter依赖,启动springboot项目
```xml
<dependency>
    <groupId>com.qiancy</groupId>
    <artifactId>self-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
测试自定义starter功能
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/springboot/src/img/integration_starter_run_result.png)

自定义jdbc

jdbc增加/批量增加
```java
//批量增加
public void addBatchUser() {
    String sql = "insert into t_user(user_id,user_name,user_age) values(?,?,?)";
    try {
        connection = DbUtilOne.openConnection();
        connection.setAutoCommit(false);
        pre = connection.prepareStatement(sql);
        int count = 9;
        for (int i = 0; i < count; i++) {
            pre.setInt(1, i);
            pre.setString(2, "jdbc插入" + i);
            pre.setString(3, "age: " + i);
            pre.addBatch();
        }
        if(pre.executeBatch().length < 9) {
            System.out.println("有数据插入失败");
        } else {
            System.out.println("批量插入成功");
        }
        connection.commit();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            pre.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//添加
public void addUser() {
    String sql = "insert into t_user(user_id,user_name,user_age) values(?,?,?)";
    try {
        connection = DbUtilOne.openConnection();
        pre = connection.prepareStatement(sql);
        int id = new Random().nextInt(10);
        pre.setInt(1, id);
        pre.setString(2, "jdbc插入" + id);
        pre.setString(3, "" + id);
        if (pre.executeUpdate() > 0) {
            System.out.println("数据插入成功");
        } else {
            System.out.println("数据插入失败");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            pre.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```
运行效果图：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/springboot/src/img/dbc_insert_and_query_result.png)

![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/springboot/src/img/jdbc_batch_and_query_result.png)

查询：
```java
//查询
public void getUser() {
    String sql = "select * from t_user";
    try {
        connection = DbUtilOne.openConnection();
        pre = connection.prepareStatement(sql);
        rs = pre.executeQuery();
        while (rs.next()) {
            System.out.printf("userId:%s,userName:%s,userAge:%s%n",rs.getInt("user_id"),rs.getString("user_name"),rs.getString("user_age"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        close(connection,pre,rs);
    }
}
```
jdbc更新：
```java
public void updataUser() {
    String sql = "update t_user set user_name = ? where user_id = ?";
    try {
        connection = DbUtilOne.openConnection();
        pre = connection.prepareStatement(sql);
        pre.setString(1,"jdbc插入8_update");
        pre.setInt(2,8);
        if(pre.executeUpdate() > 0){
            System.out.println("数据修改成功");
        } else {
            System.out.println("数据修改失败");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            pre.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
```
运行效果图:
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/springboot/src/img/jdbc_update_and_query_result.png)

jdbc删除：
```java
public void deleteUser() {
    String sql = "delete from t_user where user_id = ?";
    try {
        connection = DbUtilOne.openConnection();
        pre = connection.prepareStatement(sql);
        pre.setInt(1,8);
        if(pre.executeUpdate() > 0){
            System.out.println("数据删除成功");
        } else {
            System.out.println("数据删除失败");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            pre.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
```
运行效果图：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/springboot/src/img/jdbc_delete_and_query_result.png)

springboot 集成hikari，增加如下配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/orange?serverTimezone=UTC
    hikari:
      username: root
      driver-class-name: com.mysql.cj.jdbc.Driver
      minimum-idle: 5
      #空闲连接存活最大时间 2分钟（默认是10分钟）
      idle-timeout: 120000
      #连接池最大连接数
      maximum-pool-size: 10
      auto-commit: true
      pool-name: FirstHikariCP
      #控制连接池中连接最长生命周期这里设置了20分钟
      max-lifetime: 120000
      #连接数据库超时时间
      connection-timeout: 30000
      connection-test-query: select 1
```
hikari 配置类：
```java
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
```
hikariService:
```java
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
```
测试hikari效果图：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_05/springboot/src/img/jhikari_result.png)