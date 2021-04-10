package de.workshops.bookshelf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("application")
public class BookshelfApplicationProperties {

    private String title;

    private String version;
}
