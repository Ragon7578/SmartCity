package com.urbanlens.smartcity.dto;

import java.util.List;

public record AssetDetailDto(
        String id,
        String name,
        String domain,
        double x,
        double y,
        String status,
        MetricDto hero,
        List<MetricDto> supporting,
        List<Double> trend,
        List<AssetEventDto> events,
        String updatedAt
) {
}
