# 部署
## 1.EMQ

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

## 2.Apollo

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

