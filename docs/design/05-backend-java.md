# Java Backend Service Architecture

## Role

The smart city platform runs as a **Java service** (Spring Boot). The frontend is a visualization client; the backend owns city model initialization, asset registry, telemetry fusion, and API contracts.

## Package Layout

```
com.urbanlens.smartcity
├── SmartCityApplication          # service entrypoint
├── bootstrap.CityDataInitializer # seeds city model on startup
├── config                        # CORS, MVC, API errors
├── controller                    # REST boundary (/api/v1)
├── domain                        # city things + status + metrics
├── dto                           # API payloads for visualization
├── repository.CityRepository     # in-memory registry (swap later)
└── service
    ├── AssetRegistryService      # catalog of managed assets
    ├── DataFusionService         # telemetry → status / trends
    └── CitySceneService          # scene + detail for the UI
```

## Service Responsibilities

| Service | Responsibility |
|---------|----------------|
| `AssetRegistryService` | Identity and lookup of city assets |
| `DataFusionService` | Periodic telemetry ticks, status derivation, events |
| `CitySceneService` | Assemble visualization scene and visual metric payloads |

Controllers stay thin. Domain logic stays in services.

## Initialization Flow

```
Application start
  → CityDataInitializer.run()
      → clear registry
      → seed districts / corridors / assets
  → DataFusionService scheduled ticks begin
  → REST API serves /api/v1/city/scene and /api/v1/assets/{id}
  → Static UI served from the same service
```

## API Surface (v1)

| Method | Path | Purpose |
|--------|------|---------|
| GET | `/api/v1/city/scene` | Canvas districts, corridors, asset glyphs |
| GET | `/api/v1/city/scene?domain=energy` | Domain-filtered scene |
| GET | `/api/v1/assets/{id}` | Visual data stage for one asset |
| GET | `/api/v1/system/info` | Service identity + asset count |
| GET | `/actuator/health` | Liveness |

## Evolution Path

1. Replace `CityRepository` with JPA + PostGIS
2. Replace `DataFusionService` simulation with MQTT/Kafka consumers
3. Add auth and role-scoped layers
4. Keep controller/DTO contracts stable for the frontend
