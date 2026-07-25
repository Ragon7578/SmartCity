package com.urbanlens.smartcity.parking;

import com.urbanlens.smartcity.common.runtime.ModuleApiController;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController extends ModuleApiController {

    public ParkingController(ModuleRuntimeService runtime) {
        super(runtime);
    }
}
