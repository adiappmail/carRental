package com.refyne.carRental.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {


    ParameterBuilder aParameterBuilder = new ParameterBuilder();

    aParameterBuilder.build();

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(metaData());
  }
  private ApiInfo metaData() {
    return new ApiInfoBuilder()
            .title("Spring Boot REST API")
            .description("\"CAR RENTAL DEMO\"")
            .version("1.0.0")
            .license("LL1")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
            .build();
  }

}