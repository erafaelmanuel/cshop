package io.ermdev.cshop.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.ermdev.cshop"})
@MapperScan("io.ermdev.cshop.data.repository")
public class CShopApplication {

    public static void main(String args[]) {
        SpringApplication.run(CShopApplication.class, args);
    }
}
