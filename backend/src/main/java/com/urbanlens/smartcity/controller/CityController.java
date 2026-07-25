package com.urbanlens.smartcity.controller;

import com.urbanlens.smartcity.domain.AssetDomain;
import com.urbanlens.smartcity.dto.AssetDetailDto;
import com.urbanlens.smartcity.dto.CitySceneDto;
import com.urbanlens.smartcity.service.CitySceneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CityController {

    private final CitySceneService citySceneService;

    public CityController(CitySceneService citySceneService) {
        this.citySceneService = citySceneService;
    }

    @GetMapping("/city/scene")
    public CitySceneDto scene(@RequestParam(required = false) String domain) {
        AssetDomain filter = domain == null || domain.isBlank()
                ? null
                : AssetDomain.fromApiValue(domain);
        return citySceneService.getScene(filter);
    }

    @GetMapping("/assets/{id}")
    public AssetDetailDto asset(@PathVariable String id) {
        return citySceneService.getAssetDetail(id);
    }
}
