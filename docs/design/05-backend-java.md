# Java Backend Service Architecture

## Role

The smart city platform runs as **Spring Cloud microservices** (Java 21 / Spring Boot 3.3).
The frontend is a visualization client. Domain modules own their data and APIs; `city-scene` aggregates them for the canvas.

For the full microservice map see [06-spring-cloud-microservices.md](./06-spring-cloud-microservices.md).

## Layering (per domain module)

```
controller  → ModuleApiController surface (/api/v1/{module})
runtime     → ModuleRuntimeService (catalog + telemetry)
store       → ModuleStore (in-memory now, DB later)
bootstrap   → *DataInitializer seeds module data
```

## Aggregated API (via gateway)

| Method | Path | Purpose |
|--------|------|---------|
| GET | `/api/v1/city/scene` | Combined canvas scene |
| GET | `/api/v1/assets/{id}` | Asset detail resolved across modules |
| GET | `/api/v1/modules` | Registered module health/info |
| GET | `/api/v1/system/info` | Platform identity |
| GET | `/api/v1/{module}/**` | Direct domain module APIs |

## Evolution Path

1. Give each module its own persistence
2. Replace simulated telemetry with MQTT/Kafka consumers per module
3. Add auth at the gateway
4. Keep module contribution contracts stable for visualization
