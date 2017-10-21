package io.ermdev.ecloth.core.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfiguration {

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hibernateEntityManagerFactory){
        return hibernateEntityManagerFactory.getSessionFactory();
    }
}
