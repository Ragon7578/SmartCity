package com.urbanlens.smartcity.food;

import com.urbanlens.smartcity.common.domain.CityModule;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import com.urbanlens.smartcity.common.runtime.ModuleStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodModuleConfig {

    @Bean
    ModuleStore moduleStore() {
        return new ModuleStore();
    }

    @Bean
    ModuleRuntimeService moduleRuntimeService(ModuleStore store) {
        return new ModuleRuntimeService(CityModule.FOOD, store);
    }
}
