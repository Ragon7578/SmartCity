package com.urbanlens.smartcity.common.runtime;

import com.urbanlens.smartcity.common.dto.AssetDetailDto;
import com.urbanlens.smartcity.common.dto.AssetSummaryDto;
import com.urbanlens.smartcity.common.dto.ModuleContributionDto;
import com.urbanlens.smartcity.common.dto.ModuleInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Standard REST surface every domain module exposes for expansion and aggregation.
 */
public abstract class ModuleApiController {

    private final ModuleRuntimeService runtime;

    protected ModuleApiController(ModuleRuntimeService runtime) {
        this.runtime = runtime;
    }

    @GetMapping("/info")
    public ModuleInfoDto info() {
        return runtime.info();
    }

    @GetMapping("/visualization/contribution")
    public ModuleContributionDto contribution() {
        return runtime.contribution();
    }

    @GetMapping("/assets")
    public List<AssetSummaryDto> assets() {
        return runtime.listAssets();
    }

    @GetMapping("/assets/{id}")
    public AssetDetailDto asset(@PathVariable String id) {
        return runtime.getAsset(id);
    }
}
