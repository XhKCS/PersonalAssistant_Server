package com.robin.personalAssistant_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class PersonalAssistantServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalAssistantServerApplication.class, args);
    }

}
