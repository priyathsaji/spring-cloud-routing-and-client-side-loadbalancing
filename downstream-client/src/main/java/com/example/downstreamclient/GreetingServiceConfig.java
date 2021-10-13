package com.example.downstreamclient;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.List;

@Configuration
public class GreetingServiceConfig {
    private final DiscoveryClient discoveryClient;

    public GreetingServiceConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new GreetingInstanceSupplier(discoveryClient);
    }
}

class GreetingInstanceSupplier implements ServiceInstanceListSupplier {

    DiscoveryClient discoveryClient;

    public GreetingInstanceSupplier(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public String getServiceId() {
        return "greetings-service";
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(discoveryClient.getInstances("greetings-service"));
    }
}
