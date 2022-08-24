package com.bns.bookhubservice.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SwaggerConfiguration {
    private String version;
    private String title;

    @Bean
    public Docket api(){
        version = "v1";
        title = "BookHub API" + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bns.bookhubservice"))
                .paths(PathSelectors.any())
                .build()
                .groupName(version)
                .useDefaultResponseMessages(false)
                .apiInfo(aPiInfo(title, version))
                .useDefaultResponseMessages(false);
    }

    private ApiInfo aPiInfo(String title, String version){
        return new ApiInfo(
                title,
                "A Bookhub API Docs",
                version,
                "http://www.bookhub.kro.kr/",
                new Contact("Contact Me", "https://blog.naver.com/ghdalswl77", "enschs21@gmail.com"),
                "Licenses",

                "http://www.bookhub.kro.kr/",

                new ArrayList<>());


    }
}
