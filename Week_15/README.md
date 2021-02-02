## 十五周作业



**3.分别用100字以上的一段话，加上一幅图，总结自己对下列技术的关键点思考和经验认识：**

1. JVM
2. NIO
3. 并发编程 *
4. spring和orm等框架
5. Mysql数据库和sql
6. 分库分表 *
7. RPC和微服务
8. 分布式缓存
9. 分布式消息队列 *



1.并发编程模块：学习到了多线程相关的状态，控制状态的方法，保证并发安全的锁，常用的多线程类及其是否能聚合线程执行的结果，java8以后可操作多线程的拉姆达表达式。了解了线程池的管理及其常用的线程池工具类和常用的线程安全容器。最后深入了解AQS相关

并发操作类的核心抽象类。

![并发编程脑图](https://github.com/brickGodMan/JAVA-000/blob/main/Week_04/src/img/concurrentSummarize.png)

2.数据库模块：了解了数据迁移的发展历程以及其优缺点，mysql数据库读写分离，分库分表提升性能。利用中间件sharding-sphere-proxy实现分库分表，减少对业务代码入侵。了解了分布式事务XA规范，mysql历史版本对xa的支持，以及柔性分布式事务TCC，saga。以及其相关柔性事务框架。

![](https://github.com/brickGodMan/JAVA-000/blob/main/Week_08/SubDataBase/src/main/resources/img/db.png)

3.消息中间件模块：认识了系统间的通信方式，然后演化到标准的MQ，以及MQ相比传统通信方式的优势。分别了解了三代MQ的代表，以及其核心功能与设计思想。着重了解了kafkaMQ。

![](https://github.com/brickGodMan/JAVA-000/blob/main/Week_15/MQ.png)
