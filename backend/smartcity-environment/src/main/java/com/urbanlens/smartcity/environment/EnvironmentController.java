package com.urbanlens.smartcity.environment;

import com.urbanlens.smartcity.common.runtime.ModuleApiController;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/environment")
public class EnvironmentController extends ModuleApiController {

    public EnvironmentController(ModuleRuntimeService runtime) {
        super(runtime);
    }
}
