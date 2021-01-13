## 第二十五课作业
### 1、搭建一个3节点kafka集群，测试功能和特性；实现spring kafka 下对kafka集群的操作，将代码提交到github

#### [项目地址](https://github.com/brickGodMan/kafka-demo.git)
如要创建一个三节点集群，需要创建三个 kafka 目录副本，分别命名为9001、9002、9003。完成这一步之后，我们开始编写配置文件。以下是配置示例（本机内网ip：192.168.247.130）

可以通过zookeeper可视化工具查看如下图：
![](https://github.com/brickGodMan/kafka-demo/tree/main/img/kafka-broker.png)

```properties
############################# Server Basics #############################
# The id of the broker. This must be set to a unique integer for each broker.
broker.id=2 (其他两个节点分别 为1,3)

############################# Socket Server Settings #############################
#port=9192
#host.name=localhost
listeners=PLAINTEXT://192.168.247.130:9002 (关键配置 此处最好设置成ip方便程序连接，其他2个节点端口分别为9001,9003)


# The number of threads that the server uses for receiving requests from the network and sending responses to the network
num.network.threads=3

# The number of threads that the server uses for processing requests, which may include disk I/O
num.io.threads=8

# The send buffer (SO_SNDBUF) used by the socket server
socket.send.buffer.bytes=102400

# The receive buffer (SO_RCVBUF) used by the socket server
socket.receive.buffer.bytes=102400

# The maximum size of a request that the socket server will accept (protection against OOM)
socket.request.max.bytes=104857600


############################# Log Basics #############################

# A comma separated list of directories under which to store log files
log.dirs=/usr/local/kafka2/log/kafka-logs #关键配置其他两个节点分别配置/usr/local/kafka/log/kafka-logs、/usr/local/kafka3/log/kafka-logs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
num.partitions=2

# The number of threads per data directory to be used for log recovery at startup and flushing at shutdown.
# This value is recommended to be increased for installations with data dirs located in RAID array.
num.recovery.threads.per.data.dir=1

############################# Internal Topic Settings  #############################
# The replication factor for the group metadata internal topics "__consumer_offsets" and "__transaction_state"
# For anything other than development testing, a value greater than 1 is recommended for to ensure availability such as 3.
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1


############################# Log Retention Policy #############################

# The minimum age of a log file to be eligible for deletion due to age
log.retention.hours=168


# The maximum size of a log segment file. When this size is reached a new log segment will be created.
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
log.retention.check.interval.ms=300000

############################# Zookeeper #############################

zookeeper.connect=localhost:2181

# Timeout in ms for connecting to zookeeper
zookeeper.connection.timeout.ms=100000

group.initial.rebalance.delay.ms=0
broker.list=localhost:9001,localhost:9002,localhost:9003

```
后台方式分别启动kafka3节点
```jshelllanguage
nohup /usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties &
nohup /usr/local/kafka2/bin/kafka-server-start.sh /usr/local/kafka2/config/server.properties &
nohup /usr/local/kafka3/bin/kafka-server-start.sh /usr/local/kafka3/config/server.properties &
```
使用脚本压测生产消息性能：
```jshelllanguage
./kafka-producer-perf-test.sh --topic qiancytest --num-records 1000000 --record-size 1000 --throughput 20000 --producer-props bootstrap.servers=192.168.247.130:9001
```
压测结果：
```log
68081 records sent, 13613.5 records/sec (12.98 MB/sec), 1474.3 ms avg latency, 1905.0 max latency.
107472 records sent, 21490.1 records/sec (20.49 MB/sec), 1288.5 ms avg latency, 1968.0 max latency.
81872 records sent, 16374.4 records/sec (15.62 MB/sec), 1505.5 ms avg latency, 2945.0 max latency.
104753 records sent, 20950.6 records/sec (19.98 MB/sec), 1726.8 ms avg latency, 2913.0 max latency.
110529 records sent, 22105.8 records/sec (21.08 MB/sec), 1446.9 ms avg latency, 2353.0 max latency.
103201 records sent, 20619.6 records/sec (19.66 MB/sec), 1229.9 ms avg latency, 2017.0 max latency.
105149 records sent, 21025.6 records/sec (20.05 MB/sec), 1018.5 ms avg latency, 1714.0 max latency.
118885 records sent, 23767.5 records/sec (22.67 MB/sec), 390.7 ms avg latency, 1769.0 max latency.
96582 records sent, 18989.8 records/sec (18.11 MB/sec), 12.4 ms avg latency, 252.0 max latency.
1000000 records sent, 19989.605405 records/sec (19.06 MB/sec), 985.77 ms avg latency, 2945.00 ms max latency, 924 ms 50th, 2679 ms 95th, 2829 ms 99th, 2897 ms 99.9th.
```
使用脚本压测消费消息性能：
```jshelllanguage
用三个线程消费100W消息
./kafka-consumer-perf-test.sh --broker-list 192.168.247.130:9001,192.168.247.130:9002,192.168.247.130:9003 --topic qiancytest --fetch-size 1048576 --messages 1000000 --threads 3
```

压测结果：
```log
start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec, rebalance.time.ms, fetch.time.ms, fetch.MB.sec, fetch.nMsg.sec
2021-01-13 22:21:09:545, 2021-01-13 22:21:18:655, 953.6744, 104.6843, 1000005, 109770.0329, 84, 9026, 105.6586, 110791.6020
```

在spring boot 中使用spring-kafka 操作kafka集群：

[application.properties](https://github.com/brickGodMan/kafka-demo/tree/main/src/main/resources/application.properties)
```properties
#kafka producer
kafka.producer.bootstrapServers=192.168.247.130:9001,192.168.247.130:9002,192.168.247.130:9003
#kafka consumer
kafka.consumer.bootstrapServers=192.168.247.130:9001,192.168.247.130:9002,192.168.247.130:9003
kafka.consumer.groupId=myGroup
```

kafka配置：
```java
package com.qiancy.kafka.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2021/1/11
 * @since 1.0.0
 */
@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${kafka.producer.bootstrapServers}")
    private String producerBootstrapServers;

    @Value("${kafka.consumer.bootstrapServers}")
    private String consumerBootstrapServers;

    @Value("${kafka.consumer.groupId}")
    private String consumerGroupId;

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(properties());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public Map<String,Object> properties() {
        Map<String, Object> prop = new HashMap<>(16);
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return prop;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(3);
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```

在service中利用@Autowired 使用kafka客户端

producer:
```java
package com.qiancy.kafka.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2021/1/13
 * @since 1.0.0
 */
@Service
public class TestService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void test() {
        System.out.println("------start-------");
        kafkaTemplate.send("qiancytest","hello kafka spring");
        System.out.println("------end-------");
    }
}
```
consumer:
```java
package com.qiancy.kafka.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2021/1/13
 * @since 1.0.0
 */
@Component
public class Consumer {
    @KafkaListener(topics = "qiancytest",groupId = "myGroup")
    public void listener(ConsumerRecord<String,String> record) {
        String value = record.value();
        System.out.println(String.format("收到消息：%s",value));
    }
}
```

## 参考链接
[Spring Boot 集成kafka](https://juejin.cn/post/6844903969265975309)

[Spring 集成kafka](https://www.cnblogs.com/caoweixiong/p/12987997.html)


