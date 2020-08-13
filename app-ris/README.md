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