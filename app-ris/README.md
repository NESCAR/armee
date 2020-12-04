# application remote information system

远程信息管理系统，管理员可以直接接触的平台。通过该平台实现数据的获取以及命令下发。

## 1. 说明
### 1.1 服务器需要配置的host

```bash
sudo vim /etc/hosts
# 输入以下服务器host name和ip地址对应关系，比如
12.10.11.20 redis
```

redis：redis服务器地址

ctripcorp-apollo：Apollo配置中心

### 1.2 启动配置

```
# 本节点ID
-Dbroker.id=20 
# 生产目的
-Dproduce.to=kafka
# 生产目的url
-Dkafka.producer.server.url=kafka-1
# 生产目的端口
-Dkafka.producer.server.port=9092
# 消费源头
-Dconsume.from=kafka 
# 消费源头url
-Dkafka.consumer.server.url=Kafka-1 
# 消费源头端口
-Dkafka.consumer.server.port=9092
# 阿里云短信服务的标签，效果【ARMEE物流管理】
-Dsms.sign.name=ARMEE物流管理
# 阿里云短信模版编号
## 用于发送验证码
-Dsms.template.validate-code=SMS_204111562
## 用于发送提醒消息
-Dsms.template.alarm=SMS_206535176
# 阿里云accesskey id
-Daliyun.accesskey.id=XXXX
# 阿里云accesskey secret
-Daliyun.accesskey.secret=XXXX
```

