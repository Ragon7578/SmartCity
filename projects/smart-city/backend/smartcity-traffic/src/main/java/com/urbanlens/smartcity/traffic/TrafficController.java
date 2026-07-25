package com.urbanlens.smartcity.traffic;

import com.urbanlens.smartcity.common.runtime.ModuleApiController;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/traffic")
public class TrafficController extends ModuleApiController {

    public TrafficController(ModuleRuntimeService runtime) {
        super(runtime);
    }
}
