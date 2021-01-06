package com.example.mongo.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .globalOperationParameters(globalParameterList())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Demo",
                "1.0",
                "Terms of service",
                "Micro services",  // new Contact("Ranjan Mondal", "https://springframework.guru/about/", "ranjan@yopmail.com").toString()
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }

    private List<Parameter> globalParameterList() {
        Parameter authTokenHeader =
                new ParameterBuilder()
                        .name("Authorization") // name of the header
                        .modelRef(new ModelRef("string")) // data-type of the header
                        .required(false) // required/optional
                        .parameterType("header") // for query-param, this value can be 'query'
                        .description("Basic Auth Token")
                        .build();

        Parameter languageHeader =
                new ParameterBuilder()
                        .name("Accept-Language")
                        .modelRef(new ModelRef("string"))
                        .required(false)
                        .parameterType("header")
                        .description("en, fr")
                        .build();

        return Arrays.asList(authTokenHeader, languageHeader);
    }
}
