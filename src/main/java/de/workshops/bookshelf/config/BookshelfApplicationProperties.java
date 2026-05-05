package de.workshops.bookshelf.config;

import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("application")
public class BookshelfApplicationProperties {

    private String title;

    private String version;

    /**
     * The OpenAPI configuration
     */
    private CustomOpenApiConfig customOpenApiConfig;

    @Setter
    @Getter
    public static class CustomOpenApiConfig {

        private boolean enabled;
    }

    private Map<String, BookshelfUser> credentials;
}
