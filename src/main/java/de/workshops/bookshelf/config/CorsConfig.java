package de.workshops.bookshelf.config;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry corsRegistry) {
//        corsRegistry
//                .addMapping("/**")
//                .allowedOrigins(
//                        "http://localhost:4200",
//                        "http://localhost:3000"
//                )
//                .allowedMethods(HttpMethod.GET.name());
    }
}
