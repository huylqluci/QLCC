package com.nhom43.quanlychungcubackendgradle.share.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket bookingCloneApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Chung Cư ABC")
                .version("1.0")
                .description("API for QuanLy_ChungCu_ABC")
                .contact(new Contact("Nguyễn Văn Thủy", null, "banquanly.chungcuABC@gmail.com"))
                .license("Apache License Version 2.0")
                .build();
    }

    // Xem API tại trang

    // http://localhost:8200/swagger-ui/

    // http://localhost:8200/v2/api-docs
}
