# Smart City Vision — Visualize First, Then Show Data

## Core Idea

Managing a smart city is not primarily about “digitizing” assets into databases.
It is about two ordered steps:

1. **Visualize** city things — make roads, buildings, energy, water, transit, and sensors visible as a shared spatial picture.
2. **Display data visually** — attach live metrics, status, and trends to those visuals so operators understand the city at a glance.

Digitization is infrastructure. Visualization and visual data display are how management becomes usable.

## Product Name

**Urban Lens** / 城景

A smart-city operations surface that turns physical city systems into an interactive visual layer, then paints operational data onto that layer.

## Goals

| Goal | Description |
|------|-------------|
| Shared spatial truth | Every managed asset has a place on the city view |
| Glanceable status | Color, motion, and density communicate health before numbers |
| Drill-down data | Selecting an asset reveals metrics in a visual, not tabular-first, way |
| Domain modularity | Traffic, energy, environment, public safety plug into one canvas |

## Non-Goals (v1)

- Full digital twin simulation physics
- Autonomous control loops / closed-loop actuation
- Replacing existing SCADA or GIS systems of record

## Principle Stack

```
Physical City
    ↓ sensors / GIS / IoT / municipal systems
Digital Twin Model (identity, location, relations)
    ↓
Visualization Layer (map / 3D / schematic)
    ↓
Visual Data Display (status, charts, heat, timelines)
    ↓
Operator Action (inspect, alert, dispatch)
```

## Success Criteria

- An operator can identify a problem area in under 5 seconds from the city view alone.
- Selecting any asset shows its current state as a visual summary before raw tables.
- Design docs and prototype stay aligned: visualize → attach data → act.
