package com.urbanlens.smartcity.service;

import com.urbanlens.smartcity.domain.AssetEvent;
import com.urbanlens.smartcity.domain.AssetStatus;
import com.urbanlens.smartcity.domain.CityAsset;
import com.urbanlens.smartcity.domain.MetricValue;
import com.urbanlens.smartcity.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Binds simulated telemetry onto registered assets and refreshes visual status.
 */
@Service
public class DataFusionService {

    private static final Logger log = LoggerFactory.getLogger(DataFusionService.class);
    private static final DateTimeFormatter CLOCK =
            DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("Asia/Shanghai"));

    private final CityRepository cityRepository;

    public DataFusionService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Scheduled(fixedDelayString = "${urbanlens.telemetry.tick-ms:8000}")
    public void tick() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (CityAsset asset : cityRepository.mutableAssets()) {
            nudgeAsset(asset, random);
        }
        log.debug("Telemetry tick applied to {} assets", cityRepository.assetCount());
    }

    private void nudgeAsset(CityAsset asset, ThreadLocalRandom random) {
        MetricValue hero = asset.getHero();
        if (hero == null || hero.max() == null) {
            return;
        }

        double delta = random.nextDouble(-3.5, 3.5);
        double next = clamp(hero.value() + delta, 0, hero.max());
        asset.setHero(new MetricValue(hero.label(), round(next), hero.unit(), hero.max()));

        List<Double> trend = new ArrayList<>(asset.getTrend());
        trend.add(round(next));
        if (trend.size() > 12) {
            trend = new ArrayList<>(trend.subList(trend.size() - 12, trend.size()));
        }
        asset.setTrend(trend);
        asset.setStatus(deriveStatus(asset, next));

        if (random.nextDouble() < 0.12) {
            Instant now = Instant.now();
            asset.prependEvent(new AssetEvent(
                    now,
                    CLOCK.format(now),
                    statusMessage(asset.getStatus(), asset.getName())
            ));
        }
    }

    private AssetStatus deriveStatus(CityAsset asset, double heroValue) {
        return switch (asset.getDomain()) {
            case ENERGY, TRAFFIC -> {
                if (heroValue >= 90) {
                    yield AssetStatus.CRITICAL;
                }
                if (heroValue >= 75) {
                    yield AssetStatus.WARNING;
                }
                yield AssetStatus.ONLINE;
            }
            case ENVIRONMENT -> {
                if (heroValue >= 100) {
                    yield AssetStatus.CRITICAL;
                }
                if (heroValue >= 70) {
                    yield AssetStatus.WARNING;
                }
                yield AssetStatus.ONLINE;
            }
            case SAFETY, CIVIC -> {
                if (heroValue < 70) {
                    yield AssetStatus.WARNING;
                }
                yield AssetStatus.ONLINE;
            }
        };
    }

    private String statusMessage(AssetStatus status, String name) {
        return switch (status) {
            case CRITICAL -> "Escalation: " + name + " exceeded threshold";
            case WARNING -> "Watch: " + name + " trending warm";
            case OFFLINE -> "Feed lost for " + name;
            case ONLINE -> name + " within normal band";
        };
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
