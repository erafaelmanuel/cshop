package io.ermdev.cshop.core.bean;

import io.ermdev.cshop.typeconverter.UserConverter;
import mapfierj.SimpleMapper;
import mapfierj.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperBean {

    @Bean
    public Mapper mapper(UserConverter userConverter) {
        Mapper mapper = new Mapper();
        mapper.getConverter().register(userConverter);
        return mapper;
    }

    @Bean
    public SimpleMapper simpleMapper() {
        return new SimpleMapper();
    }
}
