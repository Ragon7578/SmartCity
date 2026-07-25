package com.urbanlens.smartcity.common.dto;

import java.util.List;

/**
 * Scene fragment contributed by one independently expandable module.
 */
public record ModuleContributionDto(
        String module,
        String displayName,
        List<DistrictDto> districts,
        List<CorridorDto> corridors,
        List<AssetSummaryDto> assets
) {
}
