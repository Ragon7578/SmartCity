package com.urbanlens.smartcity.energy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.urbanlens.smartcity")
@EnableDiscoveryClient
@EnableScheduling
public class EnergyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyApplication.class, args);
    }
}
