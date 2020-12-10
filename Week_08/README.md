### 第八周作业
##### 第十五节课作业
##### 1.对前面设计的订单表，拆分2个库，每个库16张表。并在新结构演示常见的增删改查操作。

这里使用了sharding-shpere proxy 中间件 所以配置普通数据源即可。我直接使用了JdbcTemplate来连接sharding proxy。

直接写死实现了简单的增删改查功能：
```java
@RestController
public class OrderController {
    @Autowired
    private IOrderService orderService;
    /**
     * 查询全部
     */
    @GetMapping("/list-order")
    @ResponseBody
    public Map<String, List<Order>> listOrder() {
        int initSize = 8;
        Map<String, List<Order>> result = new HashMap<>(initSize);
        List<Order> masterOrder = orderService.getOrders();
        result.put("orders", masterOrder);
        //返回数据
        return result;
    }
    /**
     * 创建订单
     */
    @GetMapping("/create-order")
    @ResponseBody
    public String createOrder() {
        int initSize = 8;
        Map<String, List<Order>> result = new HashMap<>(initSize);
        Order order = new Order();
        order.setId("2");
        order.setUserId("2");
        order.setAmount("100");
        order.setStatus("1");
        order.setCreateTime(Date.valueOf(LocalDateTime.now().toLocalDate()));
        orderService.createOrder(order);
        //返回数据
        return "success";
    }

    /**
     * 修改 id 为2的订单状态为u
     */
    @GetMapping("/update-order")
    @ResponseBody
    public String updateOrder() {
        orderService.updateOrder(2);
        //返回数据
        return "success";
    }

    /**
     * 删除 id 为2的订单
     */
    @GetMapping("/delete-order")
    @ResponseBody
    public String deleteOrder() {
        orderService.deleteOrderBy(2);
        //返回数据
        return "success";
    }
}

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> getOrders() {
        String sql = "select * from order_t ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(
                Order.class));
    }

    @Override
    public void createOrder(Order order) {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into order_t(id, user_id, amount, status, create_time)");
        sb.append("values(");
        sb.append("" + order.getId()).append(", ");
        sb.append("'" + order.getId()).append("', ");
        sb.append("'" + order.getAmount()).append("', ");
        sb.append("'" + order.getStatus()).append("',");
        sb.append("'" + order.getCreateTime()).append("'");
        sb.append(")");
        jdbcTemplate.update(sb.toString());
    }

    @Override
    public void updateOrder(int id) {
        StringBuffer sb = new StringBuffer();
        sb.append("update order_t set status = 'u' where id = " + id);
        jdbcTemplate.update(sb.toString());
    }

    @Override
    public void deleteOrderBy(int id) {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from order_t where id = " + id);
        jdbcTemplate.update(sb.toString());
    }
}
```

以下是配置文件

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

sharding-sphere proxy 关键配置 config-sharding.ymal
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
控制台日志
2020-12-09 23:30:27.001 DEBUG 2020 --- [nio-8080-exec-1] o.s.jdbc.core.JdbcTemplate               : Executing SQL update [insert into order_t(id, user_id, amount, status, create_time)values('2','2','100','1','2020-12-09')]
2020-12-09 23:30:27.007  INFO 2020 --- [nio-8080-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-12-09 23:30:27.719  INFO 2020 --- [nio-8080-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2020-12-09 23:30:28.523 ERROR 2020 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[.[dispatcherServlet]      : Servlet.service() for servlet [dispatcherServlet] in context with path [/sharding-data] threw exception [Request processing failed; nested exception is org.springframework.jdbc.UncategorizedSQLException: StatementCallback; uncategorized SQLException for SQL [insert into order_t(id, user_id, amount, status, create_time)values('2','2','100','1','2020-12-09')]; SQL state [C1000]; error code [10002]; 2Unknown exception: [No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
Possible solutions: drop(int), find(), any(), any(groovy.lang.Closure), is(java.lang.Object), take(int)]; nested exception is java.sql.SQLException: 2Unknown exception: [No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
Possible solutions: drop(int), find(), any(), any(groovy.lang.Closure), is(java.lang.Object), take(int)]] with root cause

java.sql.SQLException: 2Unknown exception: [No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
Possible solutions: drop(int), find(), any(), any(groovy.lang.Closure), is(java.lang.Object), take(int)]
sharding proxy 日志：
[ERROR] 06:04:27.059 [ShardingSphere-Command-10] o.a.s.p.f.c.CommandExecutorTask - Exception occur: 
groovy.lang.MissingMethodException: No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
Possible solutions: drop(int), any(), find(), is(java.lang.Object), find(java.lang.CharSequence), use([Ljava.lang.Object;)
	at org.codehaus.groovy.runtime.ScriptBytecodeAdapter.unwrap(ScriptBytecodeAdapter.java:71)
	at org.codehaus.groovy.runtime.callsite.PojoMetaClassSite.call(PojoMetaClassSite.java:48)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:128)
	at Script2$_run_closure1.doCall(Script2.groovy:1)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:98)
	at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:325)
	at org.codehaus.groovy.runtime.metaclass.ClosureMetaClass.invokeMethod(ClosureMetaClass.java:264)
	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1034)
	at groovy.lang.Closure.call(Closure.java:420)
	at groovy.lang.Closure.call(Closure.java:414)
	at org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm.doSharding(InlineShardingAlgorithm.java:69)
	at org.apache.shardingsphere.sharding.route.strategy.type.standard.StandardShardingStrategy.doSharding(StandardShardingStrategy.java:67)
	at org.apache.shardingsphere.sharding.route.strategy.type.standard.StandardShardingStrategy.doSharding(StandardShardingStrategy.java:56)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.routeDataSources(ShardingStandardRoutingEngine.java:203)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.route0(ShardingStandardRoutingEngine.java:191)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.routeByShardingConditionsWithCondition(ShardingStandardRoutingEngine.java:114)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.routeByShardingConditions(ShardingStandardRoutingEngine.java:107)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.getDataNodes(ShardingStandardRoutingEngine.java:84)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.route(ShardingStandardRoutingEngine.java:69)
	at org.apache.shardingsphere.sharding.route.engine.ShardingSQLRouter.createRouteContext(ShardingSQLRouter.java:70)
	at org.apache.shardingsphere.sharding.route.engine.ShardingSQLRouter.createRouteContext(ShardingSQLRouter.java:55)
	at org.apache.shardingsphere.infra.route.engine.impl.PartialSQLRouteExecutor.route(PartialSQLRouteExecutor.java:59)
	at org.apache.shardingsphere.infra.route.engine.SQLRouteEngine.route(SQLRouteEngine.java:57)
	at org.apache.shardingsphere.infra.context.kernel.KernelProcessor.generateExecutionContext(KernelProcessor.java:52)
	at org.apache.shardingsphere.proxy.backend.communication.jdbc.JDBCDatabaseCommunicationEngine.execute(JDBCDatabaseCommunicationEngine.java:77)
	at org.apache.shardingsphere.proxy.backend.text.query.QueryBackendHandler.execute(QueryBackendHandler.java:61)
	at org.apache.shardingsphere.proxy.frontend.mysql.command.query.text.query.MySQLComQueryPacketExecutor.execute(MySQLComQueryPacketExecutor.java:62)
	at org.apache.shardingsphere.proxy.frontend.command.CommandExecutorTask.executeCommand(CommandExecutorTask.java:100)
	at org.apache.shardingsphere.proxy.frontend.command.CommandExecutorTask.run(CommandExecutorTask.java:76)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
```

问题解决了记录一下：
```yaml
关键日志:
[ERROR] 06:04:27.059 [ShardingSphere-Command-10] o.a.s.p.f.c.CommandExecutorTask - Exception occur: 
groovy.lang.MissingMethodException: No signature of method: java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]
下面这段配置里面id必须是int 才能 % 16。所以使用SS调用insert数据的时候必须使用int类型id，如果id是字符串类型可使用id.hashCode() % 16 解决
 shardingAlgorithms:
   database_inline:
     type: INLINE
     props:
       algorithm-expression: db_${id % 2}
   order_t_inline:
     type: INLINE
     props:
       algorithm-expression: order_t_${id % 16} # （id 为String类型配置） order_t_${id.hashCode() % 16}
       allow-range-query-with-inline-sharding: true

```


最近也学着优秀学员整理思维脑图，感觉基础太差，努力消化新知识力不从心，整理的时候又把老师的视频重新看了一遍，边看边整理。

![](https://github.com/brickGodMan/JAVA-000/blob/main/Week_08/SubDataBase/src/main/resources/img/db.png)