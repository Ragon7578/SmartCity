# 吃喝玩乐设计

覆盖餐饮、轻娱乐与城市生活方式信息：先在地图上看到店与场馆，再展示排队、评分、榜单与打卡。

## 范围

| 子域 | 模块建议 | 示例 |
|------|----------|------|
| 吃 / 喝 | `smartcity-food`（深化） | 餐厅、咖啡馆、夜市、小吃摊 |
| 玩 / 乐 | `smartcity-leisure`（规划） | 影院、剧本杀、乐园、博物馆、夜生活 |

两个模块共享“门店型 POI”模型，但运营指标不同，便于独立扩展。

## 核心对象

| 对象 | 说明 |
|------|------|
| Venue | 门店 / 场馆 |
| MenuHighlight | 招牌菜 / 推荐项目（可选） |
| QueueState | 排队或等位状态 |
| ReviewSummary | 评分与标签摘要 |
| OpenHour | 营业时间 |
| Campaign | 优惠或主题活动 |

### Venue 关键字段

```text
id, name, module: food|leisure,
category: hotpot|cafe|cinema|nightlife|...,
location(x,y), communityId?, mallId?,
rating, reviewCount, priceLevel,
seatUtilization | crowdIndex,
avgWaitMinutes, openNow,
tags[], rankPositions[],   // 榜单位次缓存
checkinPointId?            // 关联打卡点
```

## 关键场景

1. **城市觅食**：按片区 / 商圈筛选火锅、夜宵、咖啡
2. **排队可视化**：等位时长映射为门店状态色
3. **到店闭环**：详情页给停车引导 + 可打卡
4. **美食排行榜**：热门餐厅、夜宵榜、亲子友好榜（见排行榜文档）
5. **周末玩乐**：场馆余票 / 拥挤度 / 营业状态

## 可视化与数据展示

| 信息 | 画布 | 数据面板 |
|------|------|----------|
| 门店位置 | 点位图层 | 名称、品类 |
| 拥挤 / 排队 | 状态色 + 脉冲 | 座位利用率、平均等待 |
| 评分 | 选中后展示 | 星级、标签云 |
| 榜单角标 | 可选弱提示（非贴纸堆叠） | “本周美食榜 #3” |

## API 规划

### Food（深化）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/food/venues` | 餐饮门店 |
| GET | `/api/v1/food/venues/{id}` | 详情：排队、评分、趋势 |
| GET | `/api/v1/food/venues/nearby` | 附近美食 |
| GET | `/api/v1/food/categories` | 品类 |
| GET | `/api/v1/food/visualization/contribution` | 场景贡献 |

### Leisure（规划）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/leisure/venues` | 玩乐场馆 |
| GET | `/api/v1/leisure/venues/{id}` | 详情与拥挤度 |
| GET | `/api/v1/leisure/visualization/contribution` | 场景贡献 |

排行榜统一由 `smartcity-ranking` 提供，food/leisure 只提供原始热度与评分事件。

## 与打卡、停车、购物的联动

```
用户选中餐厅
  → food.venue.detail
  → parking.nearby（到店停车）
  → checkin.start（到店打卡）
  → ranking.position（榜单位次）
```

商场内餐饮可挂 `mallId`，在购物模块详情中聚合。

## 后续扩展

- 预约取号 / 排队叫号
- 菜品级评价与相册
- 卫生评级接入监管数据
- 夜间经济专属图层
