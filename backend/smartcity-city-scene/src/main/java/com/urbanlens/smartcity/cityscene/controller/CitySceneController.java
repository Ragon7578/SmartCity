package com.urbanlens.smartcity.cityscene.controller;

import com.urbanlens.smartcity.cityscene.service.CitySceneAggregatorService;
import com.urbanlens.smartcity.common.dto.AssetDetailDto;
import com.urbanlens.smartcity.common.dto.CitySceneDto;
import com.urbanlens.smartcity.common.dto.ModuleInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CitySceneController {

    private final CitySceneAggregatorService aggregatorService;

    public CitySceneController(CitySceneAggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/city/scene")
    public CitySceneDto scene(@RequestParam(required = false) String domain) {
        return aggregatorService.scene(domain);
    }

    @GetMapping("/assets/{id}")
    public AssetDetailDto asset(@PathVariable String id) {
        return aggregatorService.asset(id);
    }

    @GetMapping("/modules")
    public List<ModuleInfoDto> modules() {
        return aggregatorService.modules();
    }

    @GetMapping("/system/info")
    public Map<String, Object> systemInfo() {
        return aggregatorService.systemInfo();
    }
}
