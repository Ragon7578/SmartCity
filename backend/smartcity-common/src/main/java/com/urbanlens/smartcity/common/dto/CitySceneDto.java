package com.urbanlens.smartcity.common.dto;

import java.util.List;

public record CitySceneDto(
        String cityName,
        List<String> modules,
        List<DistrictDto> districts,
        List<CorridorDto> corridors,
        List<AssetSummaryDto> assets
) {
}
