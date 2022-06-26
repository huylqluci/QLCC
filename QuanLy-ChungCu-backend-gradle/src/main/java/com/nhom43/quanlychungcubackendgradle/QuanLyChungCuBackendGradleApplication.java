package com.nhom43.quanlychungcubackendgradle;

import com.nhom43.quanlychungcubackendgradle.share.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class QuanLyChungCuBackendGradleApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanLyChungCuBackendGradleApplication.class, args);
    }

}
