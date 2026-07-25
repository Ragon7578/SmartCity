package com.urbanlens.smartcity.food;

import com.urbanlens.smartcity.common.runtime.ModuleApiController;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController extends ModuleApiController {

    public FoodController(ModuleRuntimeService runtime) {
        super(runtime);
    }
}
