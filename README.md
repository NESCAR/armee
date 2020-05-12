# ARMEE
ARMEE军团

```
 ______     ______     __    __     ______     ______    
/\  __ \   /\  == \   /\ "-./  \   /\  ___\   /\  ___\   
\ \  __ \  \ \  __<   \ \ \-./\ \  \ \  __\   \ \  __\   
 \ \_\ \_\  \ \_\ \_\  \ \_\ \ \_\  \ \_____\  \ \_____\ 
  \/_/\/_/   \/_/ /_/   \/_/  \/_/   \/_____/   \/_____/ 
                                                         
```

## 组织架构

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
## Enginee
负责基础任务“工兵”，可供所有上层应用复用。

## Armyee
军团，Armee项目核心（技术中台），包括可复用的各种模块，供Application或者其他上层应用调用
### Apollo
分布式配置中心

## Applicationee
面向不同需求的应用

## Correspondentee
### EMQX
EMQX是开源的MQTT介入方案。
### Sentry
网络“哨兵”，实现不同协议的兼容，包括HTTP、TCP等，进一步传到Kafka等消息中间件.

## Spyee
设备“特务”，实现数据搜集和发送。
