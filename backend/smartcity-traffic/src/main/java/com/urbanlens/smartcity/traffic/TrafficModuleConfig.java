package com.urbanlens.smartcity.traffic;

import com.urbanlens.smartcity.common.domain.CityModule;
import com.urbanlens.smartcity.common.runtime.ModuleRuntimeService;
import com.urbanlens.smartcity.common.runtime.ModuleStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrafficModuleConfig {

    @Bean
    ModuleStore moduleStore() {
        return new ModuleStore();
    }

    @Bean
    ModuleRuntimeService moduleRuntimeService(ModuleStore store) {
        return new ModuleRuntimeService(CityModule.TRAFFIC, store);
    }
}
