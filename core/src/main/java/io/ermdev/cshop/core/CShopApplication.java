package io.ermdev.cshop.core;

import io.ermdev.cshop.typeconverter.UserConverter;
import mapfierj.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"io.ermdev.cshop"})
@MapperScan({"io.ermdev.cshop.data.repository"})
public class CShopApplication {

    @Bean
    public Mapper mapper(UserConverter userConverter) {
        Mapper mapper = new Mapper();
        mapper.getConverter().register(userConverter);
        return mapper;
    }

    public static void main(String args[]) {
        SpringApplication.run(CShopApplication.class, args);
    }

}
