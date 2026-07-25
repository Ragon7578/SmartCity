# System Architecture

## Overview

Urban Lens is a **Spring Cloud microservice** platform with a browser visualization client.

```
┌────────────────────────────────────────────────────────────┐
│  Presentation — City Canvas + Visual Data Panels (web/)    │
├────────────────────────────────────────────────────────────┤
│  API Gateway — smartcity-gateway (:8080)                   │
├────────────────────────────────────────────────────────────┤
│  City Scene Aggregator — Feign fan-in across modules       │
├────────────────────────────────────────────────────────────┤
│  Domain Microservices (independently expandable)           │
│   traffic · parking · food · shopping · energy · environment│
├────────────────────────────────────────────────────────────┤
│  Service Registry — Eureka (:8761)                         │
└────────────────────────────────────────────────────────────┘
```

## Components

### Domain modules

Each business system is a deployable service with its own seed data, APIs, and telemetry:

- Traffic Management
- Parking Management
- Food Management
- Shopping Management
- Energy Management
- Environment Management

### City Scene Aggregator

Calls each module’s `/visualization/contribution` endpoint and merges:

- districts
- corridors
- asset glyphs + statuses

Asset detail requests are resolved by probing module services.

### Gateway

Single entry for UI and `/api/v1/**` routing by module path.

## Data Flow

```
Module initializer → module store
Telemetry tick → module status/metrics
Gateway → city-scene → Feign modules → scene JSON
UI paints canvas, then opens visual metrics for a selected asset
```

## Runtime

| Concern | Choice |
|---------|--------|
| Language | Java 21 |
| Framework | Spring Boot 3.3 + Spring Cloud 2023.0 |
| Discovery | Eureka |
| Entry | Spring Cloud Gateway |
| UI delivery | Gateway static resources |

See [06-spring-cloud-microservices.md](./06-spring-cloud-microservices.md) for module-by-module detail.
