package de.workshops.bookshelf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SwaggerConfig {

    @Bean
    GroupedOpenApi appOpenApi() {
        String[] packagesToScan = { "de.workshops.bookshelf" };

        return GroupedOpenApi.builder().group("app").packagesToScan(packagesToScan).build();
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Bookshelf API")
                                .version("v0.0.1")
                                .license(new License()
                                                .name("MIT License")
                                                .url("https://opensource.org/licenses/MIT")
                                )
                );
    }
}
