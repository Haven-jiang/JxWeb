package com.Haven.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.Haven.utils.CommonUtil.getImagePath;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @param registry
     * @see ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/dist/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static_file/**")
                .addResourceLocations("file:" + getImagePath());
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");

    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/function").setViewName("/function/functions.html");
        registry.addViewController("/function/youthlearn/jiangxi/query_page").setViewName("/function/YouthLearn/jiangxi/searchOrganization.html");
        registry.addViewController("/function/youthlearn").setViewName("/function/YouthLearn/jiangxi/YouthLearn.html");
        registry.addViewController("/function/youthlearn/jiangxi/register_page").setViewName("/function/YouthLearn/jiangxi/JxYouthLearn.html");
    }


}
