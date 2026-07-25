package com.urbanlens.smartcity.cityscene.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "smartcity-environment", path = "/api/v1/environment")
public interface EnvironmentModuleClient extends ModuleContributionClient {
}
