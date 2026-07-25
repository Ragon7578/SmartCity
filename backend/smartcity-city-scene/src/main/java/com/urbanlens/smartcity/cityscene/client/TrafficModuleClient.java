package com.urbanlens.smartcity.cityscene.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "smartcity-traffic", path = "/api/v1/traffic")
public interface TrafficModuleClient extends ModuleContributionClient {
}
