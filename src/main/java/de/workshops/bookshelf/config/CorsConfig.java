package de.workshops.bookshelf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry
//                .addMapping("/**")
//                .allowedOrigins(
//                        "http://localhost:4200",
//                        "http://localhost:3000"
//                )
//                .allowedMethods(HttpMethod.GET.name());
    }
}
