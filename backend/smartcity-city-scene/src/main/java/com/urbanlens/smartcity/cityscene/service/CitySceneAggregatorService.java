package com.urbanlens.smartcity.cityscene.service;

import com.urbanlens.smartcity.cityscene.client.EnergyModuleClient;
import com.urbanlens.smartcity.cityscene.client.EnvironmentModuleClient;
import com.urbanlens.smartcity.cityscene.client.FoodModuleClient;
import com.urbanlens.smartcity.cityscene.client.ModuleContributionClient;
import com.urbanlens.smartcity.cityscene.client.ParkingModuleClient;
import com.urbanlens.smartcity.cityscene.client.ShoppingModuleClient;
import com.urbanlens.smartcity.cityscene.client.TrafficModuleClient;
import com.urbanlens.smartcity.common.dto.AssetDetailDto;
import com.urbanlens.smartcity.common.dto.AssetSummaryDto;
import com.urbanlens.smartcity.common.dto.CitySceneDto;
import com.urbanlens.smartcity.common.dto.CorridorDto;
import com.urbanlens.smartcity.common.dto.DistrictDto;
import com.urbanlens.smartcity.common.dto.ModuleContributionDto;
import com.urbanlens.smartcity.common.dto.ModuleInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class CitySceneAggregatorService {

    private static final Logger log = LoggerFactory.getLogger(CitySceneAggregatorService.class);

    private final String cityName;
    private final List<NamedClient> clients;

    public CitySceneAggregatorService(
            @Value("${urbanlens.city.name:Harbor Ridge}") String cityName,
            TrafficModuleClient traffic,
            ParkingModuleClient parking,
            FoodModuleClient food,
            ShoppingModuleClient shopping,
            EnergyModuleClient energy,
            EnvironmentModuleClient environment
    ) {
        this.cityName = cityName;
        this.clients = List.of(
                new NamedClient("traffic", traffic),
                new NamedClient("parking", parking),
                new NamedClient("food", food),
                new NamedClient("shopping", shopping),
                new NamedClient("energy", energy),
                new NamedClient("environment", environment)
        );
    }

    public CitySceneDto scene(String moduleFilter) {
        Map<String, DistrictDto> districts = new LinkedHashMap<>();
        List<CorridorDto> corridors = new ArrayList<>();
        List<AssetSummaryDto> assets = new ArrayList<>();
        Set<String> modules = new LinkedHashSet<>();
        boolean filtering = moduleFilter != null && !moduleFilter.isBlank();

        for (NamedClient named : clients) {
            ModuleContributionDto contribution = safe(named.name(), named.client()::contribution);
            if (contribution == null) {
                continue;
            }
            modules.add(contribution.module());
            for (DistrictDto district : contribution.districts()) {
                districts.putIfAbsent(district.id(), district);
            }

            if (!filtering || contribution.module().equalsIgnoreCase(moduleFilter)) {
                corridors.addAll(contribution.corridors());
                assets.addAll(contribution.assets());
                continue;
            }

            List<AssetSummaryDto> filteredAssets = contribution.assets().stream()
                    .filter(a -> a.module().equalsIgnoreCase(moduleFilter)
                            || a.domain().equalsIgnoreCase(moduleFilter))
                    .toList();
            assets.addAll(filteredAssets);

            List<CorridorDto> filteredCorridors = contribution.corridors().stream()
                    .filter(c -> c.domain().equalsIgnoreCase(moduleFilter))
                    .toList();
            corridors.addAll(filteredCorridors);
        }

        return new CitySceneDto(
                cityName,
                List.copyOf(modules),
                List.copyOf(districts.values()),
                corridors,
                assets
        );
    }

    public AssetDetailDto asset(String id) {
        for (NamedClient named : clients) {
            AssetDetailDto detail = safe(named.name(), () -> named.client().asset(id));
            if (detail != null) {
                return detail;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found: " + id);
    }

    public List<ModuleInfoDto> modules() {
        List<ModuleInfoDto> infos = new ArrayList<>();
        for (NamedClient named : clients) {
            ModuleInfoDto info = safe(named.name(), named.client()::info);
            if (info != null) {
                infos.add(info);
            }
        }
        return infos;
    }

    public Map<String, Object> systemInfo() {
        CitySceneDto scene = scene(null);
        return Map.of(
                "service", "smartcity-city-scene",
                "city", cityName,
                "architecture", "spring-cloud-microservices",
                "modules", scene.modules(),
                "assets", scene.assets().size()
        );
    }

    private <T> T safe(String module, Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            log.warn("Module {} unavailable: {}", module, ex.getMessage());
            return null;
        }
    }

    private record NamedClient(String name, ModuleContributionClient client) {
    }
}
