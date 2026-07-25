# Urban Lens Design Documents

Design pack for the smart-city visualization platform (**Urban Lens / 城景**).

## Reading Order

1. [Vision](./01-vision.md) — visualize first, then display data
2. [Architecture](./02-architecture.md) — system layers and data flow
3. [Visualization](./03-visualization.md) — how city things become visible
4. [Data Display](./04-data-display.md) — how metrics are shown visually
5. [Java Backend](./05-backend-java.md) — backend layering and aggregated APIs
6. [Spring Cloud Microservices](./06-spring-cloud-microservices.md) — module split and expansion guide

## Run the platform

```bash
cd backend
./scripts/start-all.sh
```

Open `http://localhost:8080` — gateway serves UI + routes module APIs.
