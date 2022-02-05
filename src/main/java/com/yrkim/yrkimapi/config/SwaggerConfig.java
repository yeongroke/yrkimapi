package com.yrkim.yrkimapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String group;
    private String title;

    @Bean
    public Docket apiHello() {
        group = "Hello";
        title = "YRKIM API " + group;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(group)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yrkim.yrkimapi.controller"))
                .paths(PathSelectors.ant("/hello/**"))
                .build()
                .apiInfo(apiInfo(title, group));

    }

    @Bean
    public Docket api() {
        group = "v1";
        title = "YRKIM API " + group;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(group)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yrkim.yrkimapi.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo(title, group));

    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder().title(title)
                .description("웹 전용 API _ KIM YEONG ROK")
                .license("yrkimapi").licenseUrl("https://github.com/yeongroke").version(version).build();
    }
}
