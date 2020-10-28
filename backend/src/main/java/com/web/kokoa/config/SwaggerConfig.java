package com.web.kokoa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.RequestHandler;
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
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("swagger demo").description("api example").build();
    }
    @Bean
    public Docket commonApi(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("kokoa").apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.web.kokoa.controller")).paths(PathSelectors.ant("/api/**")).build();

    }

}
