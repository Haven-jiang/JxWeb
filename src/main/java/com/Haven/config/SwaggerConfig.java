package com.Haven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * swagger配置文件 SwaggerConfig
 *
 * @author HavenJust
 * @date 15:23 周二 10 五月 2022年
 */

@Configuration
@EnableOpenApi
public class SwaggerConfig {

//    @Bean
//    public Docket docket() {
//        Docket docket = new Docket(DocumentationType.OAS_30)
//                .apiInfo(new ApiInfoBuilder()
//                        .title("我的标题")
//                        .description("我的描述")
//                        // .termsOfServiceUrl("http://www.xx.com/")
////                        .contact(new Contact("knife", "https://knife.blog.csdn.net/", "xx@qq.com"))
//                        .version("1.0")
//                        .build())
//                // 分组名称
//                .groupName("all")
//                .select()
//                // 这里指定Controller扫描包路径
//                .apis(RequestHandlerSelectors.basePackage("com.Haven.controller"))
//                .paths(PathSelectors.any())
//                .build();
//
//        return docket;
//    }

//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .protocols(Collections.singleton("http"))
//                .host("https://www.talkxj.com")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.Haven.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("博客api文档")
//                .description("springboot+vue开发的博客项目")
//                .contact(new Contact("风丶宇", "https://github.com/X1192176811", "1192176811@qq.com"))
//                .termsOfServiceUrl("https://www.talkxj.com/api")
//                .version("1.0")
//                .build();
//    }

}
