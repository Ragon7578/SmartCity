(function () {
  const layers = Array.from(document.querySelectorAll(".layer"));
  const districtsGroup = document.getElementById("districts");
  const corridorsGroup = document.getElementById("corridors");
  const assetsGroup = document.getElementById("assets");
  const emptyState = document.getElementById("data-empty");
  const detailState = document.getElementById("data-detail");
  const canvasHint = document.getElementById("canvas-hint");

  const activeDomains = new Set(
    layers.filter((b) => b.getAttribute("aria-pressed") === "true").map((b) => b.dataset.domain)
  );

  let selectedId = null;

  function domainColor(domain) {
    return {
      traffic: "var(--traffic)",
      energy: "var(--energy)",
      environment: "var(--environment)",
      safety: "var(--safety)",
      civic: "var(--civic)",
    }[domain];
  }

  function renderDistricts() {
    districtsGroup.innerHTML = CITY.districts
      .map(
        (d) => `
      <g>
        <rect class="district" x="${d.x}" y="${d.y}" width="${d.w}" height="${d.h}" rx="2" />
        <text class="district-label" x="${d.x + 1.5}" y="${d.y + 4}">${d.name}</text>
      </g>`
      )
      .join("");
  }

  function renderCorridors() {
    corridorsGroup.innerHTML = CITY.corridors
      .map((c) => {
        const hidden = activeDomains.size && !activeDomains.has(c.domain);
        return `<path class="corridor ${hidden ? "hidden" : ""}" data-domain="${c.domain}" d="${c.d}" />`;
      })
      .join("");
  }

  function renderAssets() {
    assetsGroup.innerHTML = CITY.assets
      .map((a) => {
        const hidden = activeDomains.size && !activeDomains.has(a.domain);
        const selected = selectedId === a.id ? "is-selected" : "";
        const dim = selectedId && selectedId !== a.id ? "is-dim" : "";
        return `
        <g class="asset ${selected} ${dim} ${hidden ? "hidden" : ""}"
           data-id="${a.id}"
           data-domain="${a.domain}"
           data-status="${a.status}"
           tabindex="0"
           role="button"
           aria-label="${a.name}, ${a.status}">
          <circle class="asset__ring" cx="${a.x}" cy="${a.y}" r="3.4" />
          <circle class="asset__core" cx="${a.x}" cy="${a.y}" r="1.7" />
        </g>`;
      })
      .join("");

    assetsGroup.querySelectorAll(".asset").forEach((node) => {
      node.addEventListener("click", () => selectAsset(node.dataset.id));
      node.addEventListener("keydown", (e) => {
        if (e.key === "Enter" || e.key === " ") {
          e.preventDefault();
          selectAsset(node.dataset.id);
        }
      });
    });
  }

  function sparkline(values) {
    const w = 280;
    const h = 72;
    const pad = 6;
    const min = Math.min(...values);
    const max = Math.max(...values);
    const span = Math.max(max - min, 1);
    const coords = values.map((v, i) => {
      const x = pad + (i / (values.length - 1)) * (w - pad * 2);
      const y = h - pad - ((v - min) / span) * (h - pad * 2);
      return [x, y];
    });
    const line = coords.map((c, i) => `${i ? "L" : "M"}${c[0].toFixed(1)},${c[1].toFixed(1)}`).join(" ");
    const area = `${line} L${coords[coords.length - 1][0].toFixed(1)},${h} L${coords[0][0].toFixed(1)},${h} Z`;
    return { line, area, w, h };
  }

  function selectAsset(id) {
    selectedId = id;
    const asset = CITY.assets.find((a) => a.id === id);
    if (!asset) return;

    emptyState.classList.add("hidden");
    detailState.classList.remove("hidden");
    canvasHint.textContent = "Selected asset focused — inspect visual metrics on the right.";

    document.getElementById("detail-domain").textContent = asset.domain;
    document.getElementById("detail-title").textContent = asset.name;

    const status = document.getElementById("detail-status");
    status.dataset.status = asset.status;
    document.getElementById("detail-status-label").textContent = asset.status;
    document.getElementById("detail-status-meta").textContent = "Live feed · updated moments ago";

    document.getElementById("detail-hero-label").textContent = asset.hero.label;
    document.getElementById("detail-hero-value").innerHTML =
      `${asset.hero.value}<small>${asset.hero.unit}</small>`;

    const pct = Math.max(0, Math.min(100, (asset.hero.value / (asset.hero.max || 100)) * 100));
    const meter = document.getElementById("detail-meter");
    meter.style.width = pct + "%";
    meter.style.background = `linear-gradient(90deg, ${domainColor(asset.domain)}, ${domainColor(asset.domain)})`;

    document.getElementById("detail-supporting").innerHTML = asset.supporting
      .map(
        (s) => `
      <div>
        <dt>${s.label}</dt>
        <dd>${s.value}${s.unit ? ` ${s.unit}` : ""}</dd>
      </div>`
      )
      .join("");

    const spark = sparkline(asset.trend);
    document.getElementById("detail-trend").innerHTML = `
      <svg viewBox="0 0 ${spark.w} ${spark.h}" preserveAspectRatio="none" aria-hidden="true">
        <path class="area" d="${spark.area}"></path>
        <path class="line" d="${spark.line}"></path>
      </svg>`;

    document.getElementById("detail-events").innerHTML = asset.events
      .map((e) => `<li><time>${e.t}</time><span>${e.text}</span></li>`)
      .join("");

    renderAssets();
  }

  layers.forEach((btn) => {
    btn.addEventListener("click", () => {
      const on = btn.getAttribute("aria-pressed") === "true";
      btn.setAttribute("aria-pressed", on ? "false" : "true");
      activeDomains.clear();
      layers.forEach((b) => {
        if (b.getAttribute("aria-pressed") === "true") activeDomains.add(b.dataset.domain);
      });
      renderCorridors();
      renderAssets();
    });
  });

  renderDistricts();
  renderCorridors();
  renderAssets();
})();
