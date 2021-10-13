package com.example.downstreamservice;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Collections;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class DownstreamServiceApplication {

    @Value("${eureka.instance.instance-id}")
    String instanceId;

    private final Log log = LogFactory.getLog(getClass());

    public static void main(String[] args) {
        SpringApplication.run(DownstreamServiceApplication.class, args);
    }


    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/hi/{name}"), req -> {
            return ok().body(fromValue(Collections.singletonMap("greeting", String.format("Hello, %s from %s!", req.pathVariable("name"), instanceId))));
        });
    }

    private void debug(ServerRequest request) {
        String str = ToStringBuilder.reflectionToString(request);
        log.info("request: " + str);
        request.headers().asHttpHeaders().forEach((k, v) -> log.info(k + '=' + v));
    }
}
