package com.urbanlens.smartcity.shopping;

import com.urbanlens.smartcity.common.runtime.ModuleApiController;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shopping")
public class ShoppingController extends ModuleApiController {

    public ShoppingController(ModuleRuntimeService runtime) {
        super(runtime);
    }
}
