# 系统架构

## 总览

城景是 **Spring Cloud 微服务** + 浏览器城市场景客户端。

```
┌────────────────────────────────────────────────────────────┐
│  表现层 — 城市场景 + 数据面板 + 榜单/打卡页                   │
├────────────────────────────────────────────────────────────┤
│  API 网关 — smartcity-gateway (:8080)                      │
├────────────────────────────────────────────────────────────┤
│  场景聚合 — smartcity-city-scene（Feign 汇总各模块）         │
├────────────────────────────────────────────────────────────┤
│  业务微服务（可独立扩展）                                     │
│  已有：traffic · parking · food · shopping · energy · env    │
│  规划：community · property · leisure · checkin · ranking    │
├────────────────────────────────────────────────────────────┤
│  注册中心 — Eureka (:8761)                                 │
└────────────────────────────────────────────────────────────┘
```

## 业务能力分组

| 分组 | 模块 | 能力 |
|------|------|------|
| 出行 | traffic, parking | 路况、停车引导 |
| 居住 | community, property | 小区空间、物业工单与设施 |
| 消费 | food, shopping, leisure | 吃喝玩乐、购物 |
| 互动运营 | checkin, ranking | 打卡点、排行榜 |
| 市政 | energy, environment | 能源与环境感知 |

## 场景聚合

`city-scene` 拉取各模块 `/visualization/contribution`，合并：

- 片区 / 小区边界
- 廊道（交通、能源）
- 资产点（车场、门店、打卡点、设施）

详情请求按模块路由；榜单与打卡可走独立 API。

## 数据流（生活闭环示例）

```
市民点选商场
  → shopping 详情（客流）
  → parking 附近车场
  → food 场内/周边餐饮
  → checkin 打卡
  → ranking 查询名次
```

## 运行时

| 项 | 选择 |
|----|------|
| 语言 | Java 21 |
| 框架 | Spring Boot 3.3 + Spring Cloud 2023.0 |
| 发现 | Eureka |
| 入口 | Spring Cloud Gateway |
| UI | 网关静态资源 / `web/` |

细节见：

- [城市业务域总览](./07-city-domain-overview.md)
- [微服务说明](./06-spring-cloud-microservices.md)
