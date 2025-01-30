package com.branch.demo.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        // todo: inject this base url
        return WebClient.builder().baseUrl("https://api.github.com/users").build();
    }

}
