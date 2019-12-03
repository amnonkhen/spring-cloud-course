package com.akh.paymentservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
    }
    
    
    @RestController
    public static class PaymentService {
        @Value("${eureka.instance.instanceId}")
        String instanceId;

        @GetMapping("/pay/msg")
        public String msg() {
            return instanceId;
        }
    }

}
