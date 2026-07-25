package com.urbanlens.smartcity.bootstrap;

import com.urbanlens.smartcity.domain.AssetDomain;
import com.urbanlens.smartcity.domain.AssetEvent;
import com.urbanlens.smartcity.domain.AssetStatus;
import com.urbanlens.smartcity.domain.CityAsset;
import com.urbanlens.smartcity.domain.Corridor;
import com.urbanlens.smartcity.domain.District;
import com.urbanlens.smartcity.domain.MetricValue;
import com.urbanlens.smartcity.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Seeds the in-memory city model when the service starts.
 */
@Component
public class CityDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CityDataInitializer.class);

    private final CityRepository cityRepository;

    public CityDataInitializer(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        cityRepository.clear();
        seedDistricts();
        seedCorridors();
        seedAssets();
        log.info("Urban Lens city model initialized: {} assets", cityRepository.assetCount());
    }

    private void seedDistricts() {
        cityRepository.saveDistrict(new District("harbor", "Harbor Rim", 12, 58, 28, 22));
        cityRepository.saveDistrict(new District("civic", "Civic Core", 38, 30, 26, 24));
        cityRepository.saveDistrict(new District("north", "North Ridge", 62, 12, 24, 26));
        cityRepository.saveDistrict(new District("garden", "Garden Quarters", 48, 62, 30, 20));
    }

    private void seedCorridors() {
        cityRepository.saveCorridor(new Corridor(
                "c1",
                AssetDomain.TRAFFIC,
                "M 8 70 C 30 68, 40 50, 52 42 C 64 34, 78 28, 92 22"
        ));
        cityRepository.saveCorridor(new Corridor(
                "c2",
                AssetDomain.TRAFFIC,
                "M 20 20 C 36 36, 44 48, 58 72 C 64 82, 72 86, 88 88"
        ));
        cityRepository.saveCorridor(new Corridor(
                "c3",
                AssetDomain.ENERGY,
                "M 14 48 C 28 44, 50 40, 70 36 C 80 34, 86 50, 90 64"
        ));
        cityRepository.saveCorridor(new Corridor(
                "c4",
                AssetDomain.ENERGY,
                "M 40 16 C 48 28, 52 50, 56 78"
        ));
    }

    private void seedAssets() {
        cityRepository.saveAsset(asset(
                "sig-01", "Harbor & 3rd Signal", AssetDomain.TRAFFIC, 24, 66, AssetStatus.WARNING,
                MetricValue.hero("Congestion index", 78, "", 100),
                List.of(
                        MetricValue.of("Throughput", 1200, "/h"),
                        MetricValue.of("Incidents", 2, "open")
                ),
                List.of(42.0, 48.0, 55.0, 61.0, 70.0, 74.0, 78.0, 76.0, 78.0),
                List.of(
                        event("03:12", "Queue spillback on west approach"),
                        event("02:48", "Phase timing adjusted (+4s green)"),
                        event("01:05", "Camera feed healthy")
                )
        ));

        cityRepository.saveAsset(asset(
                "cam-12", "Civic Plaza Camera", AssetDomain.SAFETY, 50, 40, AssetStatus.ONLINE,
                MetricValue.hero("Device health", 99, "%", 100),
                List.of(
                        MetricValue.of("Open incidents", 0, ""),
                        MetricValue.of("Avg response", 4.1, "min")
                ),
                List.of(97.0, 98.0, 98.0, 99.0, 99.0, 99.0, 98.0, 99.0, 99.0),
                List.of(
                        event("Yesterday", "Firmware check OK"),
                        event("Mon", "Lens cleaned (scheduled)")
                )
        ));

        cityRepository.saveAsset(asset(
                "aqi-07", "Garden Air Station", AssetDomain.ENVIRONMENT, 62, 72, AssetStatus.ONLINE,
                MetricValue.hero("AQI", 42, "", 150),
                List.of(
                        MetricValue.of("PM2.5", 12, "µg/m³"),
                        MetricValue.of("Noise", 54, "dB")
                ),
                List.of(55.0, 51.0, 48.0, 46.0, 44.0, 43.0, 42.0, 41.0, 42.0),
                List.of(
                        event("04:00", "Morning dip after wind shift"),
                        event("22:10", "Night baseline restored")
                )
        ));

        cityRepository.saveAsset(asset(
                "sub-03", "Ridge Substation", AssetDomain.ENERGY, 74, 24, AssetStatus.CRITICAL,
                MetricValue.hero("Load vs capacity", 93, "%", 100),
                List.of(
                        MetricValue.of("Outage minutes", 18, "today"),
                        MetricValue.of("Renewable share", 31, "%")
                ),
                List.of(68.0, 72.0, 75.0, 80.0, 84.0, 88.0, 91.0, 93.0, 93.0),
                List.of(
                        event("05:41", "Transformer T2 thermal warning"),
                        event("05:20", "Load shed recommendation issued"),
                        event("04:55", "Peak ramp began")
                )
        ));

        cityRepository.saveAsset(asset(
                "ev-04", "Waterfront EV Hub", AssetDomain.ENERGY, 18, 62, AssetStatus.ONLINE,
                MetricValue.hero("Load vs capacity", 61, "%", 100),
                List.of(
                        MetricValue.of("Active stalls", 14, "of 22"),
                        MetricValue.of("Avg session", 38, "min")
                ),
                List.of(40.0, 44.0, 48.0, 52.0, 58.0, 60.0, 61.0, 59.0, 61.0),
                List.of(
                        event("06:02", "Two stalls freed"),
                        event("05:30", "Peak commute charging")
                )
        ));

        cityRepository.saveAsset(asset(
                "lib-01", "Central Library", AssetDomain.CIVIC, 44, 36, AssetStatus.ONLINE,
                MetricValue.hero("Service availability", 100, "%", 100),
                List.of(
                        MetricValue.of("Queue length", 6, "people"),
                        MetricValue.of("Visits today", 840, "")
                ),
                List.of(20.0, 35.0, 55.0, 70.0, 85.0, 90.0, 95.0, 98.0, 100.0),
                List.of(
                        event("08:00", "Doors open — systems green"),
                        event("Yesterday", "HVAC filter replaced")
                )
        ));

        cityRepository.saveAsset(asset(
                "park-02", "North Ridge Parking", AssetDomain.TRAFFIC, 68, 18, AssetStatus.WARNING,
                MetricValue.hero("Congestion index", 86, "", 100),
                List.of(
                        MetricValue.of("Occupancy", 91, "%"),
                        MetricValue.of("Turnover", 1.4, "/h")
                ),
                List.of(60.0, 68.0, 74.0, 80.0, 84.0, 87.0, 86.0, 88.0, 86.0),
                List.of(
                        event("07:15", "Lot nearly full"),
                        event("06:40", "Inbound surge from ridge commute")
                )
        ));

        cityRepository.saveAsset(asset(
                "flood-01", "Canal Gauge West", AssetDomain.ENVIRONMENT, 30, 78, AssetStatus.ONLINE,
                MetricValue.hero("AQI", 38, "proxy", 150),
                List.of(
                        MetricValue.of("Water level", 1.2, "m"),
                        MetricValue.of("Trend", 0, "stable")
                ),
                List.of(40.0, 39.0, 39.0, 38.0, 38.0, 37.0, 38.0, 38.0, 38.0),
                List.of(event("Tide +2h", "Within normal band"))
        ));
    }

    private static CityAsset asset(
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
        return new CityAsset(id, name, domain, x, y, status, hero, supporting, trend, events);
    }

    private static AssetEvent event(String displayTime, String text) {
        return new AssetEvent(Instant.now(), displayTime, text);
    }
}
