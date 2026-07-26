# 停车管理设计

把城市停车从“车场名单”升级为**可看见、可引导、可预约、可结算**的空间服务。

## 目标

- 在城市场景上直接看到车场位置与余位状态
- 市民快速找到附近可用车位
- 运营侧掌握周转率、违停与高峰压力

## 核心对象

| 对象 | 说明 |
|------|------|
| ParkingLot | 停车场 / 路边泊位区 |
| ParkingSpace | 单个车位（可选细化） |
| ParkingSession | 一次停车会话（入场 → 离场） |
| ParkingGuide | 导航引导结果（最近可用车场） |
| TariffPlan | 收费规则 |

### ParkingLot 关键字段

```text
id, name, communityId?, mallId?,
type: underground | surface | roadside | mechanical,
totalSpaces, freeSpaces,
pricePerHour, openHours,
location(x,y), status,
supportReservation: boolean,
evSpaces, accessibleSpaces
```

## 场景

1. **找车位**：按当前位置 / 目的地（商场、小区、景点）推荐车场
2. **余位可视化**：城市场景用颜色表示空闲 / 紧张 / 已满
3. **到店联动**：商场、餐饮、景点详情页显示“附近停车”
4. **小区停车**：业主车位、访客临停、道闸联动（与物业模块衔接）
5. **错峰与活动**：大型活动期间临时开放或限流

## 可视化规则

| 余位占比 | 状态 | 画布表现 |
|----------|------|----------|
| ≥ 40% | online | 稳定绿色/棕色点 |
| 15%–40% | warning | 缓慢脉冲 |
| < 15% 或 0 | critical | 明显脉冲 + “紧张/已满” |

## API 规划（`smartcity-parking`）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/v1/parking/lots` | 车场列表 |
| GET | `/api/v1/parking/lots/{id}` | 车场详情与趋势 |
| GET | `/api/v1/parking/nearby?x=&y=&radius=` | 附近可用车场 |
| GET | `/api/v1/parking/guide?destinationId=` | 目的地停车引导 |
| POST | `/api/v1/parking/reservations` | 预约（后续） |
| GET | `/api/v1/parking/visualization/contribution` | 城市场景贡献 |

## 与其他模块关系

```
shopping / food / leisure / community
        ↓ 提供 destination
     parking.guide
        ↓
   推荐车场 + 余位 + 步行距离
```

## 后续扩展

- 车牌识别入场、电子支付
- 反向寻车（室内地图）
- 新能源车位单独图层
- 路边泊位地磁实时占用
