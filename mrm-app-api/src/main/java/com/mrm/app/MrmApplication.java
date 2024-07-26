package com.mrm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@ComponentScan(basePackages = "com.mrm.app")
@EnableScheduling
public class MrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrmApplication.class, args);
    }
}
