package com.urbanlens.smartcity.dto;

public record MetricDto(
        String label,
        double value,
        String unit,
        Double max
) {
}
