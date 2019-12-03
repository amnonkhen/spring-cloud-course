package com.akh.userservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class UserServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    public static class UserService {

        @Autowired
        private RestTemplate restTemplate;


        @GetMapping("/pay")
        @HystrixCommand(fallbackMethod = "fallback",
            groupKey = "user-service",
            commandKey = "user-service",
            threadPoolKey = "user-service-thread")
        public String payment() {
            return restTemplate.getForObject("http://payment-service/pay/msg", String.class);
        }

        public String fallback(final Throwable hystrixCommand) {
            return "Fallback message";
        }
        
        @Value("${spring.application.name}")
        String appName;

        @GetMapping("/user/doIt")
        public String doIt() {
            return appName;
        }
    }
}
