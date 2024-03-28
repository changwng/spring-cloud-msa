package com.covenant.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
public class ScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }

}
