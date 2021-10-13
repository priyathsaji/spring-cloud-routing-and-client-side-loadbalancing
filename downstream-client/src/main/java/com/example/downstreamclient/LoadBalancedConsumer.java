package com.example.downstreamclient;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;

@Component
public class LoadBalancedConsumer implements ApplicationRunner {
    final WebClient.Builder loadBalencedBuilder;
    private final Log log = LogFactory.getLog(getClass());

    public LoadBalancedConsumer(WebClient.Builder loadBalencedBuilder) {
        this.loadBalencedBuilder = loadBalencedBuilder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, String> variables = Collections.singletonMap("name",
                "Cloud Natives!");
        IntStream.range(0, 10).forEach(i -> {
            this.loadBalencedBuilder.build().get().uri("http://greetings-service/hi/{name}", variables)
                    .retrieve().bodyToMono(JsonNode.class).map(x -> x.get("greeting").asText())
                    .subscribe(greeting -> log.info("greeting: " + greeting));
        });


    }
}
