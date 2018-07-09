//package io.ermdev.cshop.core.config;
//
//import io.ermdev.cshop.web.interceptor.LoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
//
//    private LoginInterceptor loginInterceptor;
//
//    @Autowired
//    public WebMvcConfiguration(LoginInterceptor loginInterceptor) {
//        this.loginInterceptor=loginInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/login");
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/register/**");
//    }
//}
