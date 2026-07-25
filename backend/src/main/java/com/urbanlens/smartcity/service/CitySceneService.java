package com.urbanlens.smartcity.service;

import com.urbanlens.smartcity.domain.AssetDomain;
import com.urbanlens.smartcity.domain.CityAsset;
import com.urbanlens.smartcity.dto.AssetDetailDto;
import com.urbanlens.smartcity.dto.AssetEventDto;
import com.urbanlens.smartcity.dto.AssetSummaryDto;
import com.urbanlens.smartcity.dto.CitySceneDto;
import com.urbanlens.smartcity.dto.CorridorDto;
import com.urbanlens.smartcity.dto.DistrictDto;
import com.urbanlens.smartcity.dto.MetricDto;
import com.urbanlens.smartcity.repository.CityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Builds the visualization scene and asset detail payloads for the UI.
 */
@Service
public class CitySceneService {

    private final CityRepository cityRepository;
    private final String cityName;

    public CitySceneService(
            CityRepository cityRepository,
            @Value("${urbanlens.city.name:Harbor Ridge}") String cityName
    ) {
        this.cityRepository = cityRepository;
        this.cityName = cityName;
    }

    public CitySceneDto getScene(AssetDomain domainFilter) {
        List<AssetSummaryDto> assets = cityRepository.findAllAssets().stream()
                .filter(asset -> domainFilter == null || asset.getDomain() == domainFilter)
                .map(this::toSummary)
                .toList();

        List<CorridorDto> corridors = cityRepository.findAllCorridors().stream()
                .filter(corridor -> domainFilter == null || corridor.domain() == domainFilter)
                .map(c -> new CorridorDto(c.id(), c.domain().getApiValue(), c.path()))
                .toList();

        List<DistrictDto> districts = cityRepository.findAllDistricts().stream()
                .map(d -> new DistrictDto(d.id(), d.name(), d.x(), d.y(), d.w(), d.h()))
                .toList();

        return new CitySceneDto(cityName, districts, corridors, assets);
    }

    public AssetDetailDto getAssetDetail(String id) {
        CityAsset asset = cityRepository.findAssetById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found: " + id));
        return toDetail(asset);
    }

    private AssetSummaryDto toSummary(CityAsset asset) {
        return new AssetSummaryDto(
                asset.getId(),
                asset.getName(),
                asset.getDomain().getApiValue(),
                asset.getX(),
                asset.getY(),
                asset.getStatus().getApiValue()
        );
    }

    private AssetDetailDto toDetail(CityAsset asset) {
        MetricDto hero = new MetricDto(
                asset.getHero().label(),
                asset.getHero().value(),
                asset.getHero().unit(),
                asset.getHero().max()
        );

        List<MetricDto> supporting = asset.getSupporting().stream()
                .map(m -> new MetricDto(m.label(), m.value(), m.unit(), m.max()))
                .toList();

        List<AssetEventDto> events = asset.getEvents().stream()
                .map(e -> new AssetEventDto(e.displayTime(), e.text()))
                .toList();

        return new AssetDetailDto(
                asset.getId(),
                asset.getName(),
                asset.getDomain().getApiValue(),
                asset.getX(),
                asset.getY(),
                asset.getStatus().getApiValue(),
                hero,
                supporting,
                asset.getTrend(),
                events,
                asset.getUpdatedAt().toString()
        );
    }
}
