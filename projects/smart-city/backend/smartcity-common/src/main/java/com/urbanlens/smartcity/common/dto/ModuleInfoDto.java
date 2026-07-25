package com.urbanlens.smartcity.common.dto;

public record ModuleInfoDto(
        String service,
        String module,
        String displayName,
        int assets,
        String architecture
) {
}
