# Service Discovery and Routing

- Service Discovery can be done using 
    - Netflix Eureka
    - Consul ..

- if using Neflix Eureka
  - 2 components
    - Eureka Server ( acts as a service discovery server, where all the services will be registred)
    - Eureka Client ( which registers to discovery server so that other consumers know where to communicate )

## Eureka Server

```xml
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

- some of the properties required to configure
```properties
server.port=8761 // port on which server should start
eureka.client.register-with-eureka=false //register itself to eureka server
eureka.client.fetch-registry=false
eureka.server.enable-self-preservation=false // if one or more instances is down eureka servers assumes network is down instead of service down
```

- to enable Eureka server in spring boot application we need
```
@EnableEurekaServer
```


## Eureka Service

- if Eureka client is added as dependency the service will get registered in Eureka server
- some required properties include
```properties
spring.application.name=<<application name>>
eureka.instance.instance-id=<<id of the instance>>
```


## Eureka Consumer

- this consumes the services by connecting to service discovery server ( Eureka server ).
- Client Side Load balancing is done using ```spring cloud loadbalancer```

- properties required
    - ```spring.application.name``` - name of the application to register in discovery server
    - ```eureka.instance.instance-id``` - id of the application ( better to give a unique id)
    - ```server.port``` - port on which the application should start ( if 0 start on random port )
- list of all availabe instances of a service can be fetched by 
    1. Autowiring DiscoveryClient - ```org.springframework.cloud.client.discovery.DiscoveryClient```
    2. fetching instances
    ```java
     this.discoveryClient.getInstances("<<name of the instances>>")
    ```
- Load balenced WebClient Builder can be created using 
```java
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

```
- to use web client just autowire the ```WebClient.Builder``` 
- use ```"http:/<<service name>>/<<api path to fetch>>``` as url 
    eg: ```http://greetings-service/hi/{name}```
