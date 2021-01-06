# 占位（作业未完成）
## 1.（选做）命令行下练习操作 Redis 的各种基本数据结构和命令。

## 2.（选做）分别基于 jedis，RedisTemplate，Lettuce，Redission 实现 redis 基本操作的 demo，可以使用 spring-boot 集成上述工具。

## 3.（选做）spring 集成练习:

    实现 update 方法，配合 @CachePut
    实现 delete 方法，配合 @CacheEvict
    将示例中的 spring 集成 Lettuce 改成 jedis 或 redisson

## 4.（必做）基于 Redis 封装分布式数据操作：

    在 Java 中实现一个简单的分布式锁；
    在 Java 中实现一个分布式计数器，模拟减库存。

### 分布式锁的实现原理
    
解决不同进程之间的同步问题

同一进程内的线程访问外部同一资源时，可以同步访问方法。

不同进程内的线程访问同一资源是，先访问资源对应的锁，确认没有其他线程访问该资源。

SNTNX 

    SETNX key value
    将 key 的值设为 value ，当且仅当 key 不存在。
    若给定的 key 已经存在，则 SETNX 不做任何动作。
    SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
    返回值：
    设置成功，返回 1 。
    设置失败，返回 0 。

完成同步流程

    1. 使用SETNX命令获取锁，若返回0（key已存在，锁已存在）则获取失败，反之获取成功
    2. 为了防止获取锁后程序出现异常，导致其他线程/进程调用SETNX命令总是返回0而进入死锁状态，需要为该key设置一个“合理”的过期时间
    3. 释放锁，使用DEL命令将锁数据删除
    



5.（必做）基于 Redis 的 PubSub 实现订单异步处理

6.（挑战☆）基于其他各类场景，设计并在示例代码中实现简单 demo：

    实现分数排名或者排行榜；
    实现全局 ID 生成；
    基于 Bitmap 实现 id 去重；
    基于 HLL 实现点击量计数；
    以 redis 作为数据库，模拟使用 lua 脚本实现前面课程的外汇交易事务。

7.（挑战☆☆）升级改造项目：

    实现 guava cache 的 spring cache 适配；
    替换 jackson 序列化为 fastjson 或者 fst，kryo；
    对项目进行分析和性能调优。

8.（挑战☆☆☆）以 redis 作为基础实现上个模块的自定义 rpc 的注册中心。

