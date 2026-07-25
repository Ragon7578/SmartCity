package com.urbanlens.smartcity.common.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Independently deployable smart-city business modules.
 */
public enum CityModule {
    TRAFFIC("traffic", "Traffic Management"),
    PARKING("parking", "Parking Management"),
    FOOD("food", "Food Management"),
    SHOPPING("shopping", "Shopping Management"),
    ENERGY("energy", "Energy Management"),
    ENVIRONMENT("environment", "Environment Management");

    private final String apiValue;
    private final String displayName;

    CityModule(String apiValue, String displayName) {
        this.apiValue = apiValue;
        this.displayName = displayName;
    }

    @JsonValue
    public String getApiValue() {
        return apiValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CityModule fromApiValue(String value) {
        for (CityModule module : values()) {
            if (module.apiValue.equalsIgnoreCase(value)) {
                return module;
            }
        }
        throw new IllegalArgumentException("Unknown module: " + value);
    }
}
