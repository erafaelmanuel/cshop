package io.ermdev.ecloth.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.ermdev.ecloth"})
public class EclothApplication {

    public static void main(String args[]) {
        SpringApplication.run(EclothApplication.class, args);
    }
}
