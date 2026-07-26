# 智慧城市 / smart-city

Spring Cloud 微服务 + 城市可视化。

## 启动

```bash
cd projects/smart-city/backend
chmod +x scripts/*.sh
./scripts/start-all.sh
```

- UI: http://localhost:8080
- Eureka: http://localhost:8761

```bash
./scripts/stop-all.sh
```

## 模块

| 服务 | 端口 |
|------|------|
| registry | 8761 |
| gateway | 8080 |
| city-scene | 8090 |
| traffic | 8081 |
| parking | 8082 |
| food | 8083 |
| shopping | 8084 |
| energy | 8085 |
| environment | 8086 |

## 文档

见 [`docs/design/`](docs/design/)，含停车、小区物业、吃喝玩乐、购物、打卡、排行榜等城市业务设计。

## 测试

```bash
cd projects/smart-city/backend
mvn test
```
