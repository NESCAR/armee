# 部署
## 1.EMQ（取消使用）

版本：4.0.7

### 1.1 安装

[安装流程](https://docs.emqx.io/broker/latest/cn/getting-started/install.html##shell)

### 1.2 启动

```bash
# 启动
## 直接启动
$ emqx start
EMQ X v4.0.7 is started successfully!
## systemctl 启动
$ sudo systemctl start emqx
EMQ X v4.0.7 is started successfully!
## service 启动
$ sudo service emqx start
EMQ X v4.0.7 is started successfully!
# 查看启动状态
$ emqx_ctl status
```

## 2.Apollo（取消使用）

**目前服务器部署的Apollo有问题，可以暂时使用协程的[demo](http://106.54.227.205/)。**

Apollo版本使用[1.6.1](https://github.com/ctripcorp/apollo/releases/tag/v1.6.1).

[Github仓库](https://github.com/ctripcorp/apollo/)

### 2.1 部署说明

Apollo目前支持以下环境：

* DEV
  开发环境
* FAT
  测试环境，相当于alpha环境(功能测试)
* UAT
  集成环境，相当于beta环境（回归测试）
* PRO
  生产环境

协程的部署策略为：

![分布式架构](./img/apollo-deployment.png)



### 2.2 安装

| 环境     | 版本    |
| -------- | ------- |
| JDK      | 1.8 +   |
| 操作系统 | CentOS7 |
| MySQL    | 5.6.5+  |

* **数据库初始化**

  （1）[ApolloPortalDB](https://github.com/ctripcorp/apollo/blob/master/scripts/sql/apolloportaldb.sql)

  ```bash
  mysql> source /PATH/TO/ApolloPortalDB/apolloportaldb.sql
  ```

  验证

  ```bash
  mysql> select `Id`, `Key`, `Value`, `Comment` from `ApolloPortalDB`.`ServerConfig` limit 1;
  ```

  ```
  +----+--------------------+-------+--------------------------+
  | Id | Key                | Value | Comment                  |
  +----+--------------------+-------+--------------------------+
  |  1 | apollo.portal.envs | dev   | 可支持的环境列表            |
  +----+--------------------+-------+--------------------------+
  1 row in set (0.00 sec)
  
  ```

  > 注：ApolloPortalDB只需要在生产环境部署一个即可

  （2）[apolloconfigdb.sql](https://github.com/ctripcorp/apollo/blob/master/scripts/sql/apolloconfigdb.sql)

  ```bash
  mysql> source /PATH/TO/ApolloPortalDB/apolloconfigdb.sql
  ```

  验证

  ```bash
  mysql> select `Id`, `Key`, `Value`, `Comment` from `ApolloConfigDB`.`ServerConfig` limit 1;
  ```

  ```
  +----+--------------------+-------------------------------+------------------------------------------------------+
  | Id | Key                | Value                         | Comment                                              |
  +----+--------------------+-------------------------------+------------------------------------------------------+
  |  1 | eureka.service.url | http://localhost:8080/eureka/ | Eureka服务Url，多个service以英文逗号分隔             |
  +----+--------------------+-------------------------------+------------------------------------------------------+
  1 row in set (0.00 sec)
  ```

  > 注：ApolloConfigDB需要在每个环境部署一套，如fat、uat和pro分别部署3套ApolloConfigDB

* **安装包依赖数据库配置**

  将每个安装包（portal、adminserver、configserver）的`config/application-github.properties`文件填写正确的DB信息。比如，

  ```properties
  spring.datasource.url = jdbc:mysql://localhost:3306/ApolloPortalDB?characterEncoding=utf8
  spring.datasource.username = root
  spring.datasource.password = Root123456@
  ```

* **apollo-portal的meta service配置**

  Apollo Portal需要在不同的环境访问不同的meta service(apollo-configservice)地址，所以我们需要在配置中提供这些信息。默认情况下，meta service和config service是部署在同一个JVM进程，所以meta service的地址就是config service的地址。

  `config/apollo-env.properties`文件内配置meta service。

### 2.3 启动

分别调用configerserver、adminserver、portal的`scripts/startup.sh`（需要根据服务器情况修改对应的JVM参数）。

* **JVM配置**

  

## 3.Kafka

版本：Scala 2.12-Kafka 2.3.0

需要安装JDK 1.8来启动。

### 3.1 安装

* **下载**

  下载Scala 2.12的Kafka 2.3.0版本。

  ```bash
  $ wget https://archive.apache.org/dist/kafka/2.3.0/kafka_2.12-2.3.0.tgz
  ```

* **解压**

  ```bash
  $ tar -zxvf kafka_2.12-2.3.0.tgz
  ```

* **移动到软件目录**

  比如`/root/app/kafka`

  ```bash
  $ mkdir /root/apps/kafka/
  $ mv kafka_2.12-2.3.0 /root/apps/kafka/
  ```

### 3.2 配置

在`./config/`内配置`zookeeper.properties`和`server.properties`，可以配置多个server。

* **如何配置公网访问Kakfa**

  `advertised.listeners`用于ZooKeeper向外部发布Broker的listner信息，只有发布的listener才会被外部使用。`advertised.listeners`默认和listeners相同，不需要配置。

  ```properties
  # server.properties文件
  listeners=PLAINTEXT://[Kafka-Host-Name]:9094
  # kafka服务器的/etc/hosts
  [内网IP] Kafka-Host-Name
  # kafka客户端的/etc/hosts
  [公网IP] Kafka-Host-Name
  ```

## 4.Java

版本：JDK 1.8

### 4.1 openjdk
```bash
$ yum search java | jdk
$ yum install java-1.8.0-openjdk
```

## 5.Redis

版本：3.2.12（yum安装）或者5.0.5（安装包安装）

### 5.1 安装

* **安装包安装**

  ```bash
  $ wget http://download.redis.io/releases/redis-5.0.5.tar.gz
  $ tar xzf redis-5.0.5.tar.gz
  $ cd redis-5.0.5
  $ yum install gcc
  # 官网直接make，需要jealloc
  # 此处make MALLOC=libc可以避免以上问题
  $ make MALLOC=libc
  ```

* **yum安装**

  yum只能按照早期的版本。

  ```bash
  $ yum install redis
  ```

### 5.2 启动

```bash
# 安装包启动方式
$ src/redis-server
# yum安装启动方式
$ systemclt start redis
```

## 6.MySQL

版本：5.7

### 6.1 安装

需要先卸载MariaDB，再安装MySQL。

```bash
$ sudo wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm
$ sudo yum -y install mysql57-community-release-el7-10.noarch.rpm
$ sudo yum -y install mysql-community-server
```

### 6.2 配置和启动

```bash
# 配置为远程链接
$ sudo echo "bind-address=0.0.0.0" >> /etc/my.cnf
# 启动
$ systemctl start mysqld
# 查看初始密码
$ grep 'temporary password' /var/log/mysqld.log | awk '{print $NF}'
# 登录
$ mysql -u root -p
# 更改密码
$ mysql> SET PASSWORD FOR 'root'@'localhost' = PASSWORD('[新密码]');
# 设置root用户可以用于外部访问
$ use mysql;
$ mysql > update user set host = '%' where user = 'root';
```

### 6.3 数据库初始化

运行初始化SQL文件。

## 7.InfluxDB
### 7.1 安装
安装链接：

https://portal.influxdata.com/downloads/

## 7.SMS短信服务
### 7.1 阿里云SMS开通

### 7.2 接口设置

## 所有配置信息汇总
### vm options

```
-Dding.notif.secret=xxxx
-Dding.notif.access_token=xxxx
```



