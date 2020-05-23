# 部署
## 1.EMQ

### 1.1 安装

[安装流程](https://docs.emqx.io/broker/latest/cn/getting-started/install.html##shell)

### 1.2 启动

```bash
# 启动
## 直接启动
$ emqx start
EMQ X v4.0.0 is started successfully!
## systemctl 启动
$ sudo systemctl start emqx
EMQ X v4.0.0 is started successfully!
## service 启动
$ sudo service emqx start
EMQ X v4.0.0 is started successfully!
# 查看启动状态
$ emqx_ctl status
```

## 2.Apollo

## 3.Kafka

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

  一般情况下将应用和Kafka部署在局域网内，`advertised.listeners`不需要配置（注释掉）。

  ```properties
  # server.properties文件
  listeners=PLAINTEXT://[Kafka-Host-Name]:9094
  # kafka服务器的/etc/hosts
  [内网IP] Kafka-Host-Name
  # kafka客户端的/etc/hosts
  [公网IP] Kafka-Host-Name
  ```

## 4.Java
### 4.1 openjdk
```bash
$ yum search java | jdk
$ yum install java-1.8.0-openjdk
```