# smartCity / 智慧城市

**Urban Lens (城景)** — manage a smart city by visualizing city things first, then displaying their data visually.

## Idea

Digitization alone is not enough.

1. **Visualize** roads, energy, environment, safety, and civic assets on a shared city canvas.
2. **Display data visually** — status, meters, trends, and events attached to those assets.

## Quick start

Open the prototype in a browser:

```bash
open web/index.html
# or serve it locally, e.g.
python3 -m http.server 8080 --directory web
```

Then toggle domain layers and click assets to inspect visual metrics.

## Design documents

See [`docs/design/`](docs/design/):

| Doc | Topic |
|-----|-------|
| [Vision](docs/design/01-vision.md) | Product intent and principles |
| [Architecture](docs/design/02-architecture.md) | System layers and data flow |
| [Visualization](docs/design/03-visualization.md) | How city things become visible |
| [Data Display](docs/design/04-data-display.md) | How metrics are shown visually |

## Repo layout

```
docs/design/   Design pack
web/           Interactive visualization prototype
```
