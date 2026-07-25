# smartCity / 智慧城市

**Urban Lens (城景)** — visualize city things first, then display data visually.

Project path: `projects/smart-city/`

Primary remote (Gitee): https://gitee.com/ragon6749/smart-city

Backend is a **Spring Cloud microservice** architecture. Business domains are separate expandable modules.

## Modules

| Service | Port | Responsibility |
|---------|------|----------------|
| `smartcity-registry` | 8761 | Eureka discovery |
| `smartcity-gateway` | 8080 | API entry + UI |
| `smartcity-city-scene` | 8090 | Scene aggregation |
| `smartcity-traffic` | 8081 | Traffic management |
| `smartcity-parking` | 8082 | Parking management |
| `smartcity-food` | 8083 | Food management |
| `smartcity-shopping` | 8084 | Shopping management |
| `smartcity-energy` | 8085 | Energy management |
| `smartcity-environment` | 8086 | Environment management |

## Quick start

Requires **Java 21** and **Maven**.

```bash
cd projects/smart-city/backend
chmod +x scripts/*.sh
./scripts/start-all.sh
```

Open:

- UI / API gateway: http://localhost:8080
- Eureka dashboard: http://localhost:8761

Stop:

```bash
./scripts/stop-all.sh
```

### Useful APIs

| Endpoint | Description |
|----------|-------------|
| `GET /api/v1/city/scene` | Aggregated city canvas |
| `GET /api/v1/assets/{id}` | Visual metrics for one asset |
| `GET /api/v1/modules` | Module list |
| `GET /api/v1/traffic/**` | Traffic module direct API |
| `GET /api/v1/parking/**` | Parking module direct API |
| `GET /api/v1/food/**` | Food module direct API |
| `GET /api/v1/shopping/**` | Shopping module direct API |

## Design docs

See [`docs/design/`](docs/design/), especially:

- [Architecture](docs/design/02-architecture.md)
- [Spring Cloud microservices](docs/design/06-spring-cloud-microservices.md)

## Expand a module later

1. Copy an existing domain module under `projects/smart-city/backend/`
2. Change module name, port, and seed data
3. Add gateway route + city-scene Feign client
4. Deploy independently

## Tests

```bash
cd projects/smart-city/backend
mvn test
```
