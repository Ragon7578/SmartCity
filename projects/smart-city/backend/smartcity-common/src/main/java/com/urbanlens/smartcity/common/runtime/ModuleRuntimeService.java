package com.urbanlens.smartcity.common.runtime;

import com.urbanlens.smartcity.common.domain.AssetStatus;
import com.urbanlens.smartcity.common.domain.CityModule;
import com.urbanlens.smartcity.common.dto.AssetDetailDto;
import com.urbanlens.smartcity.common.dto.AssetEventDto;
import com.urbanlens.smartcity.common.dto.AssetSummaryDto;
import com.urbanlens.smartcity.common.dto.MetricDto;
import com.urbanlens.smartcity.common.dto.ModuleContributionDto;
import com.urbanlens.smartcity.common.dto.ModuleInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Shared runtime for independently expandable domain modules.
 */
public class ModuleRuntimeService {

    private static final DateTimeFormatter CLOCK =
            DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("Asia/Shanghai"));

    private final CityModule module;
    private final ModuleStore store;
    private final String serviceName;

    public ModuleRuntimeService(CityModule module, ModuleStore store) {
        this.module = module;
        this.store = store;
        this.serviceName = "smartcity-" + module.getApiValue();
    }

    public CityModule module() {
        return module;
    }

    public ModuleStore store() {
        return store;
    }

    public ModuleInfoDto info() {
        return new ModuleInfoDto(
                serviceName,
                module.getApiValue(),
                module.getDisplayName(),
                store.assetCount(),
                "spring-cloud-microservice"
        );
    }

    public ModuleContributionDto contribution() {
        List<AssetSummaryDto> assets = store.assets().stream().map(ManagedAsset::toSummary).toList();
        return new ModuleContributionDto(
                module.getApiValue(),
                module.getDisplayName(),
                store.districts(),
                store.corridors(),
                assets
        );
    }

    public List<AssetSummaryDto> listAssets() {
        return store.assets().stream().map(ManagedAsset::toSummary).toList();
    }

    public AssetDetailDto getAsset(String id) {
        return store.findAsset(id)
                .map(ManagedAsset::toDetail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found: " + id));
    }

    @Scheduled(fixedDelayString = "${urbanlens.telemetry.tick-ms:10000}")
    public void telemetryTick() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (ManagedAsset asset : store.mutableAssets()) {
            nudge(asset, random);
        }
    }

    private void nudge(ManagedAsset asset, ThreadLocalRandom random) {
        MetricDto hero = asset.getHero();
        if (hero == null || hero.max() == null) {
            return;
        }
        double next = clamp(hero.value() + random.nextDouble(-3.0, 3.0), 0, hero.max());
        next = Math.round(next * 10.0) / 10.0;
        asset.setHero(new MetricDto(hero.label(), next, hero.unit(), hero.max()));

        List<Double> trend = new ArrayList<>(asset.getTrend());
        trend.add(next);
        if (trend.size() > 12) {
            trend = new ArrayList<>(trend.subList(trend.size() - 12, trend.size()));
        }
        asset.setTrend(trend);
        asset.setStatus(deriveStatus(next, hero.max()));

        if (random.nextDouble() < 0.1) {
            Instant now = Instant.now();
            asset.prependEvent(new AssetEventDto(CLOCK.format(now), asset.getName() + " telemetry refreshed"));
        }
    }

    private AssetStatus deriveStatus(double value, double max) {
        double ratio = value / max;
        if (ratio >= 0.9) {
            return AssetStatus.CRITICAL;
        }
        if (ratio >= 0.75) {
            return AssetStatus.WARNING;
        }
        return AssetStatus.ONLINE;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
