package io.ermdev.cshop.core.bean;

import io.ermdev.cshop.typeconverter.UserConverter;
import mapfierj.ModelMapper;
import mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapfierJMapperBean {

    private UserConverter userConverter;

    @Autowired
    public void setUserConverter(UserConverter userConverter) {
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
