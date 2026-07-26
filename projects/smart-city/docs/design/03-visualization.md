# Visualization Design — City Things Become Visible

## Intent

Before showing numbers, show the city.

Visualization answers: *Where is it? What kind of thing is it? How does it relate to neighbors?*

## Visual Hierarchy

1. **Basemap** — streets, districts, water, parks (context, low contrast)
2. **Network layers** — roads, power, water corridors (structure)
3. **Asset glyphs** — sensors, signals, stations, cameras (managed things)
4. **Status aura** — color / pulse for health (pre-numeric signal)
5. **Selection focus** — dim surroundings, elevate the chosen asset

## Domain Glyph Language

| Domain | Visual cue | Example assets |
|--------|------------|----------------|
| Traffic | Amber linear flow | Intersections, cameras |
| Parking | Warm brown points | Lots, roadside bays |
| Energy | Teal nodes & arcs | Substations, EV hubs |
| Environment | Green soft fields | AQI, noise, flood gauges |
| Community | Soft slate areas | Community boundaries, gates |
| Property | Facility points | Elevators, pumps, cameras |
| Food | Terracotta/orange points | Restaurants, night markets |
| Shopping | Steel-blue points | Malls, street stores |
| Leisure | Violet-gray points | Cinemas, venues |
| Checkin | Compact heat points | Check-in POIs |

Avoid rainbow overload: at most one accent family per active domain layer.
Rank badges stay in the data panel by default; optional “Top N highlight” layer only.

## Interaction Model

| Action | Result |
|--------|--------|
| Pan / zoom | Explore districts |
| Hover asset | Name + one-line status |
| Click asset | Open Data Stage for that asset |
| Toggle layer | Show / hide a domain |
| Click district | Aggregate visual summary for the area |

## Motion (presence, not noise)

1. **Breathing status** — critical assets pulse slowly
2. **Flow shimmer** — traffic / energy corridors suggest direction
3. **Focus transition** — canvas eases toward selection when an asset opens

## Layout (application canvas)

```
┌──────────────────────────────────────────────┐
│ Brand + layer toggles                        │
├────────────────────────────┬─────────────────┤
│                            │                 │
│     City Canvas            │  Data Stage     │
│     (primary visual)       │  (on select)    │
│                            │                 │
└────────────────────────────┴─────────────────┘
```

On mobile, canvas fills the viewport; Data Stage becomes a bottom sheet.

## Accessibility

- Status never relies on color alone (shape + label + pattern).
- Keyboard focus order: layers → canvas assets → data stage.
- Reduced-motion mode disables pulse / shimmer.
