package com.urbanlens.smartcity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sceneEndpointReturnsInitializedCity() throws Exception {
        mockMvc.perform(get("/api/v1/city/scene"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName", is("Harbor Ridge")))
                .andExpect(jsonPath("$.districts", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.assets", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void assetDetailEndpointReturnsVisualMetrics() throws Exception {
        mockMvc.perform(get("/api/v1/assets/sub-03"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("sub-03")))
                .andExpect(jsonPath("$.domain", is("energy")))
                .andExpect(jsonPath("$.hero.label").exists())
                .andExpect(jsonPath("$.trend", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void unknownAssetReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/assets/does-not-exist"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void systemInfoExposesServiceIdentity() throws Exception {
        mockMvc.perform(get("/api/v1/system/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("urban-lens-smart-city")))
                .andExpect(jsonPath("$.assets", greaterThanOrEqualTo(1)));
    }
}
