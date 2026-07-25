# Spring Cloud Microservice Architecture

## Intent

The smart-city backend is split into independently expandable Spring Cloud modules.
Each business domain (traffic, parking, food, shopping, energy, environment) is its own service and can grow without rewriting the whole platform.

## Topology

```
                 ┌──────────────────────┐
                 │  smartcity-gateway   │  :8080  UI + API entry
                 │  (Spring Cloud GW)   │
                 └──────────┬───────────┘
                            │
                 ┌──────────▼───────────┐
                 │ smartcity-city-scene │  :8090  scene aggregator (Feign)
                 └──────────┬───────────┘
       ┌─────────┬─────────┼─────────┬─────────┬─────────┐
       ▼         ▼         ▼         ▼         ▼         ▼
   traffic   parking     food    shopping   energy  environment
    :8081     :8082     :8083     :8084     :8085     :8086
                            │
                 ┌──────────▼───────────┐
                 │ smartcity-registry   │  :8761  Eureka
                 └──────────────────────┘
```

## Maven Modules

| Module | Role | Expand with |
|--------|------|-------------|
| `smartcity-common` | Shared DTOs + module runtime | New shared contracts |
| `smartcity-registry` | Eureka service discovery | HA registry later |
| `smartcity-gateway` | Single entry + static UI | Auth, rate limits |
| `smartcity-city-scene` | Aggregates module contributions | Caching, map tiles |
| `smartcity-traffic` | Traffic management | Signals, incidents, cameras |
| `smartcity-parking` | Parking management | Lots, pricing, reservations |
| `smartcity-food` | Food management | Markets, inspections, queues |
| `smartcity-shopping` | Shopping management | Malls, footfall, promotions |
| `smartcity-energy` | Energy management | Grid, EV, outages |
| `smartcity-environment` | Environment management | AQI, flood, noise |

## Standard Module API

Every domain module exposes the same surface so new modules plug into gateway + aggregator quickly:

| Method | Path | Purpose |
|--------|------|---------|
| GET | `/api/v1/{module}/info` | Service identity |
| GET | `/api/v1/{module}/assets` | Asset list |
| GET | `/api/v1/{module}/assets/{id}` | Visual metrics detail |
| GET | `/api/v1/{module}/visualization/contribution` | Scene fragment |

## How to Add a New Module

1. Copy an existing domain module (e.g. `smartcity-food`)
2. Rename package / `CityModule` enum value / port / application name
3. Seed domain-specific assets in `*DataInitializer`
4. Add a Feign client in `smartcity-city-scene`
5. Add a gateway route `/api/v1/{new-module}/**`
6. Register the module in the parent `pom.xml`

No changes are required inside unrelated business modules.

## Run

```bash
cd backend
./scripts/start-all.sh
# UI http://localhost:8080
# Eureka http://localhost:8761
```

```bash
./scripts/stop-all.sh
```

## Notes

- In-memory stores are intentional for the initialization phase.
- Replace each module’s store with its own DB when that domain matures.
- Visualization still follows: visualize city things first, then display data visually.
