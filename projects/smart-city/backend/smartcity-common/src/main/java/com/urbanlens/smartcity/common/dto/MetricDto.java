package com.urbanlens.smartcity.common.dto;

public record MetricDto(
        String label,
        double value,
        String unit,
        Double max
) {
    public static MetricDto hero(String label, double value, String unit, double max) {
        return new MetricDto(label, value, unit, max);
    }

    public static MetricDto of(String label, double value, String unit) {
        return new MetricDto(label, value, unit, null);
    }
}
