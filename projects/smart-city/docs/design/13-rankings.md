# 排行榜设计

为城市消费与互动提供可运营的榜单：美食排行榜、购物排行榜、打卡热榜等。榜单是**结果视图**，原始数据仍属各业务模块。

## 目标

- 多榜并存：美食、购物、玩乐、打卡、小区宜居等
- 规则透明、可配置、可按时段重算
- 榜单结果可回写到城市场景与详情页（名次），但不污染首屏

## 榜单类型

| 榜单 | 数据来源模块 | 典型因子 |
|------|--------------|----------|
| 美食热榜 | food + checkin | 评分、订单/浏览代理、打卡、排队活跃 |
| 夜宵榜 | food | 夜间营业、夜间打卡、品类标签 |
| 购物人气榜 | shopping + checkin | 客流、收藏、打卡、促销活跃 |
| 好评店铺榜 | shopping / food | 评分、评价数、投诉率（负向） |
| 打卡热榜 | checkin | 24h / 7d 打卡量、独立用户数 |
| 周末玩乐榜 | leisure + checkin | 拥挤度、评分、打卡 |
| 宜居小区榜 | community + property + environment | 健康度、环境、配套 |

## 核心对象

| 对象 | 说明 |
|------|------|
| RankBoard | 榜单定义（名称、周期、规则、适用范围） |
| RankFactor | 计分因子与权重 |
| RankSnapshot | 某周期计算结果快照 |
| RankItem | 快照中的一条名次 |

### RankBoard 关键字段

```text
id, name, boardType,
period: realtime|daily|weekly|monthly,
scope: city|district|community|mall,
factors[{key, weight}],
enabled, updatedAt
```

### 计分示例（美食热榜）

```text
score =
  0.35 * normalize(rating) +
  0.25 * normalize(reviewCount) +
  0.20 * normalize(checkins7d) +
  0.10 * normalize(seatUtilization) +
  0.10 * recencyBoost
```

权重可配置，避免写死在业务服务里。

## 计算流

```
业务模块产生事实事件
  （评分、客流、打卡、状态）
        ↓
 ranking 聚合 / 定时任务
        ↓
 RankSnapshot（Top N）
        ↓
 查询 API + 回写名次到 venue/mall 缓存字段
        ↓
 城市场景 / 详情页 / 榜单页展示
```

## API 规划（`smartcity-ranking`）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/ranking/boards` | 榜单列表 |
| GET | `/api/v1/ranking/boards/{id}` | 榜单定义 |
| GET | `/api/v1/ranking/boards/{id}/top?limit=20` | Top N |
| GET | `/api/v1/ranking/items?targetId=` | 某对象的上榜情况 |
| POST | `/api/v1/ranking/boards` | 运营创建榜单 |
| POST | `/api/v1/ranking/boards/{id}/recompute` | 手动重算 |

## 展示建议

### 榜单页

- 一个榜单一页：名次、名称、分值条、关键标签、较上期升降
- 支持按片区 / 品类过滤

### 详情页

- 仅展示弱文案：“本周美食榜第 3 名”
- 不在英雄区堆叠多个徽章

### 城市场景

- 默认不刷屏名次
- 可选“榜单高亮”图层：仅高亮 Top N 点位

## 运营配置

运营可配置：

1. 榜单名称与封面说明
2. 周期与适用片区
3. 因子权重
4. 黑白名单（违规门店剔除）
5. 发布与下线

## 反作弊与公平

- 打卡频控、异常坐标过滤
- 新店冷启动保护（单独“新锐榜”）
- 刷评分检测（短时同端密集好评）
- 公示规则摘要，提升信任

## 与模块边界

| 模块 | 做什么 | 不做什么 |
|------|--------|----------|
| food/shopping/leisure/checkin | 上报事实与基础分 | 不各自维护冲突榜单算法 |
| ranking | 统一计分、快照、查询 | 不存门店主数据 |
| city-scene | 可选高亮展示 | 不计算榜单 |

## 首期建议落地顺序

1. 美食热榜（周榜）
2. 购物人气榜（周榜）
3. 打卡热榜（日榜 / 实时）
4. 再扩展夜宵榜、玩乐榜、宜居小区榜
