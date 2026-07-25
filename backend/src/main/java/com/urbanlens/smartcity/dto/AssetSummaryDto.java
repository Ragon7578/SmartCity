package com.urbanlens.smartcity.dto;

public record AssetSummaryDto(
        String id,
        String name,
        String domain,
        double x,
        double y,
        String status
) {
}
