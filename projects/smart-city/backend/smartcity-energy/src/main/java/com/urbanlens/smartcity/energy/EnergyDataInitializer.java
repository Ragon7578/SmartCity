package com.urbanlens.smartcity.energy;

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
public class EnergyDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(EnergyDataInitializer.class);

    private final ModuleStore store;

    public EnergyDataInitializer(ModuleStore store) {
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        store.clear();

        store.saveCorridor(new CorridorDto("e-c1", "energy", "M 14 48 C 28 44, 50 40, 70 36 C 80 34, 86 50, 90 64"));
        store.saveCorridor(new CorridorDto("e-c2", "energy", "M 40 16 C 48 28, 52 50, 56 78"));
        store.saveAsset(asset("sub-03", "Ridge Substation", "energy", 74, 24, AssetStatus.CRITICAL,
                MetricDto.hero("Load vs capacity", 93, "%", 100),
                List.of(MetricDto.of("Outage minutes", 18, "today"), MetricDto.of("Renewable share", 31, "%")),
                List.of(68.0, 72.0, 75.0, 80.0, 84.0, 88.0, 93.0),
                List.of(new AssetEventDto("05:41", "Transformer T2 thermal warning"))));
        store.saveAsset(asset("ev-04", "Waterfront EV Hub", "energy", 18, 62, AssetStatus.ONLINE,
                MetricDto.hero("Load vs capacity", 61, "%", 100),
                List.of(MetricDto.of("Active stalls", 14, "of 22"), MetricDto.of("Avg session", 38, "min")),
                List.of(40.0, 44.0, 48.0, 52.0, 58.0, 60.0, 61.0),
                List.of(new AssetEventDto("06:02", "Two stalls freed"))));

        log.info("{} module initialized with {} assets", CityModule.ENERGY.getDisplayName(), store.assetCount());
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
        return new ManagedAsset(id, name, CityModule.ENERGY, domain, x, y, status, hero, supporting, trend, events);
    }
}
