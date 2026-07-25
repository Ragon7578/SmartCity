package com.urbanlens.smartcity.common.dto;

public record AssetSummaryDto(
        String id,
        String name,
        String module,
        String domain,
        double x,
        double y,
        String status
) {
}
