package com.urbanlens.smartcity.environment;

import com.urbanlens.smartcity.common.domain.AssetStatus;
import com.urbanlens.smartcity.common.domain.CityModule;
import com.urbanlens.smartcity.common.dto.AssetEventDto;
import com.urbanlens.smartcity.common.dto.CorridorDto;
import com.urbanlens.smartcity.common.dto.DistrictDto;
import com.urbanlens.smartcity.common.dto.MetricDto;
import com.urbanlens.smartcity.common.runtime.ManagedAsset;
import com.urbanlens.smartcity.common.runtime.ModuleStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnvironmentDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentDataInitializer.class);

    private final ModuleStore store;

    public EnvironmentDataInitializer(ModuleStore store) {
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        store.clear();

        store.saveAsset(asset("aqi-07", "Garden Air Station", "environment", 62, 72, AssetStatus.ONLINE,
                MetricDto.hero("AQI", 42, "", 150),
                List.of(MetricDto.of("PM2.5", 12, "µg/m³"), MetricDto.of("Noise", 54, "dB")),
                List.of(55.0, 51.0, 48.0, 46.0, 44.0, 43.0, 42.0),
                List.of(new AssetEventDto("04:00", "Morning dip after wind shift"))));
        store.saveAsset(asset("flood-01", "Canal Gauge West", "environment", 30, 78, AssetStatus.ONLINE,
                MetricDto.hero("Water risk index", 38, "", 150),
                List.of(MetricDto.of("Water level", 1.2, "m"), MetricDto.of("Trend", 0, "stable")),
                List.of(40.0, 39.0, 39.0, 38.0, 38.0, 37.0, 38.0),
                List.of(new AssetEventDto("Tide +2h", "Within normal band"))));

        log.info("{} module initialized with {} assets", CityModule.ENVIRONMENT.getDisplayName(), store.assetCount());
    }

    private ManagedAsset asset(
            String id,
            String name,
            String domain,
            double x,
            double y,
            AssetStatus status,
            MetricDto hero,
            List<MetricDto> supporting,
            List<Double> trend,
            List<AssetEventDto> events
    ) {
        return new ManagedAsset(id, name, CityModule.ENVIRONMENT, domain, x, y, status, hero, supporting, trend, events);
    }
}
