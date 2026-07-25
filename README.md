# smartCity / 智慧城市

**Urban Lens (城景)** — manage a smart city by visualizing city things first, then displaying their data visually.

Java backend service + browser visualization client.

## Idea

Digitization alone is not enough.

1. **Visualize** roads, energy, environment, safety, and civic assets on a shared city canvas.
2. **Display data visually** — status, meters, trends, and events attached to those assets.

## Quick start (recommended)

Requires **Java 21** and **Maven**.

```bash
cd backend
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080).

The Spring Boot service:

- initializes the city model on startup (`CityDataInitializer`)
- exposes REST APIs under `/api/v1`
- serves the Urban Lens UI from the same process
- refreshes telemetry on a schedule

### Useful endpoints

| Endpoint | Description |
|----------|-------------|
| `GET /api/v1/city/scene` | Canvas scene (districts, corridors, assets) |
| `GET /api/v1/assets/{id}` | Visual metrics for one asset |
| `GET /api/v1/system/info` | Service identity |
| `GET /actuator/health` | Health check |

## Architecture

```
web/        Visualization client (calls Java API)
backend/    Spring Boot service (registry, fusion, scene APIs)
docs/design Design documents
```

See [`docs/design/`](docs/design/) for the full design pack, especially:

- [Architecture](docs/design/02-architecture.md)
- [Java Backend](docs/design/05-backend-java.md)

## Develop frontend only

The UI source lives in `web/`. During `mvn` builds it is copied into the backend classpath. Prefer running the Java service so the UI can reach `/api/v1`.

## Tests

```bash
cd backend
mvn test
```
