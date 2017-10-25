package io.ermdev.ecloth.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.ermdev.ecloth"})
@MapperScan("io.ermdev.ecloth.data.mapper")
public class EclothApplication {

    public static void main(String args[]) {
        SpringApplication.run(EclothApplication.class, args);
    }
}
