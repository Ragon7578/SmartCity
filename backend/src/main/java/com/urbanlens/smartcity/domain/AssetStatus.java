package com.urbanlens.smartcity.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AssetStatus {
    ONLINE("online"),
    WARNING("warning"),
    CRITICAL("critical"),
    OFFLINE("offline");

    private final String apiValue;

    AssetStatus(String apiValue) {
        this.apiValue = apiValue;
    }

    @JsonValue
    public String getApiValue() {
        return apiValue;
    }
}
