package de.workshops.bookshelf.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.SerializationFeature;

@TestConfiguration
public class JacksonTestConfiguration {
    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
