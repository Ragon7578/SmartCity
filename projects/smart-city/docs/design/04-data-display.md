# Visual Data Display — Metrics People Can See

## Intent

After city things are visible, data must be shown *visually* — not as a spreadsheet that happens to sit beside a map.

Visual data display answers: *How is it doing? Is it changing? Does it need attention?*

## Display Modes

### 1. Status Block

Large state word + color bar + last-updated age.

Use for: online / warning / critical / offline.

### 2. Spark & Trend

Compact line or area chart for the last 1h / 24h / 7d.

Use for: traffic volume, power load, AQI.

### 3. Composition Rings / Bars

Share of capacity or category mix.

Use for: parking fill, energy mix, waste diversion.

### 4. Heat & Density on Canvas

District-level intensity painted on the map itself.

Use for: congestion, pollution plumes, outage clusters.

### 5. Event Timeline

Vertical visual log of alerts and state changes.

Use for: incident history on a selected asset.

## Selection Contract

When an asset is selected:

1. Canvas focuses the asset (visualize).
2. Data Stage opens with:
   - identity (name, domain, location)
   - status block
   - primary metric visual
   - secondary trend
   - recent events

Raw tables are optional and collapsed by default.

## Metric Priority

For each domain, define one **hero metric** and two supporting metrics:

| Domain | Hero metric | Supporting |
|--------|-------------|------------|
| Traffic | Congestion index | Throughput, incident count |
| Parking | Occupancy % | Free spaces, turnover |
| Energy | Load vs capacity | Outage minutes, renewable share |
| Environment | AQI | PM2.5, noise dB |
| Community / Property | Community health | Open work orders, facility online % |
| Food | Seat utilization / wait | Rating, check-ins 7d |
| Shopping | Footfall index | Open stores, active promotions |
| Leisure | Crowd index | Rating, ticket availability |
| Checkin | Heat score | Check-ins 24h / 7d |
| Ranking | Board position | Score, change vs last period |

### 6. Rank Strip（榜条）

Compact position + score bar for “美食榜 #3 / 购物榜 #1”.
Use in detail panels, not as floating stickers on the canvas.

## Stale & Missing Data

| Condition | Visual treatment |
|-----------|------------------|
| Live (< 2 min) | Solid accent |
| Aging (2–15 min) | Desaturated + “aging” label |
| Stale (> 15 min) | Hatch pattern + timestamp |
| Missing | Hollow glyph + “no feed” |

## Anti-Patterns

- Leading with dense numeric tables
- More than three charts competing in the Data Stage
- Status chips that only change color with no text/shape change
- Dashboard widgets floating over the city canvas as stickers

## Prototype Mapping

The included `web/` prototype demonstrates:

- City Canvas with domain layers and asset glyphs
- Click-to-select focus
- Data Stage with status, hero metric, trend sparkline, and event list
