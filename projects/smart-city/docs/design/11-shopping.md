# 购物管理设计

购物不是商场列表，而是城市消费空间：商场、街区店铺、客流、活动与购物排行榜。

## 目标

- 城市场景呈现商场与重点店铺
- 客流与营业状态一眼可读
- 支撑购物排行榜、到店停车、周边餐饮聚合

## 核心对象

| 对象 | 说明 |
|------|------|
| Mall | 商场 / 购物中心 / 商业综合体 |
| Store | 店铺（可属于商场或沿街） |
| Footfall | 客流指数 |
| Promotion | 促销活动 |
| Brand | 品牌（后续） |

### Mall / Store 关键字段

```text
# Mall
id, name, location, floors, storeCount,
footfallIndex, openHours, parkingLotIds[],
tags[], rankPositions[]

# Store
id, mallId?, name, category,
location, rating, priceLevel,
openNow, promotionActive,
tags[]
```

## 场景

1. **逛商圈**：地图上看商场热力与营业状态
2. **找店**：品类过滤（潮玩、数码、美妆、超市）
3. **活动日**：大促期间客流告警与停车联动
4. **购物排行榜**：人气商场、好评店铺、周末必逛
5. **一站式到店**：购物详情 → 停车 → 餐饮 → 打卡

## 可视化

| 层级 | 表现 |
|------|------|
| 商场 | 较大点或面，客流映射状态色 |
| 重点店铺 | 商场下钻后显示，或沿街图层 |
| 促销中 | 数据面板标注，避免画布贴纸化 |

客流指数建议作为 hero metric：`footfallIndex`（0–100）。

## API 规划（`smartcity-shopping`）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/shopping/malls` | 商场列表 |
| GET | `/api/v1/shopping/malls/{id}` | 商场详情 + 客流趋势 |
| GET | `/api/v1/shopping/stores` | 店铺列表 |
| GET | `/api/v1/shopping/stores/{id}` | 店铺详情 |
| GET | `/api/v1/shopping/malls/{id}/stores` | 商场内店铺 |
| GET | `/api/v1/shopping/visualization/contribution` | 场景贡献 |

## 聚合视图（详情页建议信息架构）

```
商场详情
├── 状态：营业中 / 客流
├── 停车：关联车场余位
├── 餐饮：场内或周边 food venues
├── 玩乐：场内 leisure
├── 榜单：购物榜名次
└── 打卡：商场打卡点
```

## 与排行榜

购物模块上报事件：

- 到店 / 停留（可匿名聚合）
- 评分与收藏
- 促销参与

由 `smartcity-ranking` 生成「人气商场榜」「好评店铺榜」等。

## 后续扩展

- 室内楼层图
- 优惠券核销
- 品牌导航与会员积分
- 直播带货点位（谨慎，避免首屏噪音）
