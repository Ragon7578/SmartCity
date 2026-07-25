package com.urbanlens.smartcity.cityscene.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "smartcity-food", path = "/api/v1/food")
public interface FoodModuleClient extends ModuleContributionClient {
}
