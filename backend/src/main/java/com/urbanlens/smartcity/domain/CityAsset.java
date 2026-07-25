package com.urbanlens.smartcity.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CityAsset {

    private final String id;
    private final String name;
    private final AssetDomain domain;
    private final double x;
    private final double y;
    private AssetStatus status;
    private MetricValue hero;
    private List<MetricValue> supporting;
    private List<Double> trend;
    private List<AssetEvent> events;
    private Instant updatedAt;

    public CityAsset(
            String id,
            String name,
            AssetDomain domain,
            double x,
            double y,
            AssetStatus status,
            MetricValue hero,
            List<MetricValue> supporting,
            List<Double> trend,
            List<AssetEvent> events
    ) {
        this.id = id;
        this.name = name;
        this.domain = domain;
        this.x = x;
        this.y = y;
        this.status = status;
        this.hero = hero;
        this.supporting = new ArrayList<>(supporting);
        this.trend = new ArrayList<>(trend);
        this.events = new ArrayList<>(events);
        this.updatedAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AssetDomain getDomain() {
        return domain;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
        touch();
    }

    public MetricValue getHero() {
        return hero;
    }

    public void setHero(MetricValue hero) {
        this.hero = hero;
        touch();
    }

    public List<MetricValue> getSupporting() {
        return List.copyOf(supporting);
    }

    public void setSupporting(List<MetricValue> supporting) {
        this.supporting = new ArrayList<>(supporting);
        touch();
    }

    public List<Double> getTrend() {
        return List.copyOf(trend);
    }

    public void setTrend(List<Double> trend) {
        this.trend = new ArrayList<>(trend);
        touch();
    }

    public List<AssetEvent> getEvents() {
        return List.copyOf(events);
    }

    public void prependEvent(AssetEvent event) {
        this.events.add(0, event);
        if (this.events.size() > 8) {
            this.events = new ArrayList<>(this.events.subList(0, 8));
        }
        touch();
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }
}
