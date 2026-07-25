package com.urbanlens.smartcity.cityscene.client;

import com.urbanlens.smartcity.common.dto.AssetDetailDto;
import com.urbanlens.smartcity.common.dto.ModuleContributionDto;
import com.urbanlens.smartcity.common.dto.ModuleInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ModuleContributionClient {

    @GetMapping("/info")
    ModuleInfoDto info();

    @GetMapping("/visualization/contribution")
    ModuleContributionDto contribution();

    @GetMapping("/assets/{id}")
    AssetDetailDto asset(@PathVariable("id") String id);
}
