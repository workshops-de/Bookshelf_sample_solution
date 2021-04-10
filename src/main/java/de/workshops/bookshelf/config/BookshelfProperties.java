package de.workshops.bookshelf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("bookshelf")
public class BookshelfProperties {

    private Integer someNumber;

    private String someText;
}
