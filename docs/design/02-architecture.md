# System Architecture

## Overview

Urban Lens is a layered smart-city visualization platform.

```
┌─────────────────────────────────────────────────────────┐
│  Presentation — City Canvas + Visual Data Panels        │
├─────────────────────────────────────────────────────────┤
│  Visualization Service — tiles, layers, asset glyphs    │
├─────────────────────────────────────────────────────────┤
│  Data Fusion — normalize IoT / GIS / civic feeds        │
├─────────────────────────────────────────────────────────┤
│  Asset Registry — identity, geometry, domain tags       │
├─────────────────────────────────────────────────────────┤
│  Source Systems — traffic, energy, env, safety, GIS     │
└─────────────────────────────────────────────────────────┘
```

## Components

### 1. Asset Registry

Canonical catalog of city things:

- `id`, `name`, `domain` (traffic | energy | environment | safety | civic)
- geometry (`point` | `line` | `polygon`)
- relations (road → signals, building → meters)
- visualization hints (icon, layer, z-order)

### 2. Data Fusion

Ingests telemetry and events, maps them onto assets:

- streaming metrics (occupancy, kWh, AQI, camera health)
- state machines (online / warning / critical / offline)
- time-window aggregates for charts and heatmaps

### 3. Visualization Service

Builds the spatial scene:

- basemap + thematic layers
- asset glyphs and corridors
- heat / flow overlays
- camera / focus transitions for drill-down

### 4. Presentation (Urban Lens UI)

Two primary surfaces:

1. **City Canvas** — spatial visualization of managed things
2. **Data Stage** — visual display of selected asset / district metrics

## Data Flow

```
Source → Ingest → Normalize → Bind to Asset →
  Render on Canvas → Operator selects → Visual Data Stage updates
```

## Suggested Tech (prototype → production)

| Layer | Prototype | Production candidate |
|-------|-----------|----------------------|
| UI | Static HTML/CSS/JS | React + MapLibre / Cesium |
| Assets | Local JSON | PostGIS + asset API |
| Telemetry | Simulated ticks | MQTT / Kafka → time-series DB |
| Auth | None | SSO + role-based layers |

## Security & Ops Notes

- Layer visibility is role-scoped (e.g. public safety vs. tourism).
- Telemetry paths are read-mostly; write-back goes through domain systems.
- Offline / stale data must be visually distinct from healthy live data.
