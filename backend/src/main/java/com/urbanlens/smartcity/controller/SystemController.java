package com.urbanlens.smartcity.controller;

import com.urbanlens.smartcity.service.AssetRegistryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    private final AssetRegistryService assetRegistryService;
    private final String cityName;

    public SystemController(
            AssetRegistryService assetRegistryService,
            @Value("${urbanlens.city.name:Harbor Ridge}") String cityName
    ) {
        this.assetRegistryService = assetRegistryService;
        this.cityName = cityName;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of(
                "service", "urban-lens-smart-city",
                "city", cityName,
                "assets", assetRegistryService.count(),
                "architecture", "visualize-then-display"
        );
    }
}
