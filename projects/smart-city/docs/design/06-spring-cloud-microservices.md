# Spring Cloud 微服务架构

## 意图

后端按业务域拆成可独立扩展的 Spring Cloud 模块。停车、物业、小区、购物、吃喝玩乐、打卡、排行榜等都可以新增服务，而不改无关模块。

## 拓扑

```
                 ┌──────────────────────┐
                 │  smartcity-gateway   │  :8080
                 └──────────┬───────────┘
                            │
                 ┌──────────▼───────────┐
                 │ smartcity-city-scene │  :8090
                 └──────────┬───────────┘
        已有业务服务 │ 规划业务服务
   traffic parking food shopping energy environment
   community property leisure checkin ranking
                            │
                 ┌──────────▼───────────┐
                 │ smartcity-registry   │  :8761
                 └──────────────────────┘
```

## 模块表

### 平台

| 模块 | 端口 | 角色 |
|------|------|------|
| `smartcity-common` | — | 公共 DTO 与模块运行时 |
| `smartcity-registry` | 8761 | Eureka |
| `smartcity-gateway` | 8080 | 入口 + UI |
| `smartcity-city-scene` | 8090 | 场景聚合 |

### 已有业务

| 模块 | 端口 | 扩展方向 |
|------|------|----------|
| `smartcity-traffic` | 8081 | 信号、事件、摄像头 |
| `smartcity-parking` | 8082 | 余位、引导、预约 |
| `smartcity-food` | 8083 | 门店、排队、美食数据 |
| `smartcity-shopping` | 8084 | 商场、店铺、客流 |
| `smartcity-energy` | 8085 | 电网、充电 |
| `smartcity-environment` | 8086 | 空气、水位、噪声 |

### 规划业务

| 模块 | 建议端口 | 职责 |
|------|----------|------|
| `smartcity-community` | 8087 | 小区空间与配套 |
| `smartcity-property` | 8088 | 物业工单、巡检、设施 |
| `smartcity-leisure` | 8089 | 玩乐场馆 |
| `smartcity-checkin` | 8091 | 打卡点与足迹 |
| `smartcity-ranking` | 8092 | 美食/购物/打卡等榜单 |

## 标准模块 API

| 方法 | 路径 | 用途 |
|------|------|------|
| GET | `/api/v1/{module}/info` | 服务身份 |
| GET | `/api/v1/{module}/assets` 或领域列表 | 对象列表 |
| GET | `/api/v1/{module}/.../{id}` | 详情 |
| GET | `/api/v1/{module}/visualization/contribution` | 场景碎片 |

打卡与排行榜在标准贡献接口之外，另有互动 / 榜单专用 API，见对应设计文档。

## 新增模块步骤

1. 复制已有域模块（如 `smartcity-food`）
2. 修改包名、`CityModule`、端口、应用名
3. 在 `*DataInitializer` 写入领域种子数据
4. 在 `city-scene` 增加 Feign 客户端
5. 在 gateway 增加 `/api/v1/{new}/**` 路由
6. 在父 `pom.xml` 注册模块

## 运行

```bash
cd projects/smart-city/backend
./scripts/start-all.sh
# UI http://localhost:8080
# Eureka http://localhost:8761
```

## 说明

- 初期可用内存仓储；成熟后各模块独立持久化
- 可视化原则不变：先看见城市事物，再展示数据
- 生活域设计详见 [07 城市业务域总览](./07-city-domain-overview.md) 及后续专题文档
