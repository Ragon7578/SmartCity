package com.urbanlens.smartcity.energy;

import com.urbanlens.smartcity.common.runtime.ModuleApiController;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/energy")
public class EnergyController extends ModuleApiController {

    public EnergyController(ModuleRuntimeService runtime) {
        super(runtime);
    }
}
