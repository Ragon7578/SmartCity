# 小区与物业设计

城市居住层：先看见**小区空间**，再挂上**物业服务与设施状态**。

## 拆分原则

| 模块 | 职责 | 为什么分开 |
|------|------|------------|
| `smartcity-community` | 小区空间与住户相关主数据 | 空间边界、楼栋、人口结构变化慢 |
| `smartcity-property` | 物业运营：报修、巡检、公告、收费 | 事务型、工单流，可独立扩容 |

两者通过 `communityId` 关联。

## 小区（Community）

### 核心对象

| 对象 | 说明 |
|------|------|
| Community | 小区 / 园区 |
| Building | 楼栋 |
| Unit / Room | 单元 / 房屋（可后期细化） |
| Gate | 出入口 / 门禁点 |
| Amenity | 小区配套（健身区、快递柜、会所） |

### Community 关键字段

```text
id, name, address, districtId,
boundaryPolygon | center(x,y),
buildingCount, householdCount,
propertyOrgId, tags[],
parkingLotIds[], amenityIds[]
```

### 可视化

- 小区以面或圆斑显示在城市场景
- 选中后展示：户数、物业评分、今日工单、停车余位、配套点
- 门禁 / 快递柜 / 健身区作为小区内 POI

## 物业（Property）

### 核心对象

| 对象 | 说明 |
|------|------|
| WorkOrder | 报修 / 投诉 / 建议工单 |
| Inspection | 巡检任务与结果 |
| Announcement | 公告 |
| Facility | 物业管辖设施（电梯、水泵、监控） |
| FeeBill | 物业费账单（后续） |

### 典型流程

```
居民提交报修
  → 物业接单 / 派工
  → 处理中（可在地图上看设施点状态）
  → 完成评价
  → 汇总进小区健康度
```

### 小区健康度（hero metric 建议）

综合计算，供城市场景一览：

| 因子 | 权重建议 |
|------|----------|
| 未关闭工单数 | 高 |
| 设施在线率 | 高 |
| 公告及时性 | 中 |
| 居民满意度 | 中 |
| 停车紧张度 | 低（联动停车模块） |

## API 规划

### Community

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/community/communities` | 小区列表 |
| GET | `/api/v1/community/communities/{id}` | 小区详情 |
| GET | `/api/v1/community/communities/{id}/amenities` | 配套设施 |
| GET | `/api/v1/community/visualization/contribution` | 场景贡献 |

### Property

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/property/orders` | 工单列表 |
| POST | `/api/v1/property/orders` | 创建报修 |
| GET | `/api/v1/property/communities/{id}/health` | 小区健康度 |
| GET | `/api/v1/property/facilities/{id}` | 设施详情 |
| GET | `/api/v1/property/visualization/contribution` | 设施点场景贡献 |

## 与吃喝玩乐 / 停车的关系

- 小区周边美食、购物、玩乐通过空间邻近推荐
- 小区自有车场走停车模块，访客临停由物业规则约束
- 可在小区详情页聚合：停车余位、周边榜单、附近打卡点

## 后续扩展

- 访客通行二维码
- 电梯困人告警联动安全域
- 物业费线上缴纳与催缴提醒
- 业主委员会公告分区权限
