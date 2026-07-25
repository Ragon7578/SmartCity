# System Architecture

## Overview

Urban Lens is delivered as a **Java backend service** with a browser visualization client.

```
┌─────────────────────────────────────────────────────────┐
│  Presentation — City Canvas + Visual Data Panels (web/) │
├─────────────────────────────────────────────────────────┤
│  REST API — /api/v1  (Spring Boot controllers)          │
├─────────────────────────────────────────────────────────┤
│  Application Services                                   │
│   • CitySceneService      visualization payloads        │
│   • AssetRegistryService  city-thing catalog            │
│   • DataFusionService     telemetry → visual status     │
├─────────────────────────────────────────────────────────┤
│  Asset Registry — CityRepository (in-memory → PostGIS)  │
├─────────────────────────────────────────────────────────┤
│  Bootstrap — CityDataInitializer seeds the city model   │
└─────────────────────────────────────────────────────────┘
```

## Components

### 1. Asset Registry

Canonical catalog of city things:

- `id`, `name`, `domain` (traffic | energy | environment | safety | civic)
- geometry (`point` for assets, path for corridors, rect for districts)
- status + hero / supporting metrics for visual display

Owned by `AssetRegistryService` + `CityRepository`.

### 2. Data Fusion

Ingests (currently simulates) telemetry and maps it onto assets:

- streaming metrics and trend windows
- status machines (online / warning / critical / offline)
- event log entries for the Data Stage

Owned by `DataFusionService`.

### 3. Visualization Scene Service

Builds API payloads the UI can paint:

- city scene (districts, corridors, asset glyphs)
- asset detail (status, hero metric, trend, events)

Owned by `CitySceneService`.

### 4. Presentation (Urban Lens UI)

Two primary surfaces, served by the same Java process in v1:

1. **City Canvas** — spatial visualization of managed things
2. **Data Stage** — visual display of selected asset metrics

## Data Flow

```
Initializer → Registry
Telemetry tick → DataFusion → Asset status/metrics
UI → GET /api/v1/city/scene → Canvas render
UI → GET /api/v1/assets/{id} → Data Stage visuals
```

## Runtime

| Concern | Choice |
|---------|--------|
| Language | Java 21 |
| Framework | Spring Boot 3.3 |
| Packaging | Maven module `backend/` |
| UI delivery | Copied into classpath `static/` at build |
| Port | `8080` |

## Security & Ops Notes

- Layer visibility can become role-scoped later.
- Telemetry paths are read-mostly; write-back goes through domain systems.
- Stale / missing data must stay visually distinct from healthy live data.
- See [05-backend-java.md](./05-backend-java.md) for package-level detail.
