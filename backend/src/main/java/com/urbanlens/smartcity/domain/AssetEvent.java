package com.urbanlens.smartcity.domain;

import java.time.Instant;

public record AssetEvent(
        Instant at,
        String displayTime,
        String text
) {
}
