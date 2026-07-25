package com.urbanlens.smartcity.cityscene.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "smartcity-parking", path = "/api/v1/parking")
public interface ParkingModuleClient extends ModuleContributionClient {
}
