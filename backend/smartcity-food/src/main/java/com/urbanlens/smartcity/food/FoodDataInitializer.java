package com.urbanlens.smartcity.food;

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
public class FoodDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(FoodDataInitializer.class);

    private final ModuleStore store;

    public FoodDataInitializer(ModuleStore store) {
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        store.clear();

        store.saveDistrict(new DistrictDto("garden", "Garden Quarters", 48, 62, 30, 20));
        store.saveAsset(asset("food-01", "Harbor Night Market", "food", 22, 70, AssetStatus.ONLINE,
                MetricDto.hero("Seat utilization", 72, "%", 100),
                List.of(MetricDto.of("Open stalls", 28, ""), MetricDto.of("Avg wait", 9, "min")),
                List.of(30.0, 40.0, 55.0, 60.0, 68.0, 70.0, 72.0),
                List.of(new AssetEventDto("11:20", "Lunch rush building"))));
        store.saveAsset(asset("food-02", "Civic Food Court", "food", 46, 42, AssetStatus.WARNING,
                MetricDto.hero("Seat utilization", 88, "%", 100),
                List.of(MetricDto.of("Open stalls", 12, ""), MetricDto.of("Avg wait", 16, "min")),
                List.of(50.0, 60.0, 70.0, 78.0, 82.0, 86.0, 88.0),
                List.of(new AssetEventDto("12:05", "Queue threshold reached"))));

        log.info("{} module initialized with {} assets", CityModule.FOOD.getDisplayName(), store.assetCount());
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
        return new ManagedAsset(id, name, CityModule.FOOD, domain, x, y, status, hero, supporting, trend, events);
    }
}
