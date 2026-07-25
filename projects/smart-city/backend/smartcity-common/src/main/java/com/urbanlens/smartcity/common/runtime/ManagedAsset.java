package com.urbanlens.smartcity.common.runtime;

import com.urbanlens.smartcity.common.domain.AssetStatus;
import com.urbanlens.smartcity.common.domain.CityModule;
import com.urbanlens.smartcity.common.dto.AssetDetailDto;
import com.urbanlens.smartcity.common.dto.AssetEventDto;
import com.urbanlens.smartcity.common.dto.AssetSummaryDto;
import com.urbanlens.smartcity.common.dto.MetricDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ManagedAsset {

    private final String id;
    private final String name;
    private final CityModule module;
    private final String domain;
    private final double x;
    private final double y;
    private AssetStatus status;
    private MetricDto hero;
    private List<MetricDto> supporting;
    private List<Double> trend;
    private List<AssetEventDto> events;
    private Instant updatedAt;

    public ManagedAsset(
            String id,
            String name,
            CityModule module,
            String domain,
            double x,
            double y,
            AssetStatus status,
            MetricDto hero,
            List<MetricDto> supporting,
            List<Double> trend,
            List<AssetEventDto> events
    ) {
        this.id = id;
        this.name = name;
        this.module = module;
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

    public CityModule getModule() {
        return module;
    }

    public String getDomain() {
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

    public MetricDto getHero() {
        return hero;
    }

    public void setHero(MetricDto hero) {
        this.hero = hero;
        touch();
    }

    public List<MetricDto> getSupporting() {
        return List.copyOf(supporting);
    }

    public List<Double> getTrend() {
        return List.copyOf(trend);
    }

    public void setTrend(List<Double> trend) {
        this.trend = new ArrayList<>(trend);
        touch();
    }

    public List<AssetEventDto> getEvents() {
        return List.copyOf(events);
    }

    public void prependEvent(AssetEventDto event) {
        events.add(0, event);
        if (events.size() > 8) {
            events = new ArrayList<>(events.subList(0, 8));
        }
        touch();
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public AssetSummaryDto toSummary() {
        return new AssetSummaryDto(
                id,
                name,
                module.getApiValue(),
                domain,
                x,
                y,
                status.getApiValue()
        );
    }

    public AssetDetailDto toDetail() {
        return new AssetDetailDto(
                id,
                name,
                module.getApiValue(),
                domain,
                x,
                y,
                status.getApiValue(),
                hero,
                getSupporting(),
                getTrend(),
                getEvents(),
                updatedAt.toString()
        );
    }

    private void touch() {
        updatedAt = Instant.now();
    }
}
