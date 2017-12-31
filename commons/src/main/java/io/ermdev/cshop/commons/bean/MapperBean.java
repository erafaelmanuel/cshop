package io.ermdev.cshop.commons.bean;

import io.ermdev.mapfierj.core.SimpleMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperBean {

    @Bean
    public SimpleMapper simpleMapper() {
        return new SimpleMapper();
    }
}
