package com.urbanlens.smartcity.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StaticRoutingConfig {

    @Bean
    RouterFunction<ServerResponse> staticFrontend() {
        return RouterFunctions.route()
                .GET("/", request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .bodyValue(new ClassPathResource("static/index.html")))
                .resources("/css/**", new ClassPathResource("static/css/"))
                .resources("/js/**", new ClassPathResource("static/js/"))
                .resources("/assets/**", new ClassPathResource("static/assets/"))
                .GET("/index.html", request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .bodyValue(new ClassPathResource("static/index.html")))
                .build();
    }
}
