package com.urbanlens.smartcity.cityscene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.urbanlens.smartcity")
@EnableDiscoveryClient
@EnableFeignClients
public class CitySceneApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitySceneApplication.class, args);
    }
}
