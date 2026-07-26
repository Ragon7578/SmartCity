# 打卡与兴趣点（POI）设计

在城市中设置可互动的点：市民/游客到点打卡，形成足迹与热度，反哺排行榜与推荐。

## 目标

- 运营可配置打卡点（景点、网红店、市政地标、活动点）
- 用户到点打卡、累计足迹与徽章
- 打卡热度成为美食榜 / 玩乐榜的重要信号
- 打卡点在城市场景上可见，但不遮挡主图层

## 核心对象

| 对象 | 说明 |
|------|------|
| CheckinPoint | 打卡点（可绑定已有 Venue / Mall / Community amenity） |
| CheckinEvent | 一次打卡记录 |
| UserTrail | 用户足迹摘要 |
| Badge | 徽章 / 成就 |
| CheckinCampaign | 打卡活动（集点换礼、周末路线） |

### CheckinPoint 关键字段

```text
id, name, description,
location(x,y),
bindType: food|shopping|leisure|community|standalone,
bindId?,
coverImage?, tags[],
radiusMeters,          // 有效打卡半径
enable: boolean,
heatScore, checkinCount24h, checkinCountTotal,
rankEligible: boolean
```

## 打卡规则（建议）

| 规则 | 说明 |
|------|------|
| 地理围栏 | 需在 `radiusMeters` 内（演示阶段可放宽） |
| 频控 | 同一点同一用户每日限 N 次 |
| 绑定门店 | 可复用门店坐标，不必重复建点 |
| 隐私 | 对外只展示聚合热度，个人足迹需登录可见 |

## 场景

1. **城市打卡地图**：图层开关显示打卡点热力
2. **到店打卡**：餐厅 / 商场详情页一键打卡
3. **主题路线**：例如“海港夜市 5 打卡”活动
4. **徽章**：首次打卡、连续周末、集齐某商圈
5. **运营设点**：后台配置临时活动点（市集、展览）

## 可视化

- 打卡点用独立图层，默认可关
- 热度用点大小或状态色，不使用贴片广告风
- 选中后数据面板展示：今日打卡数、热度趋势、关联榜单、活动说明

## API 规划（`smartcity-checkin`）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/checkin/points` | 打卡点列表 |
| GET | `/api/v1/checkin/points/{id}` | 打卡点详情 |
| POST | `/api/v1/checkin/points` | 运营创建打卡点 |
| PATCH | `/api/v1/checkin/points/{id}` | 更新 / 启停 |
| POST | `/api/v1/checkin/events` | 用户打卡 |
| GET | `/api/v1/checkin/trails/me` | 我的足迹 |
| GET | `/api/v1/checkin/points/{id}/heat` | 热度序列 |
| GET | `/api/v1/checkin/visualization/contribution` | 场景贡献 |

## 事件流出

```
CheckinEvent
  → ranking 服务累计热度
  → food/shopping/leisure 可读取关联热度
  → city-scene 聚合展示
```

## 与其他模块

| 模块 | 关系 |
|------|------|
| food / leisure / shopping | 门店可绑定打卡点 |
| community | 小区配套也可设打卡（如社区花园） |
| ranking | 打卡量进入排行因子 |
| parking | 热门打卡点详情推荐停车 |

## 后续扩展

- AR 打卡 / 拍照校验（可选）
- 社交分享卡片
- 城市护照（分区集章）
- 反作弊（GPS 漂移、模拟定位检测）
