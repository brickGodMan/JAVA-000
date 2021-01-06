### 第十周作业

#### 第二十三课作业

##### 1.配置redis的主从复制，sentinel高可用，Cluster集群

配置主从，我使用2台虚拟机（192.168.247.128/192.168.247.129）分别启动6个redis节点，集群模式3主3从

关键配置项:
```properties
port 9001（每个节点的端口号）
daemonize yes
bind 192.168.119.131（绑定当前机器 IP）
dir /usr/local/redis-cluster/9001/data/（数据文件存放位置）
pidfile /var/run/redis_9001.pid（pid 9001和port要对应）
cluster-enabled yes（启动集群模式）
cluster-config-file nodes-9001.conf（9001和port要对应）
cluster-node-timeout 15000
appendonly yes
```
分别在128与129两台机器准备好9001到9006个节点如下：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_10/img/file.jpg)

每个文件夹内容一致：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_10/img/file-details.jpg)

注意每个文件下redis.config文件中的配置与端口对应即可。

当节点准备就绪后分别启动128与129两台机器的redis，启动命令如下：
```js
/usr/local/bin/redis-server /usr/local/redis-cluster/9001/redis.conf
/usr/local/bin/redis-server /usr/local/redis-cluster/9002/redis.conf
/usr/local/bin/redis-server /usr/local/redis-cluster/9003/redis.conf
/usr/local/bin/redis-server /usr/local/redis-cluster/9004/redis.conf
/usr/local/bin/redis-server /usr/local/redis-cluster/9005/redis.conf
/usr/local/bin/redis-server /usr/local/redis-cluster/9006/redis.conf
```
上述命令可以写在shell 脚本中方便下次启动。

当两台机器都启动完毕后执行以下命令./bin/redis-cli --cluster create 192.168.247.128:9001 192.168.247.128:9002 192.168.247.128:9003 192.168.247.129:9001 192.168.247.129:9002 192.168.247.129:9003 --cluster-replicas 1

两台机器总共12个节点，命令中的6个节点将被redis设置为master节点，剩下的将被制动分配为从节点，这样就达到了两台机器均等节点互为主从保证了redis数据高可用。cluster-replicas 1该参数保证主从数据复制比例1:1。效果如下图：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_10/img/create-cluster.jpg)

加入成功如下图：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_10/img/join-cluster-success.jpg)

最后可查看集群信息如下：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_10/img/cluster-info-nodes.jpg)

通过如下命令可以看到自己节点的的主从信息：
![](https://github.com/brickGodMan/JAVA-000/tree/main/Week_10/img/my-replication.jpg)