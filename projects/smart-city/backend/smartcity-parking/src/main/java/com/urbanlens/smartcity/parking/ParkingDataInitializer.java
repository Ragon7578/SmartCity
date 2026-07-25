package com.urbanlens.smartcity.parking;

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
public class ParkingDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ParkingDataInitializer.class);

    private final ModuleStore store;

    public ParkingDataInitializer(ModuleStore store) {
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        store.clear();

        store.saveDistrict(new DistrictDto("civic", "Civic Core", 38, 30, 26, 24));
        store.saveAsset(asset("park-02", "North Ridge Parking", "parking", 68, 18, AssetStatus.WARNING,
                MetricDto.hero("Occupancy", 91, "%", 100),
                List.of(MetricDto.of("Free stalls", 18, ""), MetricDto.of("Turnover", 1.4, "/h")),
                List.of(60.0, 68.0, 74.0, 80.0, 84.0, 87.0, 91.0),
                List.of(new AssetEventDto("07:15", "Lot nearly full"))));
        store.saveAsset(asset("park-05", "Harbor Deck Parking", "parking", 20, 64, AssetStatus.ONLINE,
                MetricDto.hero("Occupancy", 63, "%", 100),
                List.of(MetricDto.of("Free stalls", 74, ""), MetricDto.of("Turnover", 2.1, "/h")),
                List.of(40.0, 45.0, 50.0, 55.0, 58.0, 61.0, 63.0),
                List.of(new AssetEventDto("08:00", "Morning fill started"))));

        log.info("{} module initialized with {} assets", CityModule.PARKING.getDisplayName(), store.assetCount());
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
        return new ManagedAsset(id, name, CityModule.PARKING, domain, x, y, status, hero, supporting, trend, events);
    }
}
