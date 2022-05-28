package com.letscode.classesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class ClassesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassesApiApplication.class, args);
    }

}
