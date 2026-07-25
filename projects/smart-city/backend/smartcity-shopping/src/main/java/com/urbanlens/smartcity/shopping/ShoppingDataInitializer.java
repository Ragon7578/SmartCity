package com.urbanlens.smartcity.shopping;

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
public class ShoppingDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ShoppingDataInitializer.class);

    private final ModuleStore store;

    public ShoppingDataInitializer(ModuleStore store) {
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        store.clear();

        store.saveAsset(asset("shop-01", "Ridge Mall", "shopping", 76, 28, AssetStatus.ONLINE,
                MetricDto.hero("Footfall index", 67, "", 100),
                List.of(MetricDto.of("Open stores", 120, ""), MetricDto.of("Avg dwell", 48, "min")),
                List.of(40.0, 45.0, 50.0, 55.0, 60.0, 64.0, 67.0),
                List.of(new AssetEventDto("10:00", "Weekend promotion live"))));
        store.saveAsset(asset("shop-02", "Harbor Boutique Row", "shopping", 28, 60, AssetStatus.ONLINE,
                MetricDto.hero("Footfall index", 58, "", 100),
                List.of(MetricDto.of("Open stores", 34, ""), MetricDto.of("Avg dwell", 35, "min")),
                List.of(30.0, 35.0, 42.0, 48.0, 52.0, 55.0, 58.0),
                List.of(new AssetEventDto("09:30", "Street fair setup complete"))));

        log.info("{} module initialized with {} assets", CityModule.SHOPPING.getDisplayName(), store.assetCount());
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
        return new ManagedAsset(id, name, CityModule.SHOPPING, domain, x, y, status, hero, supporting, trend, events);
    }
}
