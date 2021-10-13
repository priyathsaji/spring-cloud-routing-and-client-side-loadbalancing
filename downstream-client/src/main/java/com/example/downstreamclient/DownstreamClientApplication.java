package com.example.downstreamclient;

import com.netflix.appinfo.InstanceInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class DownstreamClientApplication implements ApplicationRunner {

    public DownstreamClientApplication(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(DownstreamClientApplication.class, args);
    }


    private final DiscoveryClient discoveryClient;
    private Log log = LogFactory.getLog(getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String serviceId = "greetings-service";
        this.log.info(String.format("registered instances of '%s'", serviceId));
        this.discoveryClient.getInstances(serviceId)
                .forEach(this::logServiceInstance);
    }

    private void logServiceInstance(ServiceInstance serviceInstance) {
        String msg = String.format("host = %s, port = %s, service ID = %s",
                serviceInstance.getHost(), serviceInstance.getPort(), serviceInstance.getInstanceId());
        log.info(msg);
    }


}
