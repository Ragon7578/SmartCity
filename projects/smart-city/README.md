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

- 项目管理（指挥城市介绍、项目周期）：[`docs/pm/`](docs/pm/)
- 设计文档（愿景、架构、各城市业务域）：[`docs/design/`](docs/design/)

## 测试

```bash
cd projects/smart-city/backend
mvn test
```
