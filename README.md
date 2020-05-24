# ARMEE
ARMEE军团，NESC课题组技术全台。

```
 ______     ______     __    __     ______     ______    
/\  __ \   /\  == \   /\ "-./  \   /\  ___\   /\  ___\   
\ \  __ \  \ \  __<   \ \ \-./\ \  \ \  __\   \ \  __\   
 \ \_\ \_\  \ \_\ \_\  \ \_\ \ \_\  \ \_____\  \ \_____\ 
  \/_/\/_/   \/_/ /_/   \/_/  \/_/   \/_____/   \/_____/ 
                                                         
```

## 1.组织架构

总体架构：

```
                       +-----------+           
                       |  Enginee  | 
                       +-----------+
                              |
+---------+                   |   
|  Armee  | ------------.     |                  
+---------+             |     | 
                        |     |
+----------+       +---------------+
|  Apollo  | ----- |  Application  |   
+----------+       +---------------+
                           |
         .------------------------------.
         |               |              |
         |          +---------+    +-------------------+
    +----------+    |  Kafka  |    |  Correspondentee  |
    |   EMQX   |    +---------+    +-------------------+
    +----------+         |      
                    +----------+
                    |  Sentry  |
                    +----------+
                  
-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.


+-----------+ 
|   Spyee   |                 
+-----------+  
```

## 1.1 Enginee
负责基础任务“工兵”，可供所有上层应用复用。

## 1.2 Armyee
军团，Armee项目核心（技术中台），包括可复用的各种模块，供Application或者其他上层应用调用。
### 1.2.1 Apollo
分布式配置中心。

## 1.3 Applicationee
面向不同需求的应用。

## 1.4 Correspondentee
网络“通信兵”，负责外部接入。
### 1.4.1 EMQX
EMQX是开源的MQTT接入方案。
### 1.4.2 Sentry
网络“哨兵”，实现不同协议的兼容，包括HTTP、TCP等，进一步传到Kafka等消息中间件.

## 1.4.3 Spyee
设备“特务”，实现数据搜集和发送。

## 2.项目部署
### 2.1 第三方部署
[部署方式](./deploy.md)

## 说明
**本项目中顶层文件夹可能包含Github的Page设置文件，如`CNAME`、`_config.yml`，可以删除。**