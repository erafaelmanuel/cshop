package io.ermdev.cshop.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.rem.cs.data.jpa"})
@SpringBootApplication(scanBasePackages = {"io.ermdev.cshop", "com.rem.cs.web", "com.rem.cs.data.jpa"})
@ComponentScan("com.rem.cs.data.jpa")
public class CShopApplication {

    public static void main(String args[]) {
        SpringApplication.run(CShopApplication.class, args);
    }

}
