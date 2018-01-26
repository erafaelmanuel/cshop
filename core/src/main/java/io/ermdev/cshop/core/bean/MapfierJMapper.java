package io.ermdev.cshop.core.bean;

import io.ermdev.cshop.typeconverter.UserConverter;
import io.ermdev.mapfierj.ModelMapper;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapfierJMapper {

    private UserConverter userConverter;

    @Autowired
    public MapfierJMapper(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Bean
    public SimpleMapper simpleMapper() {
        return new SimpleMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConverter().register(userConverter);
        return modelMapper;
    }
}
