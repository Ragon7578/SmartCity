package com.urbanlens.smartcity.traffic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.discovery.enabled=false"
})
class TrafficModuleTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void trafficModuleExposesAssetsAndContribution() throws Exception {
        mockMvc.perform(get("/api/v1/traffic/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.module", is("traffic")))
                .andExpect(jsonPath("$.assets", greaterThanOrEqualTo(1)));

        mockMvc.perform(get("/api/v1/traffic/visualization/contribution"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assets", hasSize(greaterThanOrEqualTo(1))));

        mockMvc.perform(get("/api/v1/traffic/assets/sig-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("sig-01")))
                .andExpect(jsonPath("$.module", is("traffic")));
    }
}
