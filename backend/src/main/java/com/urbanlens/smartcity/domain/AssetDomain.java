package com.urbanlens.smartcity.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AssetDomain {
    TRAFFIC("traffic"),
    ENERGY("energy"),
    ENVIRONMENT("environment"),
    SAFETY("safety"),
    CIVIC("civic");

    private final String apiValue;

    AssetDomain(String apiValue) {
        this.apiValue = apiValue;
    }

    @JsonValue
    public String getApiValue() {
        return apiValue;
    }

    public static AssetDomain fromApiValue(String value) {
        for (AssetDomain domain : values()) {
            if (domain.apiValue.equalsIgnoreCase(value)) {
                return domain;
            }
        }
        throw new IllegalArgumentException("Unknown domain: " + value);
    }
}
