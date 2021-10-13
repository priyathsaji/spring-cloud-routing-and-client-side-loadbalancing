package com.example.downstreamclient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClients({
        @LoadBalancerClient(name="greetings-service", configuration = GreetingServiceConfig.class)
})
public class WebClientConfiguration {

    @Bean
    @LoadBalanced
    WebClient.Builder webClient() {
        return WebClient.builder();
    }
}
