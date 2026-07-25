package com.urbanlens.smartcity.dto;

import java.util.List;

public record CitySceneDto(
        String cityName,
        List<DistrictDto> districts,
        List<CorridorDto> corridors,
        List<AssetSummaryDto> assets
) {
}
