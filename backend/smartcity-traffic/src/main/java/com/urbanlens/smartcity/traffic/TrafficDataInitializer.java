package com.urbanlens.smartcity.traffic;

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
public class TrafficDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TrafficDataInitializer.class);

    private final ModuleStore store;

    public TrafficDataInitializer(ModuleStore store) {
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        store.clear();

        store.saveDistrict(new DistrictDto("harbor", "Harbor Rim", 12, 58, 28, 22));
        store.saveDistrict(new DistrictDto("north", "North Ridge", 62, 12, 24, 26));
        store.saveCorridor(new CorridorDto("t-c1", "traffic", "M 8 70 C 30 68, 40 50, 52 42 C 64 34, 78 28, 92 22"));
        store.saveCorridor(new CorridorDto("t-c2", "traffic", "M 20 20 C 36 36, 44 48, 58 72 C 64 82, 72 86, 88 88"));
        store.saveAsset(asset("sig-01", "Harbor & 3rd Signal", "traffic", 24, 66, AssetStatus.WARNING,
                MetricDto.hero("Congestion index", 78, "", 100),
                List.of(MetricDto.of("Throughput", 1200, "/h"), MetricDto.of("Incidents", 2, "open")),
                List.of(42.0, 48.0, 55.0, 61.0, 70.0, 74.0, 78.0),
                List.of(new AssetEventDto("03:12", "Queue spillback on west approach"))));
        store.saveAsset(asset("cam-tr-01", "Ridge Approach Camera", "traffic", 70, 20, AssetStatus.ONLINE,
                MetricDto.hero("Congestion index", 54, "", 100),
                List.of(MetricDto.of("Throughput", 860, "/h"), MetricDto.of("Incidents", 0, "open")),
                List.of(40.0, 44.0, 48.0, 50.0, 52.0, 53.0, 54.0),
                List.of(new AssetEventDto("06:10", "Flow stable"))));

        log.info("{} module initialized with {} assets", CityModule.TRAFFIC.getDisplayName(), store.assetCount());
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
        return new ManagedAsset(id, name, CityModule.TRAFFIC, domain, x, y, status, hero, supporting, trend, events);
    }
}
