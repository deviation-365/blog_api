package com.deviation.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer {

    @Value("${api.path.default}")
    private String API_URL_PREFIX;  // /v1/api


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.OPTIONS.name(),
                                HttpMethod.PATCH.name())
                        .allowCredentials(false)
                        .allowedHeaders("Authorization", "Content-Type")
                        .maxAge(3600);
            }

        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
