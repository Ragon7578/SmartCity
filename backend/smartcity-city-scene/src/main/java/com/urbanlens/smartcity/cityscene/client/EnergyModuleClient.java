package com.urbanlens.smartcity.cityscene.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "smartcity-energy", path = "/api/v1/energy")
public interface EnergyModuleClient extends ModuleContributionClient {
}
