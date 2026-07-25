package com.urbanlens.smartcity.domain;

public record MetricValue(
        String label,
        double value,
        String unit,
        Double max
) {
    public static MetricValue of(String label, double value, String unit) {
        return new MetricValue(label, value, unit, null);
    }

    public static MetricValue hero(String label, double value, String unit, double max) {
        return new MetricValue(label, value, unit, max);
    }
}
