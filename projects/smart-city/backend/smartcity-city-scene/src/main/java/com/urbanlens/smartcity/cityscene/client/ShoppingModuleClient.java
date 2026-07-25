package com.urbanlens.smartcity.cityscene.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "smartcity-shopping", path = "/api/v1/shopping")
public interface ShoppingModuleClient extends ModuleContributionClient {
}
