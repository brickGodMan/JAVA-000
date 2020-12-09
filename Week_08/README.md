### 第八周作业
##### 第十五节课作业
##### 1.对前面设计的订单表，拆分2个库，每个库16张表。并在新结构演示常见的增删改查操作。

这里使用了sharding-shpere proxy 中间件 所以配置普通数据源即可。以下是配置文件

[application.properties](https://github.com/brickGodMan/JAVA-000/tree/main/Week_08/SubDataBase/src/main/resources/application.properties)
```properties
server.port=8080
server.servlet.context-path=/sharding-data
logging.level.org.springframework.jdbc.core.JdbcTemplate=DEBUG
#
## database
#db.conn.str = autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useLocalSessionState=true&tinyInt1isBit=false
## 使用sharding proxy 库 sharding_db(ss 自动生成的)
spring.datasource.url=jdbc:mysql://192.168.247.128:3307/dbproxy??serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

sharding-sphere proxy 关键配置

config-sharding.ymal 配置
```yaml
schemaName: dbproxy

dataSources:
 db_0:
    url: jdbc:mysql://192.168.247.128:3306/db_0?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true
    username: root
    password: 123456
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50 # 最大连接数
    minPoolSize: 1  # 最小连接数 
 db_1:
    url: jdbc:mysql://192.168.247.129:3306/db_1?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true
    username: root
    password: 123456
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50 # 最大连接数
    minPoolSize: 1  # 最小连接数 

rules:
- !SHARDING
 tables:
   order_t:
     actualDataNodes: db_${0..1}.order_t_${0..15}
     tableStrategy:
       standard:
         shardingColumn: id
         shardingAlgorithmName: order_t_inline
     keyGenerateStrategy:
       column: id
       keyGeneratorName: snowflake
 defaultDatabaseStrategy:
   standard:
     shardingColumn: id
     shardingAlgorithmName: database_inline
 defaultTableStrategy:
   none:

 shardingAlgorithms:
   database_inline:
     type: INLINE
     props:
       algorithm-expression: db_${id % 2}
   order_t_inline:
     type: INLINE
     props:
       algorithm-expression: order_t_${id % 16}
       allow-range-query-with-inline-sharding: true
```

server.yaml 配置
```yaml
authentication:
 users:
   root:
     password: 123456
#    sharding:
#      password: sharding 
#      authorizedSchemas: sharding_db

props:
  max-connections-size-per-query: 1
  acceptor-size: 16  # The default value is available processors count * 2.
  executor-size: 16  # Infinite by default.
  proxy-frontend-flush-threshold: 128  # The default value is 128.
    # LOCAL: Proxy will run with LOCAL transaction.
    # XA: Proxy will run with XA transaction.
    # BASE: Proxy will run with B.A.S.E transaction.
  proxy-transaction-type: LOCAL
  proxy-opentracing-enabled: false
  proxy-hint-enabled: false
  query-with-cipher-column: true
  sql-show: true
  check-table-metadata-enabled: false

```

#### 总结：
该作业只完成了一半，能实现查询，插入操作一直在报错。后面集成hmily 实现分布式事务demo作业只能后面在补了。
以下是报错信息，在努力解决中……
```yaml
2020-12-09 23:30:27.001 DEBUG 2020 --- [nio-8080-exec-1] o.s.jdbc.core.JdbcTemplate               : Executing SQL update [insert into order_t(id, user_id, amount, status, create_time)values('2','2','100','1','2020-12-09')]
2020-12-09 23:30:27.007  INFO 2020 --- [nio-8080-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-12-09 23:30:27.719  INFO 2020 --- [nio-8080-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2020-12-09 23:30:28.523 ERROR 2020 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[.[dispatcherServlet]      : Servlet.service() for servlet [dispatcherServlet] in context with path [/sharding-data] threw exception [Request processing failed; nested exception is org.springframework.jdbc.UncategorizedSQLException: StatementCallback; uncategorized SQLException for SQL [insert into order_t(id, user_id, amount, status, create_time)values('2','2','100','1','2020-12-09')]; SQL state [C1000]; error code [10002]; 2Unknown exception: [No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
Possible solutions: drop(int), find(), any(), any(groovy.lang.Closure), is(java.lang.Object), take(int)]; nested exception is java.sql.SQLException: 2Unknown exception: [No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
Possible solutions: drop(int), find(), any(), any(groovy.lang.Closure), is(java.lang.Object), take(int)]] with root cause

java.sql.SQLException: 2Unknown exception: [No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
Possible solutions: drop(int), find(), any(), any(groovy.lang.Closure), is(java.lang.Object), take(int)]
```

最近也学着优秀学员整理思维脑图，感觉基础太差，努力消化新知识力不从心，整理的时候又把老师的视频重新看了一遍，边看边整理。

![](https://github.com/brickGodMan/JAVA-000/blob/main/Week_08/SubDataBase/src/main/resources/img/db.png)